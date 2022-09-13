#include "events/listener.hh"

#include <iostream>
#include <misc/socket.hh>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <sys/socket.h>

#include "events/reader.hh"
#include "events/register.hh"

namespace http
{
    ListenerEW::ListenerEW(shared_socket socket)
        : EventWatcher(*socket->fd_get(), EV_READ)
    {
        auto shfd = (*socket).fd_get();
        sock_ = socket;
        if (!(*socket).is_ipv6())
        {
            struct sockaddr_in sin;
            socklen_t len = sizeof(sin);
            sys::getsockname(*socket->fd_get(), (struct sockaddr *)&sin, &len);
            port_ = sin.sin_port;
        }
        else
        {
            struct sockaddr_in6 sin;
            socklen_t len = sizeof(sin);
            sys::getsockname(*socket->fd_get(), (struct sockaddr *)&sin, &len);
            port_ = sin.sin6_port;
        }
    }

    void ListenerEW::operator()()
    {
        struct sockaddr_in client_addr;
        socklen_t client_len = sizeof(client_addr);
        try
        {
            auto client_sd =
                sock_->accept((struct sockaddr *)&client_addr, &client_len);
            event_register.register_event<RecvRequestEW>(client_sd);
        }
        catch (std::system_error &e)
        {
            std::cerr << "Error : accept\n";
            return;
        }
    }
} // namespace http
