#pragma once

template <typename T>
struct Cleanup
{
    Cleanup(T func)
        : lambda(func)
    {}

    ~Cleanup()
    {
        lambda();
    }

    T lambda;
};

template <typename T>
Cleanup<T> make_cleanup(T func);

#include "cleanup.hxx"
