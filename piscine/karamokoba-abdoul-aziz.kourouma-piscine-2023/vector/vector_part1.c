#include <stdio.h>
#include <stdlib.h>

#include "vector.h"

struct vector *vector_init(size_t n)
{
    struct vector *res = malloc(sizeof(struct vector));
    res->capacity = n;
    res->size = 0;
    int *lst = malloc(n * sizeof(int));
    if (!lst)
        return NULL;
    res->data = lst;

    return res;
}

void vector_destroy(struct vector *v)
{
    if (v)
    {
        free(v->data);
        v->data = NULL;
    }
    free(v);
}

struct vector *vector_resize(struct vector *v, size_t n)
{
    if (n != v->capacity)
    {
        v->data = realloc(v->data, n * sizeof(int));
        if (n > v->capacity)
        {
            v->capacity = n;
            for (size_t e = v->size; e < n; e++)
            {
                *(v->data + e) = 0;
            }
        }
        else
        {
            v->size = n;
            v->capacity = n;
        }
    }

    return v;
}

struct vector *vector_reset(struct vector *v, size_t n)
{
    vector_destroy(v);
    struct vector *res = vector_init(n);
    if (!res)
        return NULL;
    return res;
}
