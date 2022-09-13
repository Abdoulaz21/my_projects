#pragma once

constexpr unsigned long long factorial(unsigned int n)
{
    unsigned long long res = 1;
    while (n > 0)
    {
        res *= n;
        --n;
    }
    return res;
}
