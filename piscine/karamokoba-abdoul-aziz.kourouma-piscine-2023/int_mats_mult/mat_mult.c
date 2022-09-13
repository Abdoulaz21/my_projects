#include <stdio.h>

int add_val(int **mat1, int **mat2, size_t *l)
{
    int res = 0;
    for (size_t i = 0; i < *(l + 2); i++)
    {
        res += mat1[*l][i] * mat2[i][*(l + 1)];
    }
    return res;
}

void mat_mult(int **mat1, int **mat2, size_t *matrices_size, int **out)
{
    for (size_t i = 0; i < *matrices_size; i++)
    {
        for (size_t j = 0; j < *(matrices_size + 2); j++)
        {
            size_t my_l[3] = { i, j, *(matrices_size + 1) };
            out[i][j] = add_val(mat1, mat2, my_l);
        }
    }
}
