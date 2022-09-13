#include <stdio.h>

int *binary_search(int *begin, int *end, int elt)
{
    if (begin == end)
        return begin;
    int mid = (end - begin - 1) / 2;
    if (*(begin + mid) == elt)
        return begin + mid;
    else if (*(begin + mid) < elt)
        return binary_search(begin + mid + 1, end, elt);
    else
        return binary_search(begin, begin + mid, elt);
}
