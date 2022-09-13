#include <stdio.h>

void rot_x(char *s, int x)
{
    if (s != NULL)
    {
        if ((x != 0) && (x != 26) && (x != -26))
        {
            for (int i = 0; s[i] != '\0'; i++)
            {
                while (x < 0)
                {
                    x += 26;
                }
                if ((s[i] >= 'A' && s[i] <= 'Z'))
                {
                    if (s[i] + x > 'Z')
                        s[i] = 'A' + x - ('Z' - s[i] + 1);
                    else
                        s[i] += x;
                }
                if ((s[i] >= 'a' && s[i] <= 'z'))
                {
                    if (s[i] + x > 'z')
                        s[i] = 'a' + x - ('z' - s[i] + 1);
                    else
                        s[i] += x;
                }
            }
        }
    }
}
