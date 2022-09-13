#include "request/response.hh"

#include <istream>
#include <sstream>

namespace http
{
    Response::Response(const STATUS_CODE &code)
    {
        this->status = statusCode(code);
    }

    // 200, 400, 403, 404, 405, 426

    Response::Response(const Request &req, const STATUS_CODE &code)
    {
        auto codePeer = statusCode(code);
        this->protocol_version = req.protocol_vers;
        this->status = codePeer;
        // il faut le setup mais pour le moment ca ne sert à rien
        this->method = req.method;
    }
} // namespace http
