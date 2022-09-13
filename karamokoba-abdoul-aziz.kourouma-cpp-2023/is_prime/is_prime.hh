template <unsigned N>
constexpr bool is_prime()
{
    if (N <= 1)
        return false;

    for (unsigned i = 2; i < (N / 2) + 1; i++)
        if (N % i == 0)
            return false;

    return true;
}
