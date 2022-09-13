#pragma once

#include <vector>

template <typename T>
bool palindrome(std::vector<T> &vect)
{
    if (vect.empty())
        return true;

    auto left = vect.begin();
    auto right = vect.rbegin();

    while (left != vect.end() and right != vect.rend() and *left == *right)
    {
        ++left;
        ++right;
    }

    return (left == vect.end() and right == vect.rend());
}
