#include "bst_static.h"

#include <stddef.h>
#include <stdlib.h>

struct bst *init(size_t capacity)
{
    struct bst *res = malloc(sizeof(struct bst));
    res->size = 0;
    res->capacity = capacity;
    res->data = malloc(capacity * sizeof(struct value));
    return res;
}

void add(struct bst *tree, int value)
{
    if (tree->size == tree->capacity)
        tree->data =
            realloc(tree->data, 2 * tree->capacity * sizeof(struct value));
    if (tree->size == 0)
        tree->data[0]->val = value;
    else
    {
        size_t pos = 0;
        while ((2 * pos) + 1 < tree->size)
        {
            if (tree->data[pos]->val < value)
                pos = 2 * pos + 1;
            if (tree->data[pos]->val > value)
                pos = 2 * pos + 2;
        }
        tree->data[pos]->val = value;
        tree->size += 1;
    }
}

int search(struct bst *tree, int value)
{
    size_t res = 0;
    while (res < tree->size)
    {
        if (tree->data[res]->val > value)
            res = res * 2 + 1;
        else if (tree->data[res]->val < value)
            res = 2 * (res + 1);
        else
            return res;
    }
    return -1;
}

void bst_free(struct bst *tree)
{
    free(tree->data);
    free(tree);
}
