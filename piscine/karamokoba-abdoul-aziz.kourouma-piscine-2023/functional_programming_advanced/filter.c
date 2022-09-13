#include <stdbool.h>
#include <stddef.h>
#include <stdlib.h>

void copy_list(int *array, int *list, size_t len)
{
    for (size_t i = 0; i < len; i++)
    {
        *(array + i) = *(list + i);
    }
}

size_t filter(int *array, size_t len, int **out_array, bool (*func)(int))
{
    size_t res = 0;
    int *mylist = calloc(len, sizeof(int));
    for (size_t i = 0; i < len; i++)
    {
        if ((func)(*(array + i)) == true)
        {
            *(mylist + res) = *(array + i);
            res++;
        }
    }
    if (res == 0)
    {
        mylist = NULL;
    }
    else
    {
        mylist = realloc(mylist, res * sizeof(int));
    }
    copy_list(*out_array, mylist, res);
    free(mylist);
    return res;
}
