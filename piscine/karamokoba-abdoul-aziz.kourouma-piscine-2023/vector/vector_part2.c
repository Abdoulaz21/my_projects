#include <stdio.h>
#include <stdlib.h>

#include "vector.h"

void vector_print(const struct vector *v)
{
    if (v && v->size > 0)
    {
        printf("%d", *(v->data));
        for (size_t i = 1; i < v->size; i++)
        {
            printf(",%d", *(v->data + i));
        }
    }
    putchar('\n');
}

struct vector *vector_append(struct vector *v, int elt)
{
    if (!v)
    {
        return NULL;
    }
    else
    {
        if (v->size == v->capacity)
        {
            v->capacity *= 2;
            v->data = realloc(v->data, v->capacity * sizeof(int));
        }
        if (!v)
            return NULL;
        v->size += 1;
        *(v->data + v->size - 1) = elt;
    }
    return v;
}

struct vector *vector_insert(struct vector *v, size_t i, int elt)
{
    if (!v)
        return NULL;
    if (i == v->size)
        return vector_append(v, elt);
    if (!v || i > v->size)
        return NULL;
    if (v->size == v->capacity)
    {
        v->capacity *= 2;
        v->data = realloc(v->data, v->capacity * sizeof(int));
    }
    v->size += 1;
    for (size_t e = v->size - 1; e > i; e--)
    {
        *(v->data + e) = *(v->data + e - 1);
    }
    *(v->data + i) = elt;

    return v;
}

struct vector *vector_remove(struct vector *v, size_t i)
{
    if (!v || i >= v->size)
        return NULL;
    for (size_t e = i; e < v->size; e++)
    {
        *(v->data + e) = *(v->data + e + 1);
    }
    *(v->data + v->size) = 0;
    v->size -= 1;
    v->capacity = v->size + 1;
    return v;
}
