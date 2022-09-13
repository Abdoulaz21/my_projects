#include <stdio.h>

int top_of_the_hill(int tab[], size_t len)
{
    int res = -1;

    if (len == 1)
        res = 0;
    else
    {
        size_t i = 0;
        for (; i < len - 1; i++)
        {
            if (tab[i] >= tab[i + 1] && res == -1)
                res = i;
            if (tab[i] < tab[i + 1] && res != -1)
                return -1;
        }
        if (i == len - 1 && res == -1)
            res = len - 1;
    }
    return res;
}
