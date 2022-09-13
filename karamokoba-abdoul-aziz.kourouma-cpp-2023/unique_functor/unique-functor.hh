#pragma once

#include <functional>
#include <iostream>
#include <vector>

template <class ForwardIt, class BinaryPredicate>
ForwardIt my_unique(ForwardIt first, ForwardIt last, BinaryPredicate p);

#include "unique-functor.hxx"
