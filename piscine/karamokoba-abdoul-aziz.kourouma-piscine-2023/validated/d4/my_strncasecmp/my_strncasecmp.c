#include <stdio.h>

char lower(char c)
{
    char res = c;
    if (c <= 'Z' && c >= 'A')
        res = c + 32;
    return res;
}

int my_strncasecmp(const char *s1, const char *s2, size_t n)
{
    if (n == 0)
        return 0;
    while (n != 0 && lower(*s1) == lower(*s2))
    {
        if (n == 0 || *s1 == '\0' || *s2 == '\0')
            break;
        s1++;
        s2++;
        n--;
    }
    return lower(*s1) - lower(*s2);
}
