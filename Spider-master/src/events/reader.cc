#include "reader.hh"

#include "events/register.hh"
#include "vhost/dispatcher.hh"

namespace http
{
    RecvRequestEW::RecvRequestEW(shared_socket socket)
        : EventWatcher(*socket->fd_get(), EV_READ)
    {
        sock_ = socket;
    }

    void RecvRequestEW::operator()()
    {
        ssize_t nread = 0;
        try
        {
            size_t old_size = buff_.size();
            buff_.resize(old_size + 1024);
            nread = (*sock_).recv(buff_.data() + old_size, 1024);
            buff_.resize(old_size + nread);
        }
        catch (...)
        {
            event_register.unregister_ew(this);
            return;
        }
        if (nread == 0)
        {
            event_register.unregister_ew(this);
            return;
        }
        auto foundrn = buff_.find("\r\n\r\n");
        auto foundcl = buff_.find("Content-Length: ");
        if (foundrn == std::string::npos)
        {
            return;
        }
        auto req = Request();
        req.constructorFromString(buff_);
        if (foundcl == std::string::npos)
        {
            dispatcher.dispatch(req, sock_);
            event_register.unregister_ew(this);
            return;
        }
        auto content_length = req.collection_header.find("Content-Length");
        std::stringstream scontent_length(*content_length);
        size_t len;
        scontent_length >> len;
        if (req.message.length() != len)
            return;
        dispatcher.dispatch(req, sock_);
        event_register.unregister_ew(this);
    }
} // namespace http
