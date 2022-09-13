/**
 * \file misc/epoll.hh
 * \brief Epoll related syscalls.
 */

#pragma once

#include <sys/epoll.h>

#include "misc/sys-wrapper.hh"

namespace sys
{
    /**
     * \brief epoll_create1(2).
     */
    inline auto epoll_create1 = make_wrapper<int>(::epoll_create1);

    /**
     * \brief epoll_ctl(2).
     */
    inline auto epoll_ctl = make_wrapper<int>(::epoll_ctl);

    /**
     * \brief epoll_wait(2).
     */
    inline auto epoll_wait = make_wrapper<int>(::epoll_wait);
} // namespace sys
