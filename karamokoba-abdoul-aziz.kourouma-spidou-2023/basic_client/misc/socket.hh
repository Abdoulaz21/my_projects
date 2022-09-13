/**
 * \file misc/socket.hh
 * \brief Socket related syscalls.
 */

#pragma once

#include <fcntl.h>
#include <sys/sendfile.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#include "misc/sys-wrapper.hh"

namespace sys
{
    /**
     * \brief accept(2).
     */
    inline auto accept = make_wrapper<int>(::accept);

    /**
     * \brief bind(2).
     */
    inline auto bind = make_wrapper<void>(::bind);

    /**
     * \brief close(2).
     */
    inline auto close = make_wrapper<void>(::close);

    /**
     * \brief connect(2).
     */
    inline auto connect = make_wrapper<void>(::connect);

    static int fcntl_wrapper(int fildes, int opts)
    {
        auto flags = ::fcntl(fildes, F_GETFL);
        return ::fcntl(fildes, F_SETFL, flags | opts);
    }
    /**
     * \brief fcntl(2).
     *
     * Since fcntl is a variadic syscall we need to call a helper function
     * with a fixed number of parameter to create a wrapper out of it.
     */
    inline auto fcntl_set = make_wrapper<int>(fcntl_wrapper);

    /**
     * \brief getsockname(2).
     */
    inline auto getsockname = make_wrapper<void>(::getsockname);

    /**
     * \brief listen(2).
     */
    inline auto listen = make_wrapper<void>(::listen);

    /**
     * \brief recv(2).
     */
    inline auto recv = make_wrapper<ssize_t>(::recv);

    /**
     * \brief send(2).
     */
    inline auto send = make_wrapper<ssize_t>(::send);

    /**
     * \brief sendfile(2).
     */
    inline auto sendfile = make_wrapper<ssize_t>(::sendfile);

    /**
     * \brief setsockopt(2).
     */
    inline auto setsockopt = make_wrapper<void>(::setsockopt);

    /**
     * \brief getsockopt(2).
     */
    inline auto getsockopt = make_wrapper<void>(::getsockopt);

    /**
     * \brief socket(2).
     */
    inline auto socket = make_wrapper<int>(::socket);

    /**
     * \brief getpeername(2).
     */
    inline auto getpeername = make_wrapper<void>(::getpeername);
} // namespace sys
