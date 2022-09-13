#include "stack-creation-failed.hh"

const char *StackCreationFailed::what() const noexcept
{
    return message_.c_str();
}
