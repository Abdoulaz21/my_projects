#include <stdio.h>

void my_strupcase(char *str)
{
    size_t i = 0;
    while (*(str + i) != '\0')
    {
        if (*(str + i) >= 'a' && *(str + i) <= 'z')
            *(str + i) -= 32;
        i++;
    }
}
