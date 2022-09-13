#pragma once

#include <exception>
#include <iostream>

class StackMaxSize : public std::exception
{
public:
    StackMaxSize(const std::string &msg)
        : message_(msg)
    {}

    virtual const char *what() const noexcept;

private:
    std::string message_;
};
