#include <stdio.h>

void *my_memmove(void *dest, const void *src, size_t n)
{
    const char *s = src;
    char *d = dest;
    if (dest > src)
    {
        for (size_t e = n; e > 0; e--)
        {
            *(d + e - 1) = *(s + e - 1);
        }
    }
    else
    {
        for (size_t i = 0; i < n; i++)
        {
            *(d + i) = *(s + i);
        }
    }
    return dest;
}
