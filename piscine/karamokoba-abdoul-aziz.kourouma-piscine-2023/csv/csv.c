#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv)
{
    if (argc != 2)
        return 1;
    FILE *csv_file = fopen(*(argv + 1), "r");
    if (!csv_file)
        return 1;
    ssize_t nread;
    char *line = NULL;
    size_t len = 0;
    while ((nread = getline(&line, &len, csv_file)) != -1)
    {
        int max = 0;
        char *str;
        char *saveptr;
        char *token;
        int i;
        for (i = 0, str = line;; i++, str = NULL)
        {
            if ((token = strtok_r(str, ",", &saveptr)) == NULL)
                break;
            if (i == 0)
                max = atoi(token);
            if (atoi(token) > max)
                max = atoi(token);
        }
        printf("%d\n", max);
    }
    free(line);
    fclose(csv_file);
    return 0;
}
