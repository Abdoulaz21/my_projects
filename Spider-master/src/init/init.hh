#pragma once

#include <arpa/inet.h>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#include "misc/addrinfo/addrinfo.hh"
#include "misc/sys-wrapper.hh"
#include "socket/default-socket.hh"
#include "socket/socket.hh"
#include "socket/ssl-socket.hh"

namespace http
{
    shared_socket prepare_socket(
        const std::string &ip, const std::string &port,
        const std::unique_ptr<SSL_CTX, decltype(SSL_CTX_free) *> &ssl_ctx);
}
