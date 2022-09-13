#include <optional>

#include "vhost-factory.hh"
#include "vhost.hh"
/**
 * \file vhost/dispatcher.hh
 * \brief Dispatcher declaration.
 */

#pragma once

namespace http
{
    /**
     * \class Dispatcher
     * \brief Instance in charge of dispatching requests between vhosts.
     */
    class Dispatcher
    {
    public:
        Dispatcher() = default;
        Dispatcher(const Dispatcher &) = delete;
        Dispatcher &operator=(const Dispatcher &) = delete;
        Dispatcher(Dispatcher &&) = delete;
        Dispatcher &operator=(Dispatcher &&) = delete;

        Dispatcher(std::vector<shared_vhost> vh)
            : vh_(vh)
        {}

        void add_vhost(const shared_vhost &new_vh);

        std::optional<shared_vhost> find_vhost(Request &req,
                                               shared_socket &sock);
        std::optional<shared_vhost> find_def_vhost();

        std::vector<shared_vhost> &get_vhost();

        void dispatch(Request &req, shared_socket &sock_);

    private:
        /* FIXME: Add members to store the information relative to the
        ** Dispatcher.
        */

        std::vector<shared_vhost> vh_;
    };

    /**
     * \brief Service object.
     */
    extern Dispatcher dispatcher;
} // namespace http
