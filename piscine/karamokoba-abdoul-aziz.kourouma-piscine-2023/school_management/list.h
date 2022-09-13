#ifndef LIST_H
#define LIST_H

#include <stddef.h>
#include <sys/types.h>

#include "person.h"

struct list_node
{
    struct person *person;
    struct list_node *next;
};

struct list
{
    struct list_node *head;
};

struct list *list_init(struct list *list);
struct list *list_free(struct list *list);

ssize_t list_size(const struct list *list);
struct list_node *list_append(struct list *list, struct person *person);
int list_remove(struct list *list, size_t index);
int list_dump(const struct list *list);
int list_dump_ordered(const struct list *list);
ssize_t list_count(const struct list *list, enum person_type type);
double list_revenue(const struct list *list);
double list_average(const struct list *list, size_t promotion);

#endif
