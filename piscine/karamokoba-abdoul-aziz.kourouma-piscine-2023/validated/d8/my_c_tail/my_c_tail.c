#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void stdintail(unsigned int n)
{
    char s;
    size_t lstr = 1;

    char *str = calloc(sizeof(char), lstr);

    unsigned int lines = 0;
    size_t pos = 0;

    while (read(0, &s, 1) == 1)
    {
        if (s == '\n')
            lines++;
        *(str + pos) = s;
        if (pos + 1 >= lstr)
        {
            lstr *= 2;
            str = realloc(str, lstr);
        }
        pos++;
    }

    *(str + pos) = 0;
    size_t posi = 0;

    for (unsigned int i = 0; i + n < lines; i++)
    {
        while (*(str + posi) != '\n')
        {
            posi++;
        }
        posi++;
    }

    if (write(1, str + posi, pos - posi) == -1)
        return;

    free(str);
}
