#include "stack-creation-failed.hh"
#include "stack-empty.hh"
#include "stack-max-size.hh"
#include "stack.hh"

template <class T>
Stack<T>::Stack(size_t max_size)
{
    max_size_ = max_size;
    try
    {
        stack_.reserve(max_size);
    }
    catch (std::length_error &e)
    {
        throw StackCreationFailed("Max stack size is too big.");
    }
    catch (std::bad_alloc &e)
    {
        throw StackCreationFailed("Allocation failed.");
    }
}

template <class T>
Stack<T> &Stack<T>::push(const T &item)
{
    if (max_size_ == stack_.size())
        throw StackMaxSize("Unable to push, stack max size reached.");
    stack_.push_back(item);
    return *this;
}

template <class T>
Stack<T> &Stack<T>::operator+=(const T &item)
{
    if (max_size_ == stack_.size())
        throw StackMaxSize("Unable to push, stack max size reached.");
    stack_.push_back(item);
    return *this;
}

template <class T>
Stack<T> &Stack<T>::operator<<(const T &item)
{
    if (max_size_ == stack_.size())
        throw StackMaxSize("Unable to push, stack max size reached.");
    stack_.push_back(item);
    return *this;
}

template <class T>
T Stack<T>::pop()
{
    if (stack_.size() == 0)
        throw StackEmpty("Unable to pop, stack is empty.");
    T val = stack_[stack_.size() - 1];
    stack_.pop_back();
    return val;
}

template <class T>
T Stack<T>::operator--()
{
    if (stack_.size() == 0)
        throw StackEmpty("Unable to pop, stack is empty.");
    T val = stack_[stack_.size() - 1];
    stack_.pop_back();
    return val;
}

template <class T>
T &Stack<T>::operator[](size_t i)
{
    if (i >= stack_.size())
        throw std::out_of_range("Out of range.");
    size_t j = 0;
    auto it = stack_.rbegin();
    while (it != stack_.rend())
    {
        if (i == j)
            break;
        it++;
        j++;
    }
    return *it;
}

template <class T>
bool Stack<T>::operator==(const Stack<T> other)
{
    return stack_ == other.stack_ and max_size_ == other.max_size_;
}

template <class T>
std::ostream &Stack<T>::print(std::ostream &s) const
{
    auto it = stack_.begin();
    for (; it != stack_.end() - 1 && it != stack_.end(); it++)
    {
        s << *it << ' ';
    }
    s << *it;
    return s;
}

template <class T>
typename Stack<T>::const_iterator Stack<T>::begin() const
{
    return stack_.crbegin();
}

template <class T>
typename Stack<T>::iterator Stack<T>::begin()
{
    return stack_.rbegin();
}

template <class T>
typename Stack<T>::const_iterator Stack<T>::end() const
{
    return stack_.crend();
}

template <class T>
typename Stack<T>::iterator Stack<T>::end()
{
    return stack_.rend();
}

template <class T>
void Stack<T>::resize(size_t t)
{
    if (t < max_size_)
        stack_.erase(stack_.begin() + t, stack_.end());
    else
    {
        try
        {
            stack_.reserve(t);
        }
        catch (std::length_error &e)
        {
            throw StackCreationFailed("Max stack size is too big.");
        }
        catch (std::bad_alloc &e)
        {
            throw StackCreationFailed("Allocation failed.");
        }
    }
    max_size_ = t;
}

template <class T>
size_t Stack<T>::max_size() const
{
    return max_size_;
}

template <class T>
std::ostream &operator<<(std::ostream &ostr, const Stack<T> &p)
{
    return p.print(ostr);
}
