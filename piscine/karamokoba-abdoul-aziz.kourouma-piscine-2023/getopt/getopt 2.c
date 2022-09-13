#include <err.h>
#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

static struct option long_options[] = {
    { "artist", required_argument, NULL, 'a' },
    { "year", required_argument, NULL, 'y' },
    { NULL, 0, NULL, 0 }
};

int main(int argc, char **argv)
{
    int ch;
    char *title = NULL;
    char *artist = NULL;
    char *year = NULL;
    while ((ch = getopt_long(argc, argv, "a:y:", long_options, &optind)) != -1)
    {
        switch (ch)
        {
        case 'a':
            artist = optarg;
            break;
        case 'y':
            year = optarg;
            break;
        default:
            break;
        }
    }
    if (artist == NULL && year == NULL)
        return 1;

    if ((title = *(argv + optind)) == NULL)
    {
        warnx("getopt: missing song title");
        return 1;
    }
    printf("Song name: %s\n", title);
    if (artist != NULL)
        printf("By: %s\n", artist);
    if (year != NULL)
        printf("Year: %s\n", year);

    return 0;
}
