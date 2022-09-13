#include <stdio.h>

void swap(int *a, int *b)
{
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

int partition(int arr[], int begin, int end)
{
    int pivot = arr[end];
    int i = begin - 1;
    for (int j = begin; j <= end - 1; j++)
    {
        if (arr[j] <= pivot)
        {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[end]);
    return (i + 1);
}

void __quicksort(int *arr, int begin, int end)
{
    if (begin < end)
    {
        int pivot;
        pivot = partition(arr, begin, end);
        __quicksort(arr, begin, pivot - 1);
        __quicksort(arr, pivot + 1, end);
    }
}

void quicksort(int *tab, int len)
{
    __quicksort(tab, 0, len - 1);
}
