#include <stdio.h>
#include <stdlib.h>

#include "heap.h"

void print_int(int x)
{
    if (x == 0)
    {
        putchar('0');
    }
    else
    {
        int *val = malloc(8 * sizeof(int));
        int i = 0;
        while (x > 0)
        {
            *(val + i) = x % 10;
            i++;
            x /= 10;
        }

        for (int j = 0; j < i; j++)
        {
            char c = '0' + *(val + i - j - 1);
            putchar(c);
        }
        free(val);
    }
}

void print_array(int *array, size_t len, size_t pos, size_t *count)
{
    if (pos < len)
    {
        int val = *(array + pos);
        print_int(val);
        if (*count != len)
            putchar(' ');
        *count += 1;
        print_array(array, len, (2 * pos) + 1, count);
        print_array(array, len, (2 * pos) + 2, count);
    }
}

void print_heap(const struct heap *heap)
{
    size_t *x = malloc(sizeof(size_t));
    *x = 1;
    print_array(heap->array, heap->size, 0, x);
    putchar('\n');
    free(x);
}
