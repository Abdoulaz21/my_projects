
template <typename T>
bool unique(const std::vector<T> &container)
{
    for (auto it = container.begin(); it != container.end(); it++)
    {
        auto it2 = it + 1;
        for (; it2 != container.end(); it2++)
        {
            if (*it == *it2)
            {
                return false;
            }
        }
    }
    return true;
}
