#include <stddef.h>

size_t lchild(size_t i)
{
    return ((2 * i) + 1);
}
size_t rchild(size_t i)
{
    return ((2 * i) + 2);
}

void _heapify(int *array, size_t i, size_t m)
{
    if (i < m)
    {
        size_t l = lchild(i);
        size_t r = rchild(i);
        size_t g = i;
        if ((l < m) && (array[l] > array[i]))
            g = l;
        if ((r < m) && (array[r] > array[g]))
            g = r;
        if (g != i)
        {
            int tmp = array[i];
            array[i] = array[g];
            array[g] = tmp;
            _heapify(array, g, m);
        }
    }
}

void heapify(int *array, size_t size)
{
    for (int i = size - 1; i >= 0; i--)
    {
        _heapify(array, i, size);
    }
}

void heap_sort(int *array, size_t size)
{
    for (size_t i = 0; i < size; i++)
    {
        size_t tmp = array[size - i - 1];
        array[size - i - 1] = array[0];
        array[0] = tmp;
        heapify(array, size - i - 1);
    }
    size_t pos = size - 1;
    while (pos > 0 && array[pos] < array[pos - 1])
    {
        int tmp = array[pos];
        array[pos] = array[pos - 1];
        array[pos - 1] = tmp;
        pos--;
    }
}
