#include <stdio.h>

struct list
{
    int data;
    struct list *next;
};

void list_print(struct list *l)
{
    if (l)
    {
        printf("%d", l->data);
        while ((l = l->next))
        {
            printf(" %d", l->data);
        }
    }
    printf("\n");
}
