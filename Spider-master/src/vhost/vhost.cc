#include "vhost/vhost.hh"

#include "error/init-error.hh"
#include "misc/openssl/ssl.hh"
#include "vhost/dispatcher.hh"

namespace http
{
    static int sni_callback(SSL *ssl, int *, void *vh)
    {
        std::cerr << "SNI CALLBACK" << '\n';
        // int type = SSL_get_servername_type(ssl);
        const char *servername =
            SSL_get_servername(ssl, TLSEXT_NAMETYPE_host_name);

        auto vhost = reinterpret_cast<VHost *>(vh);
        if (servername != nullptr)
        {
            std::cerr << "NOT NULLPTR" << '\n';
            std::string sn(servername);
            std::cerr << sn << '\n';
            for (auto &x : dispatcher.get_vhost())
            {
                if (x->conf_get().vhost_server_name == sn
                    && vhost->conf_get().vhost_ip == x->conf_get().vhost_ip
                    && vhost->conf_get().vhost_port == x->conf_get().vhost_port)
                {
                    if (x->conf_get().vhost_ssl_key != "")
                    {
                        SSL_set_SSL_CTX(ssl, x->ssl_ctx_get().get());
                        return SSL_TLSEXT_ERR_OK;
                    }
                    break;
                }
            }
        }
        std::cerr << "NULLPTR" << '\n';
        return SSL_TLSEXT_ERR_NOACK;
    }

    void VHost::certified()
    {
        try
        {
            // load library
            SSL_load_error_strings();
            OpenSSL_add_ssl_algorithms();

            // create ssl_ctx
            SSL_CTX *newssl = SSL_CTX_new(TLS_method());
            SSL_CTX_set_ecdh_auto(newssl, 1);

            // set ssl_ctx in vhost
            ssl_ctx_.reset(newssl);

            // setup cert and key
            ssl::ctx_use_certificate_file("certificate file", newssl,
                                          conf_.vhost_ssl_cert.c_str(),
                                          SSL_FILETYPE_PEM);
            ssl::ctx_use_PrivateKey_file("private key", newssl,
                                         conf_.vhost_ssl_key.c_str(),
                                         SSL_FILETYPE_PEM);

            X509 *x = SSL_CTX_get0_certificate(newssl);
            ssl::x509_check_host("check host", x,
                                 conf_.vhost_server_name.c_str(),
                                 conf_.vhost_server_name.length(), 0, nullptr);
            ssl::ctx_check_private_key("check private key", newssl);

            // setup sni
            SSL_CTX_set_tlsext_servername_arg(newssl, this);
            SSL_CTX_set_tlsext_servername_callback(newssl, &sni_callback);
        }
        catch (ssl::SSL_ERROR e)
        {
            // SSL_CTX_free(ctx);
            throw std::make_error_code(e);
        }
    }
} // namespace http
