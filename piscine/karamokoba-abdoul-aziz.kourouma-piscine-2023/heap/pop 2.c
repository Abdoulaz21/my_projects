#include <assert.h>

#include "heap.h"

int pop(struct heap *heap)
{
    assert(heap->size > 0);
    int res = heap->array[0];
    int last = heap->array[heap->size - 1];
    size_t p = 0;
    size_t i = 0;
    for (; 2 * i + 1 < heap->size; i = p)
    {
        p = 2 * i + 1;
        if (p < heap->size - 1)
        {
            if (heap->array[p + 1] > heap->array[p])
                p++;
        }

        if (last < heap->array[p])
        {
            heap->array[i] = heap->array[p];
        }
        else
            break;
    }
    heap->array[i] = last;
    heap->size -= 1;
    return res;
}
