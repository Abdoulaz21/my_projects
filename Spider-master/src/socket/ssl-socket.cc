#include "ssl-socket.hh"

#include <iostream>

#include "misc/openssl/ssl.hh"

namespace http
{
    SSLSocket::SSLSocket(int domain, int type, int protocol, SSL_CTX *ssl_ctx)
        : Socket{ std::make_shared<misc::FileDescriptor>(
            sys::socket(domain, type, protocol)) }
        , ssl_(SSL_new(ssl_ctx), SSL_free)
    {}

    SSLSocket::SSLSocket(const misc::shared_fd &fd, SSL_CTX *ssl_ctx)
        : Socket{ fd }
        , ssl_(SSL_new(ssl_ctx), SSL_free)
    {
        SSL_set_fd(ssl_.get(), *fd_);
        ssl::accept(ssl_.get());
    }

    ssize_t SSLSocket::recv(void *dst, size_t len)
    {
        return ssl::read(ssl_.get(), dst, len);
    }

    ssize_t SSLSocket::send(const void *src, size_t len)
    {
        return ssl::write(ssl_.get(), src, len);
    }

    ssize_t SSLSocket::sendfile(misc::shared_fd &fd, off_t &offset, size_t len)
    {
        return sys::sendfile(*fd_, *fd, &offset, len);
    }

    void SSLSocket::bind(const sockaddr *addr, socklen_t addrlen)
    {
        sys::bind(*fd_, addr, addrlen);
    }

    void SSLSocket::listen(int backlog)
    {
        sys::listen(*fd_, backlog);
    }

    void SSLSocket::setsockopt(int level, int optname, int optval)
    {
        sys::setsockopt(*fd_, level, optname, &optval, sizeof(optval));
    }

    void SSLSocket::getsockopt(int level, int optname, int &optval)
    {
        socklen_t optlen = sizeof(optval);
        sys::getsockopt(*fd_, level, optname, &optval, &optlen);
    }

    shared_socket SSLSocket::accept(sockaddr *addr, socklen_t *addrlen)
    {
        auto new_socket = sys::accept(*fd_, addr, addrlen);

        auto sharedfd =
            std::make_shared<misc::FileDescriptor>(std::move(new_socket));
        auto client_socket =
            std::make_shared<SSLSocket>(sharedfd, SSL_get_SSL_CTX(ssl_.get()));
        sys::fcntl_set(*sharedfd, O_NONBLOCK);
        return client_socket;
    }

    void SSLSocket::connect(const sockaddr *, socklen_t)
    {
        ssl::connect(ssl_.get());
    }
} // namespace http
