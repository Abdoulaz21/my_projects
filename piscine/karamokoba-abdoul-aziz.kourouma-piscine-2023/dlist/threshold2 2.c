#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#include "dlist.h"

int dlist_get(struct dlist *list, size_t index)
{
    if (list->size <= index)
        return -1;

    struct dlist_item *sentinelle = list->head;
    for (size_t i = 0; i != index; i++)
        sentinelle = sentinelle->next;

    return sentinelle->data;
}

int dlist_insert_at(struct dlist *list, int element, size_t index)
{
    if (element < 0 || index > list->size)
        return -1;

    struct dlist_item *new = malloc(sizeof(struct dlist_item));
    new->data = element;

    if (index == 0)
        dlist_push_front(list, element);
    else if (index == list->size)
        dlist_push_back(list, element);
    else
    {
        struct dlist_item *sentinelle = list->head;
        for (size_t i = 0; i < index; i++)
        {
            sentinelle = sentinelle->next;
        }
        struct dlist_item *tmp = sentinelle->next;
        sentinelle->next = new;
        tmp->prev = new;
        new->prev = sentinelle;
        new->next = tmp;
    }

    return 1;
}

int dlist_find(const struct dlist *list, int element)
{
    if (list == NULL)
        return -1;

    struct dlist_item *sentinelle = list->head;
    int index = 0;
    while (sentinelle != NULL)
    {
        if (sentinelle->data == element)
            return index;
        index++;
        sentinelle = sentinelle->next;
    }

    return -1;
}

int dlist_remove_at(struct dlist *list, size_t index)
{
    if (index >= list->size)
        return -1;

    struct dlist_item *tmp = list->head;

    for (size_t i = 0; i < index; i++)
    {
        tmp = tmp->next;
    }
    int res = tmp->data;
    if (tmp->next)
        (tmp->next)->prev = tmp->prev;
    else
    {
        list->tail = tmp->prev;
        if (list->tail)
            (list->tail)->next = NULL;
    }
    if (tmp->prev)
        (tmp->prev)->next = tmp->next;
    else
    {
        list->head = tmp->next;
        if (list->head)
            (list->head)->prev = NULL;
    }
    free(tmp);
    list->size -= 1;
    return res;
}
