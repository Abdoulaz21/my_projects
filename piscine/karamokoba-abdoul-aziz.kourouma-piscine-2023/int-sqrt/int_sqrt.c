#include <stdio.h>

int int_sqrt(int n)
{
    int res = n;

    if (n < 0)
        res = -1;
    else
    {
        while ((res * res) > n)
        {
            res--;
        }
    }

    return res;
}
