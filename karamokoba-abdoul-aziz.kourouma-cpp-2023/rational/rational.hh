// rational.hh
#pragma once

#include <ostream>

struct Rational
{
    Rational(int numerator = 0, int denominator = 1);

    friend std::ostream &operator<<(std::ostream &os, const Rational &r);
    Rational operator*(const Rational &r) const;
    Rational operator+(const Rational &r) const;
    void operator*=(const Rational &r);

    int num;
    int den;
};
