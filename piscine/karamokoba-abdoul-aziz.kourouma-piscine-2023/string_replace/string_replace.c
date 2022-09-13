#include <stdio.h>
#include <stdlib.h>

char *string_replace(char c, const char *str, const char *pattern)
{
    if (str == NULL)
        return NULL;
    size_t size = sizeof(str);
    if (pattern != NULL && *pattern != 0)
        size *= sizeof(pattern);
    char *res = malloc(size);
    if (res == NULL)
    {
        return NULL;
    }
    size_t j = 0;
    for (size_t i = 0; *(str + i) != 0; i++)
    {
        if (*(str + i) == c)
        {
            if (pattern == NULL)
            {
                continue;
            }
            size_t pos = 0;
            while (*(pattern + pos) != 0)
            {
                *(res + j) = *(pattern + pos);
                pos++;
                j++;
            }
        }
        else
        {
            *(res + j) = *(str + i);
            j++;
        }
    }
    *(res + j) = 0;
    return res;
}
