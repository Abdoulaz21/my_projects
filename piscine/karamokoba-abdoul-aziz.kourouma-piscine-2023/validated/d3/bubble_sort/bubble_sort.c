#include <stdio.h>

void bubble_sort(int array[], size_t size)
{
    if (size > 0)
    {
        size_t passed;
        int unsorted = 1;
        while (unsorted == 1)
        {
            passed = 0;
            for (size_t i = 0; i < size - 1; i++)
            {
                if (array[i] > array[i + 1])
                {
                    int tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                }
                else
                    passed++;
            }
            if (passed == size - 1)
                unsorted = 0;
        }
    }
}
