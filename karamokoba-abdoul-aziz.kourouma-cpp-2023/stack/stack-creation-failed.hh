#pragma once

#include <exception>
#include <iostream>

class StackCreationFailed : public std::exception
{
public:
    StackCreationFailed(const std::string &msg)
        : message_(msg)
    {}

    virtual const char *what() const noexcept;

private:
    std::string message_;
};
