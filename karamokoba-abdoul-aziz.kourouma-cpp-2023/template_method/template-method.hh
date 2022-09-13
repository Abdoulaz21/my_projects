#pragma once

#include <vector>

class AbstractSort
{
public:
    void sort(std::vector<int> &v) const;

private:
    virtual bool need_swap(int, int) const = 0;
};

class IncreasingOrderSort : public AbstractSort
{
    bool need_swap(int a, int b) const override;
};

class DecreasingOrderSort : public AbstractSort
{
    bool need_swap(int a, int b) const override;
};
