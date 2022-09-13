#include <stdlib.h>

#include "heap.h"

void add(struct heap *heap, int val)
{
    if (heap->size == heap->capacity)
    {
        size_t temp = heap->size;
        heap->capacity *= 2;
        heap->array = realloc(heap->array, sizeof(int) * temp * 2);
    }
    size_t pos = heap->size;
    heap->array[pos] = val;
    heap->size += 1;

    while (pos > 0)
    {
        size_t tmp = (pos - 1) / 2;
        if (heap->array[pos] > heap->array[tmp])
        {
            int val = heap->array[tmp];
            heap->array[tmp] = heap->array[pos];
            heap->array[pos] = val;
        }
        pos = tmp;
    }
}
