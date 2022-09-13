#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#include "functional_programming_advanced.h"

void print_list(int *array, size_t len)
{
    for (size_t i = 0; i < len - 1; i++)
    {
        printf("%d\n", *(array + i));
    }
    if (len > 0)
        printf("%d\n", *(array + len - 1));
}

bool is_even(int x)
{
    if (x % 2 == 0)
        return true;
    return false;
}

void print_even(int *array, size_t len)
{
    int *out_array = calloc(1, sizeof(int));
    size_t out_len = filter(array, len, &out_array, is_even);
    print_list(out_array, out_len);
    free(out_array);
}
