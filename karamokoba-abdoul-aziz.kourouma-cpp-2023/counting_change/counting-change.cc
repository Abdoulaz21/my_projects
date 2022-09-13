#include <iostream>

constexpr long long count(int S[], int m, int n)
{
    if (n == 0)
        return 1;
    else if ((m <= 0 && n >= 1) || n < 0)
        return 0;
    else
        return count(S, m - 1, n) + count(S, m, n - S[m - 1]);
}

constexpr long long count_change(const long long amount)
{
    int change[] = { 1, 2, 5, 10, 20, 50, 100, 200 };
    return count(change, 7, amount);
}
