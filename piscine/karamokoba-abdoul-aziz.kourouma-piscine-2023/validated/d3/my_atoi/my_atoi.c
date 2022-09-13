#include <stdio.h>

int my_atoi(const char *str)
{
    int res = 0;
    if (*str == '\0')
    {
        return 0;
    }
    int sign = -1;
    while (*str == ' ')
    {
        str++;
    }
    while (*str != '\0')
    {
        if (*str == '-' && sign == -1)
        {
            sign = 1;
        }
        else if (*str == '+' && sign == -1)
        {
            sign = 0;
        }
        else if (*str >= '0' && *str <= '9')
        {
            int val = *str - '0';
            res = res * 10 + val;
        }
        else
        {
            return 0;
        }
        str++;
    }
    if (sign == 1)
        res = -res;
    return res;
}
