#include "list.h"

#include <stddef.h>
#include <stdlib.h>
#include <string.h>

struct list *list_prepend(struct list *list, const void *value,
                          size_t data_size)
{
    struct list *new = malloc(sizeof(struct list));
    if (new == NULL)
        return NULL;
    new->data = malloc(data_size);
    if (new->data == NULL)
        return NULL;
    memcpy(new->data, value, data_size);
    new->next = list;
    return new;
}

size_t list_length(struct list *list)
{
    size_t length = 0;
    while (list)
    {
        list = list->next;
        length++;
    }
    return length;
}

void list_destroy(struct list *list)
{
    if (list)
    {
        list_destroy(list->next);
        free(list->data);
        free(list);
    }
}
