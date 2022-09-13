#include <stdio.h>

int check_alphabet(const char *str, const char *alphabet)
{
    if (alphabet == NULL || *alphabet == 0)
        return 1;
    if (*str == 0)
        return 0;
    size_t i;
    for (i = 0; *(alphabet + i) != 0; i++)
    {
        size_t j = 0;
        while (*(str + j) != 0)
        {
            if (*(str + j) == *(alphabet + i))
            {
                break;
            }
            j++;
        }
        if (*(str + j) == 0)
            return 0;
    }
    return *(alphabet + i) == 0;
}
