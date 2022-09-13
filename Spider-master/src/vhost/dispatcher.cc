/**
 * \file vhost/dispatcher.hh
 * \brief Dispatcher declaration.
 */
#include "vhost/dispatcher.hh"

#include <arpa/inet.h>
#include <ctime>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string>
#include <sys/socket.h>

#include "events/register.hh"
#include "events/writer.hh"
#include "misc/socket.hh"

namespace http
{
    /**
     * \class Dispatcher
     * \brief Instance in charge of dispatching requests between vhosts.
     */
    Dispatcher dispatcher;
    void Dispatcher::add_vhost(const shared_vhost &new_vh)
    {
        vh_.push_back(new_vh);
    }

    static bool is_wildcard(std::string ip)
    {
        if (ip == "0.0.0.0")
            return true;
        for (auto &it : ip)
        {
            if (it != '0' && it != ':')
                return false;
        }
        return true;
    }

    std::optional<shared_vhost> Dispatcher::find_vhost(Request &req,
                                                       shared_socket &sock)
    {
        // get ip and port
        char myIP[16];
        unsigned int myPort;

        if (!sock->is_ipv6())
        {
            struct sockaddr_in sin;
            socklen_t len = sizeof(sin);
            sys::getsockname(*sock->fd_get(), (struct sockaddr *)&sin, &len);
            inet_ntop(AF_INET, &sin.sin_addr, myIP, sizeof(myIP));
            myPort = ntohs(sin.sin_port);
        }
        else
        {
            struct sockaddr_in6 sin;
            socklen_t len = sizeof(sin);
            sys::getsockname(*sock->fd_get(), (struct sockaddr *)&sin, &len);
            inet_ntop(AF_INET6, &sin.sin6_addr, myIP, sizeof(myIP));
            myPort = ntohs(sin.sin6_port);
        }
        std::string port = std::to_string(myPort);
        Header head = req.collection_header;
        auto ip = head.find("Host");
        for (auto &x : vh_)
        {
            auto serv_ip = x->conf_get().vhost_ip;
            if (is_wildcard(serv_ip) || is_wildcard(myIP))
            {
                serv_ip = myIP;
            }

            const auto &serv_port = std::to_string(x->conf_get().vhost_port);
            const auto &serv_name = x->conf_get().vhost_server_name;
            const auto &serv_name_port = serv_name + ":" + serv_port;
            const auto &serv_ip_port = serv_ip + ":" + serv_port;

            if (serv_name == ip.value_or("") || serv_ip == ip.value_or("")
                || serv_name_port == ip.value_or("")
                || serv_ip_port == ip.value_or(""))
            {
                if (serv_ip == myIP && serv_port == port)
                    return std::optional<shared_vhost>{ x };
            }
        }

        return find_def_vhost();
    }

    std::optional<shared_vhost> Dispatcher::find_def_vhost()
    {
        for (auto &x : vh_)
        {
            if (x->conf_get().vhost_def_vh)
            {
                return std::optional<shared_vhost>{ x };
            }
        }

        return std::nullopt;
    }

    std::vector<shared_vhost> &Dispatcher::get_vhost()
    {
        return vh_;
    }

    static std::string generate_time()
    {
        time_t tmm = std::time(0);
        char *dt = ctime(&tmm);
        std::string date(dt);
        return date;
    }

    void Dispatcher::dispatch(Request &req, shared_socket &sock_)
    {
        std::string date = generate_time();
        auto vho = find_vhost(req, sock_);
        std::string to_send;

        if (vho)
        {
            auto conn_shared = std::make_shared<Connection>();
            vho.value()->respond(req, conn_shared);
            auto res = conn_shared->resp;
            std::string code =
                std::to_string(static_cast<int>((res.status).first));
            // HTTP/1.1 CODE OK
            to_send = res.protocol_version + " " + code + " "
                + res.status.second + "\r\n";
            // Date
            to_send = to_send + "Date: " + date;
            if (res.status.first == 200)
            {
                // Content-length + connection + message
                to_send += "Content-Length: " + std::to_string(res.len) + "\r\n"
                    + "Connection: close\r\n\r\n";
                if (req.method != "HEAD")
                    to_send += res.message;
            }
            else
            {
                to_send = to_send + "Content-Length: 0\r\n"
                    + "Connection: close\r\n\r\n";
            }
            event_register.register_event<SendReponseEW>(sock_, to_send);
        }
        else
        {
            // HTTP/1.1 CODE KO
            to_send = "HTTP/1.1 400 BAD_REQUEST\r\n";
            // Date
            to_send = to_send + "Date: " + date;
            // Content length and connection close
            to_send =
                to_send + "Content-Length: 0\r\n" + "Connection: close\r\n\r\n";
            event_register.register_event<SendReponseEW>(sock_, to_send);
            // close la socket pour close la conection*/
        }
    }
} // namespace http
