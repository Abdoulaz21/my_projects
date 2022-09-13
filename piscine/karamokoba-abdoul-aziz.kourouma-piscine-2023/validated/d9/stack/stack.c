#include <stdio.h>
#include <stdlib.h>

struct stack
{
    int data;
    struct stack *next;
};

struct stack *stack_push(struct stack *s, int e)
{
    if (!s)
    {
        s->data = e;
        s->next = NULL;
    }
    else
    {
        struct stack *tmp = malloc(sizeof(struct stack));
        tmp->data = e;
        tmp->next = s;
        s = tmp;
    }
    return s;
}

struct stack *stack_pop(struct stack *s)
{
    if (!s)
        return NULL;
    struct stack *tmp = malloc(sizeof(struct stack));
    tmp->data = s->data;
    tmp->next = NULL;
    s = s->next;
    free(tmp);
    return s;
}

int stack_peek(struct stack *s)
{
    return s->data;
}
