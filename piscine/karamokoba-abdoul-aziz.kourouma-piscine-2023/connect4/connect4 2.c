#include <stdio.h>

int connect4(char *game[], size_t columns, size_t lines)
{
    int p1 = 0;
    int p2 = 0;
    for (size_t i = 0; i < lines; i++)
    {
        for (size_t j = 0; j < columns; j++)
        {
            if (((columns - j >= 4) && (game[i][j] == game[i][j + 1])
                 && (game[i][j] == game[i][j + 2])
                 && (game[i][j] == game[i][j + 3]))
                || ((lines - i >= 4) && (game[i][j] == game[i + 1][j])
                    && (game[i][j] == game[i + 2][j])
                    && (game[i][j] == game[i + 3][j]))
                || ((lines - i >= 4) && (columns - j >= 4)
                    && (game[i][j] == game[i + 1][j + 1])
                    && (game[i][j] == game[i + 2][j + 2])
                    && (game[i][j] == game[i + 3][j + 3]))
                || ((lines - i >= 4) && (j >= 3)
                    && (game[i][j] == game[i + 1][j - 1])
                    && (game[i][j] == game[i + 2][j - 2])
                    && (game[i][j] == game[i + 3][j - 3])))
            {
                if (game[i][j] == 'X')
                    p1 = 1;
                if (game[i][j] == 'O')
                    p2 = 1;
            }
        }
    }
    int res = 0;
    (p1 > p2) ? res = 1 : ((p1 < p2) ? (res = 2) : (res = 0));
    return res;
}
