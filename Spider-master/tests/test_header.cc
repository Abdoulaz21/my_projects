#include <cassert>
#include <criterion/criterion.h>
#include <sstream>

#include "../src/request/header.hh"

namespace http
{
    std::unordered_map<std::string, std::string> map;
    Header head = Header();

    void setup()
    {
        std::string req =
            "Host: example.com\r\nUser-Agent: curl/7.68.0\r\nAccept: */*\r\n";
        std::istringstream stream(req);
        head = Header(stream);
        map = head.getm_header();
    }

    Test(header, constructor, .init = setup)
    {
        assert(head.error == false);
        assert(map["Host"] == "example.com");
        assert(map["User-Agent"] == "curl/7.68.0");
        assert(map["Accept"] == "*/*");
    }

    Test(header, constructor_with_space)
    {
        std::string req = "Host: example.com   "
                          "\r\nUser-Agent:curl/7.68.0\r\nAccept:   */*\r\n";
        std::istringstream stream(req);
        Header head = Header(stream);
        auto map = head.getm_header();

        assert(map["Host"] == "example.com");
        assert(map["User-Agent"] == "curl/7.68.0");
        assert(map["Accept"] == "*/*");
    }

    Test(header, add, .init = setup)
    {
        head.add("key", "Value");
        map = head.getm_header();

        assert(map["key"] == "Value");
    }

    Test(header, is_exist, .init = setup)
    {
        assert(head.is_exist("Host") == true);
        assert(head.is_exist("User") == false);
    }

    Test(header, find, .init = setup)
    {
        assert(head.find("Host") == "example.com");
        assert(head.find("User") == std::nullopt);
    }

    Test(header, error)
    {
        std::string req =
            "Host example.c\r\nUser-Agent:curl/7.68.0\r\nAccept:   */*\r\n";
        std::istringstream stream(req);
        Header head = Header(stream);
        auto map = head.getm_header();

        assert(head.error == true);

        assert(map["Host"] != "example.com");
        assert(map["User-Agent"] != "curl/7.68.0");
        assert(map["Accept"] != "*/*");
    }
} // namespace http
