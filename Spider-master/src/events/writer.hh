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
     * \class SendReponseEW
     * \brief Workflow for writer socket.
     */
    class SendReponseEW : public EventWatcher
    {
    public:
        /**
         * \brief Create a SendReponseEW from a writer socket.
         */
        explicit SendReponseEW(shared_socket socket,
                               const std::string &message);

        /**
         * \brief Start writing response on writer socket.
         */
        void operator()() final;

    private:
        /**
         * \brief Writer socket.
         */
        shared_socket sock_;
        /**
         * \brief Position in the buffer.
         */
        size_t pos_ = 0;
        /**
         * \brief Message to send
         */
        std::string message_;
    };

} // namespace http
