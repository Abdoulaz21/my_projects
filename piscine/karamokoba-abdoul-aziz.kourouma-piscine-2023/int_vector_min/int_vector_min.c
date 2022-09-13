#include <stdio.h>

#define INT_VECTOR_LENGTH 64

struct int_vector
{
    size_t size;
    int data[INT_VECTOR_LENGTH];
};

int int_vector_min(struct int_vector vec)
{
    int m = *(vec.data);
    for (size_t i = 1; i < vec.size; i++)
    {
        if (m > *(vec.data + i))
            m = *(vec.data + i);
    }
    return m;
}
