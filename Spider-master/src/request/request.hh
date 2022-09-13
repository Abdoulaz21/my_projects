/**
 * \file request/request.hh
 * \brief Request declaration.
 */

#pragma once

#include <string>

#include "header.hh"
#include "types.hh"

namespace http
{
    /**
     * \struct Request
     * \brief Value object representing a request.
     */
    struct Request
    {
        Request() = default;
        Request(const Request &) = default;
        Request &operator=(const Request &) = default;
        Request(Request &&) = default;
        Request &operator=(Request &&) = default;
        ~Request() = default;

        void constructorFromString(const std::string &request_content)
        {
            std::istringstream in(request_content);
            std::string line;

            std::getline(in, line);
            std::istringstream in_line(line);
            std::string word;

            std::getline(in_line, word, ' ');
            this->method = word;

            std::getline(in_line, word, ' ');
            this->uri = word;

            std::getline(in_line, word, '\r');
            this->protocol_vers = word;

            this->collection_header = Header(in);
            auto pos = request_content.find("\r\n\r\n");
            if (pos != std::string::npos)
            {
                message = request_content.substr(pos + 4);
            }
        }

        friend std::ostream &operator<<(std::ostream &out, const Request &req)
        {
            out << req.method << ' ' << req.uri << ' ' << req.protocol_vers
                << http_crlf;
            out << req.collection_header;
            // Todo print the message content
            return out;
        }

        std::string method = "";
        std::string uri = "";
        std::string protocol_vers = "";
        Header collection_header;
        std::string message = "";
    };

} // namespace http
