#include <stdio.h>

unsigned int number_digits_rec(unsigned int n)
{
    unsigned int res;
    if ((n / 10) == 0)
    {
        res = 1;
    }
    else
    {
        res = 1 + number_digits_rec(n / 10);
    }
    return res;
}
