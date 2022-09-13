#include <stdio.h>

void swap(int *a, int *b)
{
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

unsigned array_min(const int arr[], unsigned start, unsigned size)
{
    unsigned res = start;
    while (start < size)
    {
        if (*(arr + res) > *(arr + start))
            res = start;
        start++;
    }
    return res;
}

void selection_sort(int arr[], unsigned size)
{
    for (unsigned i = 0; i < size; i++)
    {
        unsigned min = array_min(arr, i, size);
        if (*(arr + min) < *(arr + i))
            swap(arr + min, arr + i);
    }
}
