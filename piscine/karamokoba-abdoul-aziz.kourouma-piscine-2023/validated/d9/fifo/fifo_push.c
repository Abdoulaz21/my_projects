#include <stdio.h>
#include <stdlib.h>

#include "fifo.h"

void fifo_push(struct fifo *fifo, int elt)
{
    if (fifo->size == 0)
    {
        struct list *tmp = malloc(sizeof(struct list));
        tmp->data = elt;
        tmp->next = NULL;
        fifo->head = tmp;
        fifo->tail = tmp;
    }
    else if (fifo->size == 1)
    {
        struct list *tmp = malloc(sizeof(struct list));
        tmp->data = elt;
        tmp->next = NULL;
        fifo->tail = tmp;
        fifo->head->next = fifo->tail;
    }
    else
    {
        struct list *tmp = malloc(sizeof(struct list));
        if (!tmp)
            return;
        tmp->data = elt;
        tmp->next = NULL;
        fifo->tail->next = tmp;
        fifo->tail = tmp;
        tmp = NULL;
        free(tmp);
    }
    fifo->size += 1;
}
