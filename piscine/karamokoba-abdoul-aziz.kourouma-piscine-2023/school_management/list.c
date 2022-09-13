#include "list.h"

#include <stdlib.h>

struct list *list_init(struct list *list)
{
    list->head = NULL;
    return list;
}

struct list *list_free(struct list *list)
{
    if (list == NULL || list->head == NULL)
        return list;
    else if (list->head == NULL)
    {
        free(list->head);
        list->head = NULL;
    }

    struct list_node *sentinelle = list->head->next;
    while (sentinelle != list->head)
    {
        struct list_node *tmp = sentinelle->next;
        free(sentinelle);
        sentinelle = tmp;
    }
    free(sentinelle);
    list->head = NULL;
    return list;
}

ssize_t list_size(const struct list *list)
{
    if (list == NULL)
        return -1;

    struct list_node *sentinelle = list->head;
    if (sentinelle == NULL)
        return 0;

    ssize_t len = 1;
    while (sentinelle->next != list->head)
    {
        sentinelle = sentinelle->next;
        len++;
    }

    return len;
}

struct list_node *list_append(struct list *list, struct person *person)
{
    if (person == NULL || list == NULL)
        return NULL;

    struct list_node *new = malloc(sizeof(new));
    if (new == NULL)
        abort();
    new->next = NULL;
    new->person = person;

    struct list_node *sentinelle = list->head;
    if (sentinelle == NULL)
    {
        list->head = new;
        new->next = new;
        return new;
    }
    while (sentinelle->next != list->head)
        sentinelle = sentinelle->next;

    sentinelle->next = new;
    new->next = list->head;

    return new;
}

int list_remove(struct list *list, size_t index)
{
    if (list == NULL || list->head == NULL)
        return 0;

    size_t size = list_size(list);
    if (size == 1)
    {
        free(list->head);
        list->head = NULL;
    }
    else if (index == 0)
    {
        struct list_node *current = list->head;
        for (size_t i = 0; current->next != list->head; i++)
        {
            current = current->next;
        }
        current->next = list->head->next;
        free(list->head);
        list->head = current->next;
    }
    size_t new_index = index % size;
    struct list_node *before = NULL;
    struct list_node *current = list->head;
    for (size_t i = 0; i < new_index; i++)
    {
        before = current;
        current = current->next;
    }
    before->next = current->next;
    free(current);

    return 1;
}
