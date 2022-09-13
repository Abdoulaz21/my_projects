#include <stdio.h>

void *my_memcpy(void *dest, const void *source, size_t num)
{
    char *dst = dest;
    const char *src = source;
    while (num)
    {
        *dst = *src;
        dst++;
        src++;
        num--;
    }

    return dest;
}
