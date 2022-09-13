#include <stdio.h>
#include <stdlib.h>

int *my_calloc(size_t size, int init)
{
    if (size == 0)
        return NULL;
    int *res = malloc(size * sizeof(int));
    if (res == NULL)
        return NULL;
    for (size_t i = 0; i < size; i++)
    {
        *(res + i) = init;
    }
    return res;
}
