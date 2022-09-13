#include <stdio.h>
#include <stdlib.h>

#include "fifo.h"

void fifo_pop(struct fifo *fifo)
{
    if (fifo->head)
    {
        if (fifo->head->next != fifo->tail)
        {
            struct list *tmp = fifo->head;
            fifo->head = tmp->next;
            free(tmp);
            fifo->size -= 1;
        }
        else
        {
            fifo->head = NULL;
            fifo->tail = NULL;
            fifo->size = 0;
        }
    }
}
