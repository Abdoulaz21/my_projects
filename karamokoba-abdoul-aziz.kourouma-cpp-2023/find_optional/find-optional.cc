
#include "find-optional.hh"

std::optional<std::size_t> find_optional(const std::vector<int> &vect,
                                         int value)
{
    std::optional<std::size_t> res = std::nullopt;
    std::size_t count = 0;

    for (auto it = vect.begin(); it != vect.end(); ++it)
    {
        if (*it == value)
        {
            res = count;
            break;
        }
        count += 1;
    }

    return res;
}
