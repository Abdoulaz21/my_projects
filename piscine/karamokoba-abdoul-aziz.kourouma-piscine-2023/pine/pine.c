#include <stdio.h>

int pine(unsigned n)
{
    int res;
    if (n < 3)
        res = 1;
    else
    {
        res = 0;
        unsigned int val = 1;

        for (unsigned int i = 0; i < n; i++)
        {
            for (unsigned int m = 0; m < n - i - 1; m++)
            {
                putchar(' ');
            }
            for (unsigned int n = 0; n < val; n++)
            {
                putchar('*');
            }
            putchar('\n');
            val += 2;
        }

        for (unsigned int j = 0; j < (n / 2); j++)
        {
            for (unsigned int k = 0; k < (n - 1); k++)
            {
                putchar(' ');
            }
            putchar('*');
            putchar('\n');
        }
    }
    return res;
}
