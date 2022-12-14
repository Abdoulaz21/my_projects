/**
 * \file vhost/vhost-static-file.hh
 * \brief VHostStaticFile declaration.
 */

#pragma once

#include <fstream>
#include <sstream>

#include "config/config.hh"
#include "request/request.hh"
#include "request/response.hh"
#include "vhost/connection.hh"
#include "vhost/vhost.hh"

namespace http
{
    /**
     * \class VHostStaticFile
     * \brief VHost serving static files.
     */
    class VHostStaticFile : public VHost
    {
    public:
        friend class VHostFactory;
        virtual ~VHostStaticFile() = default;

    private:
        /**
         * \brief Constructor called by the factory.
         *
         * \param config VHostConfig virtual host configuration.
         */
        explicit VHostStaticFile(const VHostConfig &config);

    public:
        /**
         * \brief Send response.
         *
         * \param req Request.
         * \param conn Connection.
         *
         * Note that these iterators will only be useful starting from SRPS.
         */
        void respond(Request &req, std::shared_ptr<Connection> conn) final;
    };
} // namespace http
