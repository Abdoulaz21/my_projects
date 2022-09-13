#include <stdio.h>

size_t my_strspn(const char *s, const char *accept)
{
    if (*accept == 0 || *s == 0)
        return 0;
    size_t res = 0;
    size_t i = 0;
    for (size_t k = 0; *(s + k) != 0; k++)
    {
        for (i = 0; *(accept + i) != 0; i++)
        {
            if (*(s + k) == *(accept + i))
            {
                res++;
                break;
            }
        }
        if (*(accept + i) == 0)
            return res;
    }
    return res;
}
