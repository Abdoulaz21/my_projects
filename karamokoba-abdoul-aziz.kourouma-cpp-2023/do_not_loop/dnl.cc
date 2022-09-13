#include "dnl.hh"

size_t min_elt_length(const std::vector<std::string> &req)
{
    auto f_cmp = [](const std::string &lhs, const std::string &rhs) {
        return lhs.size() < rhs.size();
    };
    size_t min_element =
        std::min_element(req.begin(), req.end(), f_cmp)->size();
    return min_element;
}

size_t max_elt_length(const std::vector<std::string> &req)
{
    auto f_cmp = [](const std::string &lhs, const std::string &rhs) {
        return lhs.size() < rhs.size();
    };
    size_t max_element =
        std::max_element(req.begin(), req.end(), f_cmp)->size();
    return max_element;
}

size_t sum_elt_length(const std::vector<std::string> &req)
{
    auto f_add = [](std::size_t a, std::string str) {
        return str.length() + a;
    };
    return std::accumulate(req.begin(), req.end(), 0, f_add);
}

size_t count_elt(const std::vector<std::string> &req, const std::string &elt)
{
    size_t count_elt = std::count(req.begin(), req.end(), elt);
    return count_elt;
}

size_t count_duplicate(const std::vector<std::string> &req)
{
    std::vector<std::string> vec_tmp;
    auto f_count = [&vec_tmp](const std::string &p) {
        if (count_elt(vec_tmp, p) == 0)
        {
            vec_tmp.insert(vec_tmp.begin(), p);
        }
        return true;
    };
    std::count_if(req.begin(), req.end(), f_count);
    return req.size() - vec_tmp.size();
}
