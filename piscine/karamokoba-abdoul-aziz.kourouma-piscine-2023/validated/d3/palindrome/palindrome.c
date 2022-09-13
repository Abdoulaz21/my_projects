#include <stdio.h>

int palindrome(const char *s)
{
    if (s == NULL)
        return 0;
    if (*s == 0)
        return 1;
    size_t n = 0;
    const char *r = s;
    while (*(s + n) != 0)
    {
        n++;
    }
    size_t i;
    size_t j;
    for (i = 0, j = n - 1; i < j; i++, j--)
    {
        while (*(r + i) < 'A' || *(r + i) > 'z'
               || (*(r + i) > 'Z' && *(r + i) < 'a'))
        {
            i++;
        }

        while (*(s + j) < 'A' || *(s + j) > 'z'
               || (*(s + j) > 'Z' && *(s + j) < 'a'))
        {
            j--;
        }

        if (*(r + i) != *(s + j))
            break;
    }
    return (i >= j);
}
