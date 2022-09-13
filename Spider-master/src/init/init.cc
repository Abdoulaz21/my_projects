#include "init.hh"

namespace http
{
    static shared_socket create_and_bind(
        const misc::AddrInfo &addrinfo,
        const std::unique_ptr<SSL_CTX, decltype(SSL_CTX_free) *> &ssl_ctx)
    {
        shared_socket dSocket;
        for (const auto &addr : addrinfo)
        {
            try
            {
                // on doit verifier s'il faut creer ssl ou default socket
                if (ssl_ctx == nullptr)
                    dSocket = std::make_shared<DefaultSocket>(
                        addr.ai_family, addr.ai_socktype, addr.ai_protocol);
                else
                    dSocket = std::make_shared<SSLSocket>(
                        addr.ai_family, addr.ai_socktype, addr.ai_protocol,
                        ssl_ctx.get());
                if (addr.ai_family == AF_INET6)
                    dSocket->ipv6_set(true);
                dSocket->setsockopt(SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, 1);
                sys::fcntl_set(*(dSocket->fd_get()), O_NONBLOCK);
                dSocket->bind(addr.ai_addr, addr.ai_addrlen);
                return dSocket;
            }
            catch (...)
            {
                throw; // continue;
            }
        }
        throw std::runtime_error("server: failed to bind");
    }

    shared_socket prepare_socket(
        const std::string &ip, const std::string &port,
        const std::unique_ptr<SSL_CTX, decltype(SSL_CTX_free) *> &ssl_ctx)
    {
        misc::AddrInfoHint hints = misc::AddrInfoHint();
        hints.family(AF_UNSPEC);
        hints.socktype(SOCK_STREAM);
        hints.flags(AI_PASSIVE);
        misc::AddrInfo rv = misc::getaddrinfo(ip.c_str(), port.c_str(), hints);
        auto dSocket = create_and_bind(rv, ssl_ctx);

        dSocket->listen(10);
        return dSocket;
    }
} // namespace http
