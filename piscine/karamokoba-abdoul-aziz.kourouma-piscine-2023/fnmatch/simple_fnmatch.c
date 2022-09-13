#include "simple_fnmatch.h"

#define EOS '\0'

int question_char(const char *pattern, const char *string)
{
    if (*string == EOS)
        return FNM_NOMATCH;
    return simple_fnmatch(pattern + 1, string + 1);
}

int star_char(const char *pattern, const char *string)
{
    if (*(pattern + 1) == 0)
        return 0;
    else
    {
        while (simple_fnmatch(pattern + 1, string) != 0)
        {
            string++;
            if (*string == EOS)
                return FNM_NOMATCH;
        }
        return simple_fnmatch(pattern + 1, string);
    }
}

int escape_char(const char *pattern, const char *string)
{
    if (*(pattern + 1) == *string)
        return simple_fnmatch(pattern + 2, string + 1);
    return FNM_NOMATCH;
}

int simple_fnmatch(const char *pattern, const char *string)
{
    if (*string != EOS)
    {
        switch (*pattern)
        {
        case EOS:
            return FNM_NOMATCH;
        case '?':
            return question_char(pattern, string);
        case '*':
            return star_char(pattern, string);
        case '\\':
            return escape_char(pattern, string);
        default:
            if (*pattern != *string)
                return FNM_NOMATCH;
            return simple_fnmatch(pattern + 1, string + 1);
        }
    }

    if ((*pattern == '*' && *(pattern + 1) == EOS) || *pattern == EOS)
        return 0;

    return FNM_NOMATCH;
}
