#pragma once

#include <iostream>
#include <optional>
#include <sstream>
#include <string>
#include <unordered_map>

namespace http
{
    class Header
    {
    public:
        Header() = default;
        Header(std::istringstream &str);
        std::unordered_map<std::string, std::string> getm_header();
        void add(std::string key, std::string value);
        bool is_exist(const std::string &sear);
        std::optional<std::string> find(const std::string &key);

        friend std::ostream &operator<<(std::ostream &os, const Header &o);

        bool error = false;

    private:
        std::unordered_map<std::string, std::string> m_header;
    };
} // namespace http
