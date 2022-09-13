#include "basic-client.hh"

#include <iostream>
#include <stdexcept>
#include <string>

#include "misc/addrinfo/addrinfo-iterator.hh"
#include "misc/addrinfo/addrinfo.hh"
#include "misc/socket.hh"

int create_and_connect(const misc::AddrInfo &addrinfo)
{
    int res;
    auto p = addrinfo.begin();

    while (p != addrinfo.end())
    {
        if ((res =
                 sys::socket(p->ai_family, (*p).ai_socktype, (*p).ai_protocol))
            == -1)
        {
            p++;
            continue;
        }

        sys::connect(res, (*p).ai_addr, (*p).ai_addrlen);

        break;
    }

    if (p == addrinfo.end())
        throw std::runtime_error("client: failed to connect\n");

    return res;
}

int prepare_socket(const std::string &ip, const std::string &port)
{
    int res;
    misc::AddrInfoHint hints = misc::AddrInfoHint();
    hints.family(AF_UNSPEC);
    hints.socktype(SOCK_STREAM);

    misc::AddrInfo servinfo =
        misc::getaddrinfo(ip.c_str(), port.c_str(), hints);

    res = create_and_connect(servinfo);

    return res;
}

void communicate(int server_socket)
{
    std::string message;
    while (!std::getline(std::cin, message).eof())
    {
        if (message[message.size() - 1] != '\n')
            message += '\n';

        int numbytessend;

        if ((numbytessend =
                 sys::send(server_socket, message.c_str(), message.size(), 0))
            == -1)
        {
            perror("send");
            exit(1);
        }

        char buffer[DEFAULT_BUFFER_SIZE];

        int numbytesrecv = 0;

        do
        {
            if ((numbytesrecv += sys::recv(server_socket, buffer + numbytesrecv,
                                           DEFAULT_BUFFER_SIZE - 1, 0))
                == -1)
            {
                perror("receive");
                exit(1);
            }
        } while (buffer[numbytesrecv - 1] != '\n');

        buffer[numbytesrecv] = '\0';
        std::cout << "Server answered with: " << buffer;
    }
}

int main(int argc, char **argv)
{
    if (argc != 3)
    {
        std::cerr << "Usage: " << argv[0] << " SERVER_IP SERVER_PORT" << '\n';
        exit(1);
    }

    int sock = prepare_socket(argv[1], argv[2]);
    communicate(sock);

    return 0;
}
