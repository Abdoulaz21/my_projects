#include "rational.hh"

#include <ostream>
#include <stdexcept>

Rational::Rational(int numerator, int denominator)
    : num(numerator)
    , den(denominator)
{
    if (den == 0)
        throw std::invalid_argument("Denominator cannot be equal to 0.");
}

std::ostream &operator<<(std::ostream &os, const Rational &r)
{
    return os << r.num << "/" << r.den;
}

Rational Rational::operator*(const Rational &r) const
{
    return Rational(r.num * num, r.den * den);
}

Rational Rational::operator+(const Rational &r) const
{
    if (den == r.den)
        return Rational(r.num + num, den);
    return Rational(r.num * den + r.den * num, r.den * den);
}

void Rational::operator*=(const Rational &r)
{
    den = den * r.den;
    num = num * r.num;
}
