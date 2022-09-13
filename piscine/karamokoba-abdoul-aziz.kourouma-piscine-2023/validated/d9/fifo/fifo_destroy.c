#include <stdio.h>
#include <stdlib.h>

#include "fifo.h"

void fifo_destroy(struct fifo *fifo)
{
    if (fifo->head && fifo->tail)
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
    free(fifo->head);
    free(fifo->tail);
    free(fifo);
}
