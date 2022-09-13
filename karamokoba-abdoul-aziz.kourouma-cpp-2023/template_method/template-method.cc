#include "template-method.hh"

#include <utility>

void AbstractSort::sort(std::vector<int> &v) const
{
    for (auto first = v.begin(); first != v.end(); first++)
    {
        for (auto second = first + 1; second != v.end(); second++)
        {
            if (need_swap(*first, *second))
                std::swap(*first, *second);
        }
    }
}

bool IncreasingOrderSort::need_swap(int a, int b) const
{
    return (a >= b);
}

bool DecreasingOrderSort::need_swap(int a, int b) const
{
    return (a <= b);
}
