#include "socket/default-socket.hh"

#include <iostream>

#include "misc/socket.hh"

namespace http
{
    DefaultSocket::DefaultSocket(int domain, int type, int protocol)
        : Socket{ std::make_shared<misc::FileDescriptor>(
            sys::socket(domain, type, protocol)) }
    {}

    DefaultSocket::DefaultSocket(const misc::shared_fd &fd)
        : Socket{ fd }
    {}

    ssize_t DefaultSocket::recv(void *dst, size_t len)
    {
        return sys::recv(*fd_, dst, len, 0);
    }

    ssize_t DefaultSocket::send(const void *src, size_t len)
    {
        return sys::send(*fd_, src, len, MSG_NOSIGNAL);
    }

    ssize_t DefaultSocket::sendfile(misc::shared_fd &fd, off_t &offset,
                                    size_t len)
    {
        return sys::sendfile(*fd_, *fd, &offset, len);
    }

    void DefaultSocket::bind(const sockaddr *addr, socklen_t addrlen)
    {
        sys::bind(*fd_, addr, addrlen);
    }

    void DefaultSocket::listen(int backlog)
    {
        sys::listen(*fd_, backlog);
    }

    void DefaultSocket::setsockopt(int level, int optname, int optval)
    {
        sys::setsockopt(*fd_, level, optname, &optval, sizeof(optval));
    }

    void DefaultSocket::getsockopt(int level, int optname, int &optval)
    {
        socklen_t optlen = sizeof(optval);
        sys::getsockopt(*fd_, level, optname, &optval, &optlen);
    }

    shared_socket DefaultSocket::accept(sockaddr *addr, socklen_t *addrlen)
    {
        auto new_socket = sys::accept(*fd_, addr, addrlen);
        auto sharedfd =
            std::make_shared<misc::FileDescriptor>(std::move(new_socket));
        sys::fcntl_set(*sharedfd, O_NONBLOCK);
        return std::make_shared<DefaultSocket>(sharedfd);
    }

    void DefaultSocket::connect(const sockaddr *addr, socklen_t addrlen)
    {
        sys::connect(*fd_, addr, addrlen);
    }

} // namespace http
