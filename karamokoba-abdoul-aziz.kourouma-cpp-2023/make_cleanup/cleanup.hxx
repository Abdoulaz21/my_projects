#pragma once

#include "cleanup.hh"

template <typename T>
Cleanup<T> make_cleanup(T func)
{
    return Cleanup<T>(func);
}
