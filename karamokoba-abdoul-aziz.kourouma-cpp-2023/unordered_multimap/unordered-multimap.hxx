#include "unordered-multimap.hh"

template <typename KeyType, typename ValueType>
bool UnorderedMultimap<KeyType, ValueType>::empty() const
{
    return vect_map_.begin() == vect_map_.end();
}

template <typename KeyType, typename ValueType>
unsigned UnorderedMultimap<KeyType, ValueType>::size() const
{
    return vect_map_.size();
}

template <typename KeyType, typename ValueType>
std::pair<typename UnorderedMultimap<KeyType, ValueType>::iterator,
          typename UnorderedMultimap<KeyType, ValueType>::iterator>
UnorderedMultimap<KeyType, ValueType>::equal_range(const KeyType &key)
{
    for (auto it = vect_map_.begin(); it != vect_map_.end(); it++)
    {
        if ((*it).first == key)
        {
            auto fir = it;
            while ((*it).first == key)
            {
                it++;
            }
            return std::make_pair(fir, it);
        }
    }
    return std::make_pair(vect_map_.end(), vect_map_.end());
}

template <typename KeyType, typename ValueType>
void UnorderedMultimap<KeyType, ValueType>::add(const KeyType &key,
                                                const ValueType &value)
{
    std::pair<KeyType, ValueType> mypair(key, value);
    auto it = vect_map_.begin();
    for (; it != vect_map_.end(); it++)
    {
        if ((*it).first == key)
        {
            while ((it != vect_map_.end()) && (*it).first == key)
            {
                it++;
            }
            vect_map_.insert(it, mypair);
            break;
        }
    }
    if (it == vect_map_.end())
        vect_map_.push_back(mypair);
}

template <typename KeyType, typename ValueType>
unsigned UnorderedMultimap<KeyType, ValueType>::count(const KeyType &key) const
{
    unsigned res = 0;
    for (auto it = vect_map_.begin(); it != vect_map_.end(); it++)
    {
        if ((*it).first == key)
            res++;
    }

    return res;
}

template <typename KeyType, typename ValueType>
void UnorderedMultimap<KeyType, ValueType>::clear()
{
    vect_map_.clear();
}
