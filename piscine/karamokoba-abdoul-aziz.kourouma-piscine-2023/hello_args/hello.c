#include <stdio.h>

int main(int argc, char *argv[])
{
    if (argc > 1)
    {
        for (int i = 1; i < argc; i++)
        {
            if ((i + 1) % 2 == 0)
                puts("Hello Even!");
            else
                puts("Hello Odd!");
            puts(*(argv + i));
        }
    }
    return 0;
}
