#pragma once

#include <exception>
#include <iostream>

class StackEmpty : public std::exception
{
public:
    StackEmpty(const std::string &msg)
        : message_(msg)
    {}

    virtual const char *what() const noexcept;

private:
    std::string message_;
};
