#include <stdio.h>

void array_max_min(int tab[], size_t len, int *max, int *min)
{
    if (tab != NULL && len != 0)
    {
        int M = tab[0];
        int m = M;

        for (size_t i = 1; i < len; i++)
        {
            if (M < tab[i])
                M = tab[i];
            if (m > tab[i])
                m = tab[i];
        }

        *max = M;
        *min = m;
    }
}
