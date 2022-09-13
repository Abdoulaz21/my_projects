#include <stdio.h>

int my_strncmp(const char *s1, const char *s2, size_t n)
{
    while (n != 0 && *s1 == *s2)
    {
        if (*s1 == '\0' || *s2 == '\0')
            break;
        s1++;
        s2++;
        n--;
    }
    if (n == 0)
        return 0;
    return *s1 - *s2;
}

size_t my_strlen(const char *str)
{
    size_t res = 0;
    while (*(str + res) != 0 && *(str + res) != '|')
    {
        res++;
    }
    return res;
}

unsigned str_contain(const char *str, const char *params)
{
    unsigned res = 0;
    size_t np = 1;
    for (size_t i = 0; *(params + i) != 0; i++)
    {
        if (*(params + i) == '|')
            np++;
    }
    for (size_t j = 0; *(str + j) != 0; j++)
    {
        for (size_t k = 0; k < np; k++)
        {
            const char *tmp = params + (2 * k);
            size_t plen = my_strlen(tmp);
            if (my_strncmp(str + j, tmp, plen) == 0)
                res++;
        }
    }
    return res;
}
