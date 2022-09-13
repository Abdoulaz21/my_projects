#include <stdio.h>
#include <stdlib.h>

typedef int (*f_cmp)(const void *, const void *);
void insertion_sort(void **array, f_cmp cmp)
{
    size_t i = 0;
    while (*(array + i) != NULL)
    {
        for (size_t j = i; j > 0; j--)
        {
            if (cmp(*(array + j), *(array + j - 1)) < 0)
            {
                void *tmp = *(array + j);
                *(array + j) = *(array + j - 1);
                *(array + j - 1) = tmp;
            }
        }
        i++;
    }
}
