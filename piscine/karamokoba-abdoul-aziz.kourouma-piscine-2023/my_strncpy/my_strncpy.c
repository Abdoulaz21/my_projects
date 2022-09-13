#include <stdio.h>

char *my_strncpy(char *dest, const char *source, size_t num)
{
    if (num == 0)
        return dest;
    size_t i;
    for (i = 0; i < num && source[i] != 0; i++)
    {
        dest[i] = source[i];
    }
    for (; i < num; i++)
    {
        dest[i] = '\0';
    }
    return dest;
}
