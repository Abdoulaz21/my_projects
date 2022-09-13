#include <stdio.h>

size_t len_big(const char ***matrix)
{
    size_t res = 0;
    while (matrix[res] != NULL)
    {
        res++;
    }
    return res;
}

size_t len_lil(const char **string)
{
    size_t res = 0;
    while (string[res] != NULL)
    {
        res++;
    }
    return res;
}

void reverse(const char **lst)
{
    size_t ll = len_lil(lst);
    for (size_t i = 0; i <= ((ll / 2) - 1); i++)
    {
        const char *tp = lst[i];
        lst[i] = lst[ll - i - 1];
        lst[ll - i - 1] = tp;
    }
}

void reverse_matrix(const char ***matrix)
{
    if (*matrix[0] == NULL)
        return;
    size_t len_mat = len_big(matrix);
    if (len_mat < 2)
    {
        reverse(matrix[0]);
    }
    for (size_t i = 0; i <= len_mat / 2 - 1; i++)
    {
        reverse(matrix[i]);
        reverse(matrix[len_mat - i - 1]);
        const char **tmp = matrix[i];
        matrix[i] = matrix[len_mat - i - 1];
        matrix[len_mat - i - 1] = tmp;
    }
    if (len_mat % 2 != 0)
    {
        reverse(matrix[len_mat / 2]);
    }
}

static void dump_matrix(const char ***matrix)
{
    printf("{\n");
    for (const char ***elt = matrix; *elt; ++elt)
    {
        printf("  { ");
        for (const char **str = *elt; *str; ++str)
            printf("%s, ", *str);
        printf("}\n");
    }
    printf("}\n");
}

/*int main(void)
**{
**    const char *line1[] = {"1", "2", "3", NULL};
**    const char **matrix[] =
**    {
**        line1, NULL
**    };
**
**    dump_matrix(matrix);
**    reverse_matrix(matrix);
**    dump_matrix(matrix);
**    return 0;
**}
*/
