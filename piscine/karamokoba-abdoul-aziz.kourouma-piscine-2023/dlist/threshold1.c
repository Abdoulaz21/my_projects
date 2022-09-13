#include <stdio.h>
#include <stdlib.h>

#include "dlist.h"

struct dlist *dlist_init(void)
{
    struct dlist *new_list = malloc(sizeof(struct dlist));
    new_list->size = 0;
    new_list->head = NULL;
    new_list->tail = NULL;
    return new_list;
}

void dlist_print(const struct dlist *list)
{
    if (list)
    {
        struct dlist_item *head = list->head;
        struct dlist_item *tail = list->tail;

        do
        {
            printf("%d\n", head->data);
            head = head->next;
        } while (head != tail);
        printf("%d\n", head->data);
    }
}

int dlist_push_front(struct dlist *list, int element)
{
    struct dlist_item *new = malloc(sizeof(struct dlist_item));
    if (new == NULL)
        return 0;

    new->data = element;
    new->prev = NULL;
    list->size += 1;

    if (list->head == NULL)
        list->tail = new;

    struct dlist_item *head = list->head;
    new->next = head;
    if (head != NULL)
        head->prev = new;
    list->head = new;

    return 1;
}

int dlist_push_back(struct dlist *list, int element)
{
    struct dlist_item *new_item = malloc(sizeof(struct dlist_item));
    if (new_item == NULL)
        return 0;
    new_item->data = element;
    new_item->prev = NULL;
    new_item->next = NULL;

    list->size++;
    if (list->tail == NULL)
    {
        list->head = new_item;
        list->tail = new_item;
        return 1;
    }
    struct dlist_item *tail = list->tail;

    tail->next = new_item;
    new_item->prev = tail;
    list->tail = new_item;
    return 1;
}

size_t dlist_size(const struct dlist *list)
{
    return list->size;
}
