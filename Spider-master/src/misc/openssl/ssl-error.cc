#include "misc/openssl/ssl-error.hh"

namespace ssl
{
    const std::error_category &ssl_category()
    {
        static ssl_error_category instance;
        return instance;
    }

} // namespace ssl
