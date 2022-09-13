#include <stdio.h>

void swap(char *c1, char *c2)
{
    char tmp = *c1;
    *c1 = *c2;
    *c2 = tmp;
}

void str_revert(char str[])
{
    size_t i = 0;
    char *tmp = str;
    while (*(str + i) != '\0')
    {
        i++;
    }
    for (size_t j = 0; j < (i / 2); j++)
    {
        swap((tmp + j), (str + i - j - 1));
    }
    str = tmp;
}
