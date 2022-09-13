#include <stdio.h>

size_t max_int(int *list)
{
    size_t res = 0;
    for (size_t i = 1; i < 26; i++)
    {
        if (*(list + i) > *(list + res))
            res = i;
    }
    return res;
}

size_t size(const char table[])
{
    size_t res = 0;
    while (*(table + res) != 0)
    {
        res++;
    }
    return res;
}

void freq_analysis(const char text[], const char table[])
{
    // char res[4 * size(table) + 1];
    int freq[26] = { 0 };
    for (size_t i = 0; *(text + i) != 0; i++)
    {
        size_t val = *(text + i) - 'A';
        *(freq + val) += 1;
    }
    char order[26];
    for (size_t l = 0; l < 26; l++)
    {
        *(order + l) = 'a';
    }
    int pos = 0;
    for (size_t j = 0; j < size(table); j++)
    {
        size_t tmp = max_int(freq);
        *(order + tmp) = *(table + pos);
        pos++;
        *(freq + tmp) = 0;
    }
    // size_t p = 0;
    for (size_t k = 0; k < 26; k++)
    {
        if (*(order + k) != 'a')
        {
            char cc = 'A' + k;
            //*(res + p) = cc;
            //*(res + p + 1) = ' ';
            //*(res + p + 2) = *(order + k);
            //*(res + p + 3) = '\n';
            // p += 4;
            printf("%c %c\n", cc, *(order + k));
        }
    }
    //*(res + (4 * size(table))) = '\0';
    // printf("%s",res);
}

int main(void)
{
    printf("chaine 1\n");
    freq_analysis("FXOWFFOWOFF", "ABCD");
    printf("chaine 2\n");
    freq_analysis("ABBCCCDDDD", "ABCD");
    printf("chaine 3\n");
    freq_analysis("AAB", "XY");
    return 0;
}
