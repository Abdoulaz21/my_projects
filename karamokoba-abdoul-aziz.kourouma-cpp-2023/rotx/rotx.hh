#pragma once

#include "encrypt.hh"

class RotX : public Encrypt
{
public:
    RotX(std::string input, int x)
        : Encrypt(input)
        , val_(x)
    {}

    void process() override;

private:
    int val_;
};
