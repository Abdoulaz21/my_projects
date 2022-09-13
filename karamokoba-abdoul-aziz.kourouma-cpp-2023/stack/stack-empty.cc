#include "stack-empty.hh"

const char *StackEmpty::what() const noexcept
{
    return message_.c_str();
}
