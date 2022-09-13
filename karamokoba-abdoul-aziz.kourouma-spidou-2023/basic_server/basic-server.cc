#include "basic-server.hh"

#include <iostream>

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

int accept_client(int socket)
{
    int res = accept(socket, nullptr, nullptr);
    return res;
}

void communicate(int client)
{
    char buf[DEFAULT_BUFFER_SIZE];
    unsigned int numbytes = 1;
    do
    {
        unsigned int numbytesrecv =
            sys::recv(client, buf, DEFAULT_BUFFER_SIZE - 1, 0);
        if (numbytesrecv == 0)
            break;
        while (buf[numbytesrecv - 1] != '\n')
        {
            numbytes = sys::recv(client, buf + numbytesrecv,
                                 DEFAULT_BUFFER_SIZE - 1, 0);
            numbytesrecv += numbytes;
        }
        buf[numbytesrecv] = '\0';
        std::cout << "Received Body: " << buf;

        sys::send(client, buf, numbytesrecv, 0);
    } while (numbytes != 0);
    std::cout << "Client disconnected\n";
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
    while (1)
    {
        int client;
        if ((client = accept_client(socket)) == -1)
            continue;

        std::cout << "Client connected" << '\n';

        communicate(client);

        sys::close(client);
    }
}
