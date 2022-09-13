#include "header.hh"

#include "types.hh"

namespace http
{
    Header::Header(std::istringstream &str)
    {
        std::string line;
        while (std::getline(str, line) && line[0] != '\n')
        {
            auto delim = line.find(":");
            if (delim == std::string::npos)
            {
                error = true;
                break;
            }
            std::string key = line.substr(0, delim);
            std::string value =
                line.substr(delim + 1, line.length() - delim - 2);
            while (value[0] == ' ' || value[0] == '\t')
                value.erase(0, 1);
            while (value[value.length() - 1] == ' ' || value[0] == '\t')
                value.erase(value.length() - 1, 1);
            add(key, value);
        }
    }
    std::unordered_map<std::string, std::string> Header::getm_header()
    {
        return m_header;
    }
    void Header::add(std::string key, std::string value)
    {
        m_header.insert(std::pair<std::string, std::string>(key, value));
    }

    bool Header::is_exist(const std::string &key)
    {
        auto it = m_header.find(key);
        if (it == m_header.end())
            return false;
        return true;
    }
    std::optional<std::string> Header::find(const std::string &key)
    {
        auto it = m_header.find(key);
        if (it == m_header.end())
            return std::nullopt;
        return std::optional<std::string>{ it->second };
    }

    std::ostream &operator<<(std::ostream &os, const Header &o)
    {
        for (auto it : o.m_header)
        {
            os << it.first << ": " << it.second << http_crlf;
        }
        return os;
    }
} // namespace http
