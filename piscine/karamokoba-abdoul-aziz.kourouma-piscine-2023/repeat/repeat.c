#include <stdio.h>

int main(int argc, char **argv)
{
    if (argc != 3)
        return 1;
    int nb = *argv[2] - '0';
    const char *str = argv[1];
    for (int i = 0; i < nb; i++)
    {
        puts(str);
    }
    return 0;
}
