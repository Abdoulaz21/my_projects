#pragma once

#include <string>

#include "invalid_argument.hh"

class Player
{
public:
    Player(const std::string &name, unsigned int age)
        : name_(name)
        , age_(age)
    {
        if (name.empty())
            throw InvalidArgumentException("Name can't be empty.");
        if (age > 150)
            throw InvalidArgumentException("Sorry gramp, too old to play.");
    }

    std::string get_name()
    {
        return name_;
    }

private:
    std::string name_;
    unsigned int age_;
};
