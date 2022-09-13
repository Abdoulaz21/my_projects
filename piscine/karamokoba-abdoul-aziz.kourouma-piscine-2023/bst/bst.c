#include "bst.h"

#include <stdlib.h>

struct bst_node *create_node(int value)
{
    struct bst_node *res = malloc(sizeof(struct bst_node));
    res->data = value;
    res->left = NULL;
    res->right = NULL;
    return res;
}

struct bst_node *add_node(struct bst_node *tree, int value)
{
    struct bst_node *val = create_node(value);
    if (tree == NULL)
        return val;
    if (tree->data == value)
        return tree;
    struct bst_node *tmp = tree;
    while (tmp)
    {
        if (tmp->left == NULL)
        {
            tmp->left = val;
            break;
        }
        if (tmp->left->data > value)
            tmp = tmp->left;
        if (tmp->right == NULL)
        {
            tmp->right = val;
            break;
        }
        if (tmp->right->data < value)
            tmp = tmp->right;
    }
    return tree;
}

struct bst_node *delete (struct bst_node *tree, int value)
{
    if (tree == NULL)
        return NULL;
    if (tree->data == value)
    {
        return tree;
    }
    else if (tree->data < value)
        return delete (tree->left, value);
    else
        return delete (tree->right, value);
}

void rearrange_left(struct bst_node *tree)
{
    struct bst_node *tleft = tree->left;
    if (tleft->right == NULL)
    {
        tleft->right = tree->right;
        tree = tleft;
    }
    struct bst_node *tmp = tleft->right;
    struct bst_node *father = tleft;
    while (tmp->right)
    {
        father = tmp;
        tmp = tmp->right;
    }
    if (tmp->left)
        father->right = tmp->left;
    tmp->left = tleft;
    tmp->right = tree->right;
    tree = tmp;
}

void rearrange_right(struct bst_node *tree)
{
    struct bst_node *tright = tree->right;
    if (tright->left == NULL)
    {
        tright->left = tree->left;
        tree = tright;
    }
    struct bst_node *tmp = tright->left;
    struct bst_node *father = tright;
    while (tmp->left)
    {
        father = tmp;
        tmp = tmp->left;
    }
    if (tmp->right)
        father->left = tmp->right;
    tmp->right = tright;
    tmp->left = tree->left;
    tree = tmp;
}

struct bst_node *delete_node(struct bst_node *tree, int value)
{
    struct bst_node *root = delete (tree, value);
    if (root)
    {
        if (root->left)
            rearrange_left(root);
        else if (root->right)
            rearrange_right(root);
        else
            root = NULL;
        return tree;
    }
    return NULL;
}

const struct bst_node *find(const struct bst_node *tree, int value)
{
    if (tree == NULL)
        return NULL;
    if (tree->data == value)
        return tree;
    else if (tree->data < value)
        return find(tree->left, value);
    else
        return find(tree->right, value);
}

void free_bst(struct bst_node *tree)
{
    if (tree)
    {
        if (tree->right)
            free_bst(tree->right);
        if (tree->left)
            free_bst(tree->left);
    }
    free(tree);
}
