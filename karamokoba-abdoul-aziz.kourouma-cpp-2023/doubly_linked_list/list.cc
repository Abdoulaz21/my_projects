#include "list.hh"

List::List()
    : nb_elts_(0)
    , first_(nullptr)
    , last_(nullptr)
{}

void List::push_front(int i)
{
    auto elt = std::make_shared<Node>(i);
    if (nb_elts_ == 0)
    {
        first_ = elt;
        last_ = elt;
    }
    else
    {
        (*first_).prev_set(elt);
        (*elt).next_set(first_);
        first_ = elt;
    }
    nb_elts_ += 1;
}

void List::push_back(int i)
{
    auto elt = std::make_shared<Node>(i);
    if (this->nb_elts_ == 0)
    {
        this->first_ = elt;
        this->last_ = elt;
    }
    else
    {
        (*last_).next_set(elt);
        (*elt).prev_set(last_);
        last_ = elt;
    }
    nb_elts_++;
}

std::optional<int> List::pop_front()
{
    if (this->nb_elts_ == 0)
        return std::nullopt;

    int res = (*first_).val_get();
    first_ = (*first_).next_get();

    nb_elts_ -= 1;
    return res;
}

std::optional<int> List::pop_back()
{
    if (nb_elts_ == 0)
        return std::nullopt;

    int res = (*last_).val_get();
    last_ = (*last_).prev_get();

    nb_elts_ -= 1;
    return res;
}

void List::print(std::ostream &os) const
{
    if (nb_elts_ == 0)
        return;

    auto tmp = first_;
    while (tmp != last_)
    {
        os << (*tmp).val_get() << " ";
        tmp = (*tmp).next_get();
    }
    os << (*tmp).val_get();
}

int List::length() const
{
    return nb_elts_;
}
