/**
 * \file request/response.hh
 * \brief Response declaration.
 */

#pragma once

#include "request/request.hh"
#include "request/types.hh"

namespace http
{
    /**
     * \struct Response
     * \brief Value object representing a response.
     */
    struct Response
    {
        explicit Response(const STATUS_CODE &);
        Response(const Request &, const STATUS_CODE & = STATUS_CODE::OK);

        Response() = default;
        Response(const Response &) = default;
        Response &operator=(const Response &) = default;
        Response(Response &&) = default;
        Response &operator=(Response &&) = default;
        ~Response() = default;

        std::string protocol_version = "HTTP/1.1";
        std::pair<STATUS_CODE, std::string> status; // = (200, "OK");
        Header collection_header;
        std::string message = "";
        std::string method = "";
        ssize_t len = 0;
    };
} // namespace http
