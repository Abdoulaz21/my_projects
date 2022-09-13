#include <stdio.h>

int main(int argc, char **argv)
{
    if (argc != 2)
        return 1;
    FILE *fp = fopen(*(argv + 1), "r");
    if (!fp)
        return 2;

    char s[512];
    while (fgets(s, 512, fp))
    {
        if (*s == '\n')
        {
            if (fputs("\n", stdout) >= 0)
                continue;
        }
        int l = 0;
        for (int i = 0; *(s + i) != '\n'; i++)
        {
            l++;
        }
        char r[512] = { 0 };
        for (int i = 0; i < l; i++)
        {
            *(r + i) = *(s + l - i - 1);
        }
        *(r + l) = '\n';
        // puts(r);
        if (fputs(r, stdout) < 0)
            break;
    }
    return fclose(fp);
}
