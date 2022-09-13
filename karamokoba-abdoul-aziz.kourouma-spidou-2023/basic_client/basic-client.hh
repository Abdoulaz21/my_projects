#pragma once

#include "misc/addrinfo/addrinfo.hh"

constexpr int DEFAULT_BUFFER_SIZE = 2048;

/**
 * \brief Iterate over the addrinfo wrapper to connect to the server
 *
 * \param addrinfo: addrinfo wrapper
 *
 * \return The created socket or throws an runtime_error
 *
 * Try to create and connect a socket with every addrinfo element
 * (using addrinfo_iter) until it succeeds
 *
 */
int create_and_connect(const misc::AddrInfo &addrinfo);

/**
 * \brief Initialize the Addrinfo struct and call create_and_connect()
 *
 * \param ip: IP address of the server
 * \param port: Port of the server
 *
 * \return The created socket
 */
int prepare_socket(const std::string &ip, const std::string &port);

/**
 * \brief Handle communication with the server
 *
 * \param server_socket: server socket
 *
 * Read user message from STDIN and send it to the server
 *
 * Step 2: This function sends small messages to the server
 *
 * Step 3: In addition to step 2 behavior, this function receives the server
 * response on small messages
 *
 * Step 4: The function must now handle long messages
 */
void communicate(int server_socket);
