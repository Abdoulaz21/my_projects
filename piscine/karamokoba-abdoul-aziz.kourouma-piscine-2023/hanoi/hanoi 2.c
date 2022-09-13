#include <stdio.h>

void hanoi_rec(int nb, char src, char tmp, char dst);

void hanoi(unsigned n)
{
    hanoi_rec(n, '1', '2', '3');
}

void hanoi_rec(int nb, char src, char tmp, char dst)
{
    if (nb == 1)
    {
        putchar(src);
        putchar('-');
        putchar('>');
        putchar(dst);
        putchar('\n');
        return;
    }
    hanoi_rec(nb - 1, src, dst, tmp);
    putchar(src);
    putchar('-');
    putchar('>');
    putchar(dst);
    putchar('\n');
    hanoi_rec(nb - 1, tmp, src, dst);
    return;
}
