#include <assert.h>
#include <err.h>
#include <errno.h>
#include <stddef.h>

int int_palindrome(int n)
{
    int res = 0;
    if (n >= 0)
    {
        int tmp = n;
        int val = 0;
        while (tmp != 0)
        {
            val = (val * 10) + (tmp % 10);
            tmp /= 10;
        }
        if (val == n)
            res = 1;
    }
    return res;
}
