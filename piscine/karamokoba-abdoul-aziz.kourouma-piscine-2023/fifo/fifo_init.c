#include <stdio.h>
#include <stdlib.h>

#include "fifo.h"

struct fifo *fifo_init(void)
{
    struct fifo *res = malloc(sizeof(struct fifo));
    if (!res)
        return NULL;
    res->tail = NULL;
    res->head = NULL;
    res->size = 0;
    return res;
}
