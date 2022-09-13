#include <stdio.h>

#define INT_VECTOR_LENGTH 64
struct int_vector
{
    size_t size;
    int data[INT_VECTOR_LENGTH];
};

static struct int_vector swap(struct int_vector vec, size_t i, size_t j)
{
    int tmp = *(vec.data + i);
    *(vec.data + i) = *(vec.data + j);
    *(vec.data + j) = tmp;
    return vec;
}

struct int_vector int_vector_insert_sort(struct int_vector vec)
{
    size_t i = 0;
    while (i != vec.size)
    {
        for (size_t j = i; j > 0; j--)
        {
            if (*(vec.data + j) < *(vec.data + j - 1))
            {
                vec = swap(vec, j, j - 1);
            }
        }
        i++;
    }
    return vec;
}
