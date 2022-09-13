#pragma once

#include "misc/addrinfo/addrinfo.hh"

/**
 * \brief The length of the event array, must be greater than zero
 */
constexpr int MAX_EVENTS = 64;

constexpr int DEFAULT_BUFFER_SIZE = 2048;

/**
 * \brief Iterate over the addrinfo wrapper to create and bind a socket.
 *
 * \param addrinfo: addrinfo wrapper
 *
 * \return The created socket
 *
 * Try to create and bind a socket with every addrinfo element until it
 * succeeds. Throws a runtime_error exception if an error occurs.
 */
int create_and_bind(const misc::AddrInfo &addrinfo);

/**
 * \brief Initialize the Addrinfo struct, call create_and_bind() and listen(2)
 *
 * \param ip: IP address of the server
 * \param port: Port of the server
 *
 * \return The created socket
 *
 * Initialize the addrinfo wrapper needed by create_and_bind() before calling
 * it. When create_and_bind() returns a valid socket, set the socket to
 * listening and return it.
 */
int prepare_socket(const std::string &ip, const std::string &port);

/**
 * \brief Accept a new client
 *
 * \param epoll_instance: the epoll instance
 * \param server_socket: listening socket
 *
 * \return The new socket
 */
int accept_client(int epoll_instance, int server_socket);
