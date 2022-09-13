#include <limits.h>
#include <stdio.h>

int max_ptr(int *ptr, size_t size)
{
    int res = INT_MIN;
    if (size > 0)
    {
        for (size_t i = 0; i < size; i++)
        {
            if (res < *(ptr + i))
            {
                res = *(ptr + i);
            }
        }
    }

    return res;
}
