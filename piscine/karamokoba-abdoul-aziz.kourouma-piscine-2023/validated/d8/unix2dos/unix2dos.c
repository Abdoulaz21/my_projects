#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>

unsigned unix2dos(const char *path_to_file)
{
    int fd;
    if ((fd = open(path_to_file, O_RDWR)) == -1)
        return 1;
    char s;
    size_t lstr = 1;
    char *str = malloc(lstr * sizeof(char));
    size_t pos = 0;
    while (read(fd, &s, 1) == 1)
    {
        if (pos + 2 >= lstr)
        {
            lstr = 2 * lstr + 2;
            str = realloc(str, lstr);
        }
        if (s == '\n' && *(str + pos - 1) != '\r')
        {
            *(str + pos) = '\r';
            pos++;
        }
        *(str + pos) = s;
        pos++;
    }
    *(str + pos) = 0;
    if (lseek(fd, 0, SEEK_SET) == -1)
        return 1;
    if (write(fd, str, pos) == -1)
        return 1;
    free(str);
    if (close(fd) == -1)
        return 1;
    return 0;
}
