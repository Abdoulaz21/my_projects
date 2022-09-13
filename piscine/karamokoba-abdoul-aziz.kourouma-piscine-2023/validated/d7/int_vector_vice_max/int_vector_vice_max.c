#include <stdio.h>

#define INT_VECTOR_LENGTH 64

struct int_vector
{
    size_t size;
    int data[INT_VECTOR_LENGTH];
};

int int_vector_vice_max(const struct int_vector vec)
{
    int M;
    int m;
    if (*(vec.data) > *(vec.data + 1))
    {
        M = *(vec.data);
        m = *(vec.data + 1);
    }
    else if (*(vec.data) < *(vec.data + 1))
    {
        M = *(vec.data + 1);
        m = *(vec.data);
    }
    for (size_t i = 2; i < vec.size; i++)
    {
        if (M < *(vec.data + i))
        {
            m = M;
            M = *(vec.data + i);
        }
        if (m < *(vec.data + i) && M > *(vec.data + i))
            m = *(vec.data + i);
    }
    return m;
}
