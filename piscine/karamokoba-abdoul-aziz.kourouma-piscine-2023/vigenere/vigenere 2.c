#include <stdio.h>

void cipher(const char *key, const char *msg, char *res)
{
    size_t pos = 0;
    size_t i = 0;
    for (; *(msg + i) != 0; i++)
    {
        char t = *(msg + i);
        if (t >= 'a' && t <= 'z')
            t -= 32;
        if (t < 'A' || t > 'Z')
        {
            *(res + i) = t;
            continue;
        }
        char k = *(key + pos);
        if (k >= 'a' && k <= 'z')
            k -= 32;
        *(res + i) = 'A' + ((t + k) % 26);
        pos++;
        if (*(key + pos) == 0)
            pos = 0;
    }
    if (i != 0)
        *(res + i) = 0;
}

void decipher(const char *key, const char *msg, char *res)
{
    size_t pos = 0;
    size_t i = 0;
    for (; *(msg + i) != 0; i++)
    {
        char t = *(msg + i);

        if (t >= 'a' && t <= 'z')
            t -= 32;

        if (t < 'A' || t > 'Z')
        {
            *(res + i) = t;
            continue;
        }

        char k = *(key + pos);

        if (k >= 'a' && k <= 'z')
            k -= 32;

        int val = (t - k);

        if (val >= 0)
            *(res + i) = 'A' + val;
        else
            *(res + i) = 'A' + val + 26;

        pos++;
        if (*(key + pos) == 0)
            pos = 0;
    }
    if (i != 0)
        *(res + i) = 0;
}
