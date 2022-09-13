#include <stdio.h>

#define INT_VECTOR_LENGTH 64
struct int_vector
{
    size_t size;
    int data[INT_VECTOR_LENGTH];
};

int int_vector_hill(struct int_vector vec)
{
    int res = -1;

    if (vec.size < 1)
        return -1;
    else if (vec.size == 1)
        return 0;
    else
    {
        size_t i = 0;
        for (; i < vec.size - 1; i++)
        {
            if (*(vec.data + i) < 0 || *(vec.data + i + 1) < 0)
                return -1;
            if (*(vec.data + i) >= *(vec.data + i + 1) && res == -1)
                res = i;
            if (*(vec.data + i) < *(vec.data + i + 1) && res != -1)
                return -1;
        }
        if (i == vec.size - 1 && res == -1)
            res = vec.size - 1;
    }
    return res;
}
