#include <stdio.h>
#include <stdlib.h>

#include "fifo.h"

void fifo_print(const struct fifo *fifo)
{
    if (fifo->size != 0)
    {
        size_t s = fifo->size;
        printf("%d", fifo->head->data);
        struct list *tmp = fifo->head->next;
        while (s != 0)
        {
            if (!tmp)
                break;
            printf("-%d", tmp->data);
            tmp = tmp->next;
            s--;
        }
    }
    puts("");
}
