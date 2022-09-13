#include <stdio.h>

size_t my_strlen(const char *s)
{
    size_t res = 0;
    if (!s)
        return 0;
    else
    {
        while (s[res] != '\0')
        {
            res++;
        }
    }
    return res;
}
