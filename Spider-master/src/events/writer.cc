#include "events/writer.hh"

#include <iostream>

#include "events/register.hh"

namespace http
{
    SendReponseEW::SendReponseEW(shared_socket socket,
                                 const std::string &message)
        : EventWatcher(*socket->fd_get(), EV_WRITE)
        , sock_(socket)
        , message_(message)
    {}

    void SendReponseEW::SendReponseEW::operator()()
    {
        ssize_t len =
            (*sock_).send(message_.c_str() + pos_, message_.length() - pos_);
        if (pos_ + len != message_.length() && len != 0)
            pos_ += len;
        else
            event_register.unregister_ew(this);
    }
} // namespace http
