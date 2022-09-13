#include "list.h"

#include <stdio.h>
#include <stdlib.h>

struct list *list_add(struct list *l, int e)
{
    struct list *tmp = malloc(sizeof(struct list));
    tmp->data = e;
    tmp->next = l;
    l = tmp;
    return l;
}

struct list *list_find(struct list *l, int e)
{
    if (!l)
        return NULL;
    struct list *tmp = l;
    while (tmp->data != e)
    {
        if (!tmp->next)
            return NULL;
        tmp = tmp->next;
    }
    return tmp;
}

struct list *list_remove(struct list *l, int e)
{
    if (!l)
        return NULL;
    if (l->data == e)
    {
        struct list *res = l->next;
        free(l);
        return res;
    }
    struct list *tmp = l;
    struct list *prev = tmp;
    while (tmp->data != e && tmp->next != NULL)
    {
        prev = tmp;
        tmp = tmp->next;
    }
    if (tmp->data == e)
    {
        prev->next = tmp->next;
        free(tmp);
    }
    return l;
}

struct list *list_reverse_sorted_add(struct list *l, int e)
{
    if (!l || l->data <= e)
    {
        struct list *res = list_add(l, e);
        return res;
    }
    struct list *elm = malloc(sizeof(struct list));
    if (!elm)
        return l;
    elm->data = e;
    struct list *tmp = l;
    while (tmp->next && tmp->next->data > e)
    {
        tmp = tmp->next;
    }
    elm->next = tmp->next;
    tmp->next = elm;
    return l;
}

struct list *list_remove_if(struct list *l, int (*predicate)(int))
{
    if (!l)
        return NULL;
    struct list *tmp = l;
    while (tmp != NULL)
    {
        if (predicate(tmp->data) == 1)
            l = list_remove(l, tmp->data);
        tmp = tmp->next;
    }
    return l;
}
