#include <stdio.h>

int my_strstr(const char *haystack, const char *needle)
{
    int res = -1;
    if (!(needle))
        res = -1;
    else if (needle == NULL || *needle == '\0')
        res = 0;
    else
    {
        int pos;
        int in = 0;
        size_t i = 0;
        size_t j = 0;
        while (*(haystack + i) != '\0' && *(needle + j) != '\0')
        {
            if (*(haystack + i) == *(needle + j))
            {
                if (in == 0)
                {
                    in = 1;
                    pos = i;
                }
                j++;
            }
            else
            {
                j = 0;
                in = 0;
                pos = -1;
            }
            i++;
        }
        if (*(haystack + i) == '\0' && *(needle + j) != '\0')
            pos = -1;
        res = pos;
    }
    return res;
}
