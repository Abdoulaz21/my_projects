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

struct int_vector int_vector_bubble_sort(struct int_vector vec)
{
    if (vec.size > 0)
    {
        size_t passed;
        int unsorted = 1;
        while (unsorted == 1)
        {
            passed = 0;
            for (size_t i = 0; i < vec.size - 1; i++)
            {
                if (*(vec.data + i) > *(vec.data + i + 1))
                {
                    vec = swap(vec, i, i + 1);
                }
                else
                    passed++;
            }
            if (passed == vec.size - 1)
                unsorted = 0;
        }
    }
    return vec;
}
