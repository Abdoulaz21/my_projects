#include <stdio.h>

void reverse(char s[], size_t size)
{
    int i;
    int j;
    char c;

    for (i = 0, j = size - 1; i < j; i++, j--)
    {
        c = s[i];
        s[i] = s[j];
        s[j] = c;
    }
}

char *my_itoa(int value, char *s)
{
    int sign = value;
    size_t count = 0;

    if (sign < 0)
        value = -value;
    do
    {
        s[count] = value % 10 + '0';
        value /= 10;
        count++;
    } while (value > 0);
    if (sign < 0)
    {
        s[count] = '-';
        count++;
    }
    s[count] = '\0';
    reverse(s, count);
    return s;
}
