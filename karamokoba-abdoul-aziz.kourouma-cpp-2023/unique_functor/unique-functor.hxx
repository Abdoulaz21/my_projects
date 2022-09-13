template <class ForwardIt, class BinaryPredicate>
static ForwardIt find_char(ForwardIt first, ForwardIt last, BinaryPredicate p)
{
    auto elt = first + 1;
    for (; elt != last; elt++)
    {
        if (!p(*(elt - 1), *elt))
        {
            return elt;
        }
    }
    return last;
}

template <class ForwardIt, class BinaryPredicate>
ForwardIt my_unique(ForwardIt first, ForwardIt last, BinaryPredicate p)
{
    auto elt = first + 1;
    for (; elt != last; elt++)
    {
        if (p(*(elt - 1), *elt))
        {
            ForwardIt begin = find_char(elt - 1, last, p);
            if (begin == last)
            {
                break;
            }

            auto tmp = elt;
            for (; tmp != begin; tmp++)
            {
                *tmp = *begin;
            }
        }
    }

    return elt;
}
