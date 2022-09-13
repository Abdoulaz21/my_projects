#include "vector.hh"

#include <iostream>
#include <ostream>

Vector &Vector::operator+=(const Vector &rhs)
{
    x += rhs.x;
    y += rhs.y;
    return *this;
}

Vector &Vector::operator-=(const Vector &rhs)
{
    x -= rhs.x;
    y -= rhs.y;
    return *this;
}

Vector &Vector::operator*=(int scalar)
{
    x *= scalar;
    y *= scalar;
    return *this;
}

std::ostream &operator<<(std::ostream &os, const Vector &vect)
{
    return os << '{' << vect.x << ',' << vect.y << '}';
}

Vector operator+(const Vector &lhs, const Vector &rhs)
{
    Vector res = Vector(lhs.x + rhs.x, lhs.y + rhs.y);
    return res;
}

Vector operator-(const Vector &lhs, const Vector &rhs)
{
    Vector res = Vector(lhs.x - rhs.x, lhs.y - rhs.y);
    return res;
}

Vector operator*(const Vector &lhs, int scalar)
{
    Vector res = Vector(lhs.x * scalar, lhs.y * scalar);
    return res;
}

Vector operator*(int scalar, const Vector &rhs)
{
    Vector res = Vector(rhs.x * scalar, rhs.y * scalar);
    return res;
}

int operator*(const Vector &lhs, const Vector &rhs)
{
    int res = (lhs.x * rhs.x) + (lhs.y * rhs.y);
    return res;
}
