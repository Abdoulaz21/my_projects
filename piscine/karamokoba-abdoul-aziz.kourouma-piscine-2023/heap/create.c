#include <stdlib.h>

#include "heap.h"

struct heap *create_heap(void)
{
    struct heap *res = malloc(sizeof(struct heap));
    res->size = 0;
    res->capacity = 8;
    int *arr = malloc(8 * sizeof(int));
    for (size_t i = 0; i < 8; i++)
    {
        *(arr + i) = 0;
    }
    res->array = arr;
    return res;
}
