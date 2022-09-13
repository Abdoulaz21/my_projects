#include "epoll-server.hh"

#include <cstring>
#include <iostream>
#include <map>
#include <system_error>

#include "./misc/epoll.hh"
#include "./misc/socket.hh"

int create_and_bind(const misc::AddrInfo &addrinfo)
{
    int res;
    auto p = addrinfo.begin();
    while (p != addrinfo.end())
    {
        if ((res = sys::socket(p->ai_family, p->ai_socktype, p->ai_protocol))
            == -1)
        {
            p++;
            continue;
        }

        int optval = 1;
        sys::setsockopt(res, SOL_SOCKET, SO_REUSEADDR, &optval, sizeof(int));
        sys::bind(res, p->ai_addr, p->ai_addrlen);
        break;
    }

    if (p == addrinfo.end())
    {
        throw std::runtime_error("server: failed to bind");
    }
    return res;
}

int prepare_socket(const std::string &ip, const std::string &port)
{
    misc::AddrInfoHint hints = misc::AddrInfoHint();
    hints.family(AF_UNSPEC);
    hints.socktype(SOCK_STREAM);
    hints.flags(AI_PASSIVE);

    misc::AddrInfo servinfo =
        misc::getaddrinfo(ip.c_str(), port.c_str(), hints);
    int res = create_and_bind(servinfo);

    sys::listen(res, 5);

    return res;
}

int accept_client(int epoll_instance, int socket)
{
    std::cout << "Client connected" << '\n';
    int res = sys::accept(socket, nullptr, nullptr);
    struct epoll_event ev
    {};
    ev.data.fd = res;
    ev.events = EPOLLIN;
    sys::epoll_ctl(epoll_instance, EPOLL_CTL_ADD, res, &ev);
    return res;
}

void communicate(int client, std::map<int, std::string> &map)
{
    char buf[DEFAULT_BUFFER_SIZE];
    memset(buf, 0, sizeof(buf));
    unsigned int numbytesrecv = sys::recv(client, buf, sizeof(buf), 0);
    if (numbytesrecv == 0)
    {
        std::cout << "Client disconnected" << '\n';
        sys::close(client);
        map.erase(client);
        return;
    }

    std::string mystr = std::string(buf, numbytesrecv);
    map[client] += mystr;

    if (mystr[mystr.length() - 1] == '\n')
    {
        std::cout << "Received Body: " << map[client].data();
        for (auto p = map.begin(); p != map.end(); ++p)
        {
            sys::send(p->first, map[client].c_str(), map[client].length(), 0);
        }
        map[client].clear();
    }
}

int main(int argc, char **argv)
{
    if (argc != 3)
    {
        std::cerr << "Usage: " << argv[1] << " SERVER_IP SERVER_PORT" << '\n';
        return 1;
    }

    std::vector<std::string> args(argv, argv + 3);
    int socket = prepare_socket(args[1], args[2]);

    sys::listen(socket, 5);
    int my_epoll = sys::epoll_create1(0);
    struct epoll_event event
    {};
    std::map<int, std::string> map;
    event.data.fd = socket;
    event.events = EPOLLIN;
    if (epoll_ctl(my_epoll, EPOLL_CTL_ADD, socket, &event) == -1)
        return 1;

    while (1)
    {
        struct epoll_event events[MAX_EVENTS];
        int nb_events = sys::epoll_wait(my_epoll, events, MAX_EVENTS, -1);

        for (int event_idx = 0; event_idx < nb_events; event_idx++)
        {
            if (socket == events[event_idx].data.fd)
            {
                int accept = accept_client(my_epoll, socket);
                map.insert({ accept, "" });
            }
            else
            {
                auto newfd = events[event_idx].data.fd;
                try
                {
                    communicate(newfd, map);
                }
                catch (const std::system_error &err)
                {
                    sys::close(newfd);
                    map.erase(newfd);
                }
            }
        }
    }
    sys::close(socket);

    return 0;
}
