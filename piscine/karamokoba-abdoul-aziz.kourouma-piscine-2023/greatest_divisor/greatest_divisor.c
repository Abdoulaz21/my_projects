#include <assert.h>
#include <err.h>
#include <errno.h>
#include <stddef.h>
#include <stdio.h>

unsigned int greatest_divisor(unsigned int n)
{
    unsigned int res = 0;
    for (unsigned int i = 1; i <= (n / 2); i++)
    {
        if (((n % i) == 0) && (i > res))
            res = i;
    }

    if (res == 0)
        res = n;

    return res;
}
