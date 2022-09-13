#include "int-container.hh"

MyIntContainer::MyIntContainer(size_t size)
    : current_size_(0)
    , max_size_(size)
{
    elems_ = std::make_unique<int[]>(size);
}

void MyIntContainer::print() const
{
    for (size_t i = 0; i < current_size_; i++)
    {
        std::cout << elems_[i];
        if (i != current_size_ - 1)
            std::cout << " | ";
    }
    std::cout << '\n';
}

size_t MyIntContainer::get_len() const
{
    size_t res = 0;
    for (size_t i = 0; i < current_size_; i++)
    {
        res += 1;
    }
    return res;
}

bool MyIntContainer::add(int elem)
{
    if (current_size_ < max_size_)
    {
        elems_[current_size_] = elem;
        current_size_ += 1;
        return true;
    }
    return false;
}

std::optional<int> MyIntContainer::pop()
{
    std::optional<int> res = std::nullopt;
    if (current_size_ != 0)
    {
        res = elems_[current_size_ - 1];
        current_size_ -= 1;
    }
    return res;
}

std::optional<int> MyIntContainer::get(size_t position) const
{
    std::optional<int> res = std::nullopt;
    if (position < current_size_)
    {
        res = elems_[position];
    }
    return res;
}

std::optional<size_t> MyIntContainer::find(int elem) const
{
    std::optional<size_t> res = std::nullopt;
    for (size_t i = 0; i < current_size_; i++)
    {
        if (elems_[i] == elem)
        {
            res = i;
            break;
        }
    }
    return res;
}

void MyIntContainer::sort()
{
    for (size_t i = 1; i < current_size_; ++i)
    {
        int key = elems_[i];
        size_t j = i - 1;
        while (j != 0 && elems_[j] > key)
        {
            elems_[j + 1] = elems_[j];
            --j;
        }
        elems_[j + 1] = key;
    }

    for (size_t i = 0; i < current_size_; ++i)
    {
        if (elems_[i] > elems_[i + 1])
        {
            int tmp = elems_[i];
            elems_[i] = elems_[i + 1];
            elems_[i + 1] = tmp;
        }
        else
            break;
    }
}

bool MyIntContainer::is_sorted() const
{
    if (current_size_ < 2)
        return true;
    for (size_t i = 0; i < current_size_ - 1; i++)
    {
        if (elems_[i] > elems_[i + 1])
            return false;
    }
    return true;
}
