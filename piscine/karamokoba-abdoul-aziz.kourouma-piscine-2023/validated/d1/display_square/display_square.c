#include <stdio.h>

void display_square(int width)
{
    if (width > 3)
    {
        if ((width % 2) == 0)
            width++;

        int rep = ((width + 1) / 2) - 2;

        for (int i = 0; i < width; i++)
        {
            putchar('*');
        }
        putchar('\n');

        for (int j = 0; j < rep; j++)
        {
            putchar('*');
            for (int k = 0; k < (width - 2); k++)
            {
                putchar(' ');
            }
            putchar('*');
            putchar('\n');
        }

        for (int l = 0; l < width; l++)
        {
            putchar('*');
        }

        putchar('\n');
    }

    if (width == 3 || width == 2)
    {
        for (int m = 0; m < 2; m++)
        {
            for (int n = 0; n < 3; n++)
            {
                putchar('*');
            }
            putchar('\n');
        }
    }

    if (width == 1)
    {
        putchar('*');
        putchar('\n');
    }
}
