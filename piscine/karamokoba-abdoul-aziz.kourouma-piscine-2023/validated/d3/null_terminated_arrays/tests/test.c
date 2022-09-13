#include <criterion/criterion.h>
#include <criterion/redirect.h>
#include <stdio.h>

#include "null_terminated_arrays.h"

static const char *empty[] = { NULL };

static const char *digits[] = {
    "zero", "one",   "two",   "three", "four", "five",
    "six",  "seven", "eight", "nine",  NULL,
};

static const char *more_digits[] = {
    "ten",     "eleven",    "twelve",   "thirteen", "fourteen", "fifteen",
    "sixteen", "seventeen", "eighteen", "nineteen", NULL,
};

static const char *even_more_digits[] = {
    "twenty",       "twenty one",  "twenty two", "twenty three",
    "twenty four",  "twenty five", "twenty six", "twenty seven",
    "twenty eight", "twenty nine", NULL,
};

static const char *random[] = {
    "RYZ0KMbVdBC", "ZCdJ7F",    "uOPqpaXkd",   "QqUWQFM",     "xU209rWw",
    "7",           "",          "dQ",          "zCqGhveNNwv", "QwsJA7",
    "zSCuXkv9F",   "LMsD8w+",   "zgZxu4ra",    "h",           "yG2DHy",
    "C7B5FvYsBEp", "siB7QS",    "D/EpChTge",   "qvZ5Cys",     "KPQaXpXt",
    "P",           "u9hBvb",    "utVOa4bSang", "ekOtwP",      "zeCtrVnyw",
    "64QzHvT",     "K6mIIx6y",  "0",           "9Rgv2J",      "oBbbRQPqDyg",
    "DbPu2k",      "MnrXm1rRF", "Nd7mFf9",     "S7MlkfHi",    "m",
    "2loRTo",      NULL,
};

static const char **tricky_matrix[] = { empty, NULL };

static const char **large_matrix[] = { digits, more_digits, even_more_digits,
                                       random, NULL };

static const char **medium_matrix[] = { digits, more_digits, even_more_digits,
                                        NULL };

static const char **small_matrix[] = { digits, NULL };

void reverse_matrix(const char ***matrix);

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

static void check_reverse(const char ***matrix, const char *expected)
{
    reverse_matrix(matrix);
    dump_matrix(matrix);
    fflush(stdout);
    cr_assert_stdout_eq_str(expected);
}

TestSuite(null_terminated_arrays, .init = cr_redirect_stdout, .timeout = 15);

Test(null_terminated_arrays, small)
{
    char *expected =
        "{\n"
        "  { nine, eight, seven, six, five, four, three, two, one, zero, }\n"
        "}\n";
    check_reverse(small_matrix, expected);
}

Test(null_terminated_arrays, medium)
{
    char *expected =
        "{\n"
        "  { twenty nine, twenty eight, twenty seven, twenty six, twenty five, "
        "twenty four, twenty three, twenty two, twenty one, twenty, }\n"
        "  { nineteen, eighteen, seventeen, sixteen, fifteen, fourteen, "
        "thirteen, twelve, eleven, ten, }\n"
        "  { nine, eight, seven, six, five, four, three, two, one, zero, }\n"
        "}\n";
    check_reverse(medium_matrix, expected);
}

Test(null_terminated_arrays, large)
{
    char *expected =
        "{\n"
        "  { 2loRTo, m, S7MlkfHi, Nd7mFf9, MnrXm1rRF, DbPu2k, oBbbRQPqDyg, "
        "9Rgv2J, 0, K6mIIx6y, 64QzHvT, zeCtrVnyw, ekOtwP, utVOa4bSang, u9hBvb, "
        "P, KPQaXpXt, qvZ5Cys, D/EpChTge, siB7QS, C7B5FvYsBEp, yG2DHy, h, "
        "zgZxu4ra, LMsD8w+, zSCuXkv9F, QwsJA7, zCqGhveNNwv, dQ, , 7, xU209rWw, "
        "QqUWQFM, uOPqpaXkd, ZCdJ7F, RYZ0KMbVdBC, }\n"
        "  { twenty nine, twenty eight, twenty seven, twenty six, twenty five, "
        "twenty four, twenty three, twenty two, twenty one, twenty, }\n"
        "  { nineteen, eighteen, seventeen, sixteen, fifteen, fourteen, "
        "thirteen, twelve, eleven, ten, }\n"
        "  { nine, eight, seven, six, five, four, three, two, one, zero, }\n"
        "}\n";
    check_reverse(large_matrix, expected);
}

Test(null_terminated_arrays, tricky)
{
    char *expected = "{\n"
                     "  { }\n"
                     "}\n";
    check_reverse(tricky_matrix, expected);
}

int main(int argc, char *argv[])
{
    struct criterion_test_set *tests = criterion_initialize();

    int result = 0;
    if (criterion_handle_args(argc, argv, true))
        result = !criterion_run_all_tests(tests);

    criterion_finalize(tests);
    return result;
}
