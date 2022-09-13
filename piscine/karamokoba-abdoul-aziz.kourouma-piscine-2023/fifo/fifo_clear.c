#include <stdio.h>
#include <stdlib.h>

#include "fifo.h"

void fifo_clear(struct fifo *fifo)
{
    while (fifo->head)
    {
        struct list *tmp = fifo->head;
        fifo->head = fifo->head->next;
        free(tmp);
    }
    fifo->head = NULL;
    fifo->tail = NULL;
    fifo->size = 0;
}
