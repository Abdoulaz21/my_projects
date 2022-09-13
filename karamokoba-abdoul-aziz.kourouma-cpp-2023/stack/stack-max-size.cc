#include "stack-max-size.hh"

const char *StackMaxSize::what() const noexcept
{
    return message_.c_str();
}
