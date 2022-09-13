#include <stdio.h>

int my_memcmp(const void *s1, const void *s2, size_t num)
{
    if (num == 0)
        return 0;
    const char *ss1 = s1;
    const char *ss2 = s2;
    while (num && *ss1 == *ss2)
    {
        ss1++;
        ss2++;
        num--;
    }
    return *ss1 - *ss2;
}
