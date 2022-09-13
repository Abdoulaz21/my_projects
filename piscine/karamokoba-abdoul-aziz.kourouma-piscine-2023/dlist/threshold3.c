#include <stddef.h>
#include <stdlib.h>

#include "dlist.h"

void dlist_map_square(struct dlist *list)
{
    struct dlist_item *sentinelle = list->head;

    while (sentinelle != NULL)
    {
        sentinelle->data = sentinelle->data * sentinelle->data;
        sentinelle = sentinelle->next;
    }
}

void dlist_reverse(struct dlist *list)
{
    struct dlist_item *sentinelle = list->head;

    while (sentinelle != NULL)
    {
        struct dlist_item *tmp = sentinelle->prev;
        sentinelle->prev = sentinelle->next;
        sentinelle->next = tmp;

        sentinelle = sentinelle->prev;
    }
    struct dlist_item *tmp2 = list->head;
    list->head = list->tail;
    list->tail = tmp2;
}

struct dlist *dlist_split_at(struct dlist *list, size_t index)
{
    if (index >= list->size)
    {
        return NULL;
    }

    struct dlist *new = malloc(sizeof(struct dlist));
    new->size = list->size - index;
    struct dlist_item *sentinelle = list->head;

    for (size_t i = 0; i < index; i++)
        sentinelle = sentinelle->next;

    new->head = sentinelle;
    new->tail = list->tail;
    if (index == 0)
    {
        list->head = NULL;
        list->tail = NULL;
        list->size = 0;
    }
    else
    {
        struct dlist_item *prev = sentinelle->prev;
        prev->next = NULL;
        list->tail = prev;
        list->size = index;
    }

    return new;
}

void dlist_concat(struct dlist *list1, struct dlist *list2)
{
    if (list2->size == 0)
        return;

    if (list1 == NULL)
    {
        list1->head = list2->head;
        list1->tail = list2->tail;
        list1->size = list2->size;
        list2->head = NULL;
        list2->tail = NULL;
        list2->size = 0;
        return;
    }
    struct dlist_item *tail1 = list1->tail;
    struct dlist_item *head2 = list2->head;
    head2->prev = tail1;
    tail1->next = head2;
    list1->tail = list2->tail;
    list1->size = list1->size + list2->size;
    list2->head = NULL;
    list2->tail = NULL;
    list2->size = 0;
}
/*
int main(void)
{
    struct dlist_item head1;
    struct dlist_item tail1 = {.data=4, .prev=&head1 , .next=NULL};
    head1.data = 6;
    head1.prev=NULL;
    head1.next=&tail1;
    struct dlist list1 = { .size = 2, .head=&head1, .tail=&tail1};

    struct dlist_item head2;
    struct dlist_item tail2 = {.data=8, .prev=&head2 , .next=NULL};
    head1.data = 10;
    head1.prev=NULL;
    head1.next=&tail2;


    struct dlist list2 = { .size = 2, .head=&head2, .tail=&tail2};
    dlist_concat(&list1, &list2);

    return 0;
}*/
