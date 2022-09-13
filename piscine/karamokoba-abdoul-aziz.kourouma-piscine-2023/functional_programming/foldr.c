#include <stddef.h>

int _foldr(int *array, size_t len, size_t count, int (*func)(int, int))
{
    int res = 0;
    if (len > count)
    {
        res = (func)(*array, _foldr(array + 1, len, count + 1, func));
    }
    return res;
}

int foldr(int *array, size_t len, int (*func)(int, int))
{
    int acc = 0;
    size_t count = 0;
    acc = (func)(*array, _foldr(array + 1, len, count + 1, func));
    return acc;
}
