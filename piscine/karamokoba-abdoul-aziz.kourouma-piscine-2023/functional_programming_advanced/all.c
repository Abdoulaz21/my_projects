#include <stdbool.h>
#include <stddef.h>

bool all(int *array, size_t len, bool (*func)(int))
{
    for (size_t i = 0; i < len; i++)
    {
        if ((func)(*(array + i)) == false)
            return false;
    }
    return true;
}
