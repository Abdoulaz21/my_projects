#include <stdio.h>
#include <stdlib.h>

#include "node.h"

struct list
{
    struct list *next;
    struct bst_node *node;
};

void parcour_en_profondeur(struct bst_node *root, struct list *data)
{
    if (root == NULL)
        return;

    struct list *new = malloc(sizeof(struct list));
    new->next = NULL;
    new->node = root;

    struct list *current = data;
    parcour_en_profondeur(root->left, data);
    while (current->next != NULL)
    {
        current = current->next;
    }
    current->next = new;
    parcour_en_profondeur(root->right, data);
}

struct list *newarr(void)
{
    struct list *res = malloc(sizeof(struct list));
    res->next = NULL;
    res->node = NULL;
    return res;
}

int find_pair(struct bst_node *root, int k)
{
    int number_of_pairs = 0;
    struct list *arr = newarr();

    parcour_en_profondeur(root, arr);

    struct list *tmp1 = arr->next;
    struct list *tmp2 = arr->next;

    for (int i = 0; tmp1 != NULL; i++)
    {
        for (int j = i + 1; tmp2 != NULL; j++)
        {
            if (tmp1->node->data + tmp2->node->data == k)
            {
                number_of_pairs++;
                printf("%d %d\n", tmp1->node->data, tmp2->node->data);
            }
            tmp2 = tmp2->next;
        }
        tmp1 = tmp1->next;
        tmp2 = tmp1->next;
        if (tmp2 == NULL || tmp1 == NULL)
            break;
    }

    while (arr->next != NULL)
    {
        struct list *tmp = arr->next;
        free(arr);
        arr = tmp;
    }
    free(arr);

    if (number_of_pairs > 0)
        return 1;
    return 0;
}
