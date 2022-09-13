#pragma once

#include <string>

class RLE
{
public:
    RLE(char special_char)
        : spe_char_(special_char)
    {}

    const std::string perform(const std::string);

private:
    char spe_char_;
};
