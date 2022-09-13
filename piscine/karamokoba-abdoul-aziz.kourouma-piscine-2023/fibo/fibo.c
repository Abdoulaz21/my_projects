#include <stdio.h>

unsigned long fibonacci(unsigned long n)
{
    unsigned long res;
    if (n == 0)
    {
        res = 0;
    }
    else if (n == 1)
    {
        res = 1;
    }
    else
    {
        unsigned long x = 0;
        unsigned long y = 1;
        for (unsigned long i = 0; i < n; i++)
        {
            unsigned long z = x;
            x = y;
            y = y + z;
        }
        res = x;
    }
    return res;
}
