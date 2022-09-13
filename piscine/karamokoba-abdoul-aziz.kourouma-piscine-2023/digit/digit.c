#include <stdio.h>

unsigned int digit(int n, int k)
{
    int res;
    if (n <= 0 || k <= 0)
        res = 0;
    else
    {
        int val = n;
        for (int i = 1; i < k; i++)
        {
            val /= 10;
        }
        res = val % 10;
    }
    return res;
}
