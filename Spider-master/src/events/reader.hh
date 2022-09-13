/**
 * \file events/listener.hh
 * \brief ListenerEW declaration.
 */

#pragma once

#include "events/events.hh"
#include "socket/socket.hh"

namespace http
{
    /**
     * \class RecvRequestEW
     * \brief Workflow for reader socket.
     */
    class RecvRequestEW : public EventWatcher
    {
    public:
        /**
         * \brief Create a RecvRequestEW from a reader socket.
         */
        explicit RecvRequestEW(shared_socket socket);

        /**
         * \brief Start reading request on reader socket.
         */
        void operator()() final;

    private:
        /**
         * \brief Listener socket.
         */
        shared_socket sock_;
        /**
         * \brief Connection of this socket
         */
        std::string buff_ = "";
    };
} // namespace http
