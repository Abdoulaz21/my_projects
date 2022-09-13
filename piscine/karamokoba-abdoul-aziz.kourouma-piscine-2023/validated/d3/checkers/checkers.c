#include <stdio.h>

int main(void)
{
    for (int i = 0; i < 5; i++)
    {
        for (int k = 0; k < 5; k++)
        {
            putchar('O');
            putchar('X');
        }
        putchar('\n');
        for (int l = 0; l < 5; l++)
        {
            putchar('X');
            putchar('O');
        }
        putchar('\n');
    }
    return 0;
}
