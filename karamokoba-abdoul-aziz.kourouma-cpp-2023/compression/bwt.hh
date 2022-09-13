#pragma once

#include <string>

class BWT
{
public:
    BWT(char special_char)
        : end_char_(special_char)
    {}

    const std::string perform(const std::string);

private:
    char end_char_;
};
