#pragma once

#include <exception>
#include <iostream>
#include <string>

class InvalidArgumentException : public std::exception
{
public:
    InvalidArgumentException(const std::string &msg)
        : msg_(msg)
    {}

    virtual const char *what() const noexcept
    {
        return msg_.c_str();
    }

private:
    std::string msg_;
};
