#include "iostream"

template <class C, typename T>
void my_foreach(C it_lhs, C it_rhs, T &&f)
{
    for (; it_lhs != it_rhs; ++it_lhs)
    {
        f(*it_lhs);
    }
}
