#include <stdio.h>

int my_strcasecmp(const char *s1, const char *s2)
{
    size_t p = 0;
    int res;
    while (s1[p] != '\0' && s2[p] != '\0')
    {
        if (s1[p] != s2[p])
        {
            if (s1[p] - s2[p] < 32)
                res = -1;
            else if (s1[p] - s2[p] > 32)
                res = 1;
            else
                res = 0;
        }
        else
            res = 0;
        p++;
    }
    if (s1[p] == '\0' && s2[p] != '\0')
        res = -1;
    if (s1[p] != '\0' && s2[p] == '\0')
        res = 1;
    return res;
}
