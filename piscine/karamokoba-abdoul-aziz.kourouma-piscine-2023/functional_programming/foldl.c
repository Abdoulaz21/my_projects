#include <stddef.h>

int _foldl(int *array, size_t len, int (*func)(int, int), int acc)
{
    if (len > 0)
    {
        acc = (func)(_foldl(array, len - 1, func, acc), *(array + len - 1));
    }
    return acc;
}

int foldl(int *array, size_t len, int (*func)(int, int))
{
    int acc = 0;
    size_t pos = 1;
    acc = (func)(_foldl(array, len - 1, func, acc), *(array + len - pos));
    return acc;
}
