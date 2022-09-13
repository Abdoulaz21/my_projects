#include <stdio.h>

void *my_memset(void *s, int c, size_t n)
{
    char *tmp = s;
    while (n > 0)
    {
        *tmp = c;
        n--;
        tmp++;
    }
    return s;
}
