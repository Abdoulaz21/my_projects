#pragma once

class CloserTo
{
public:
    CloserTo(int dist)
        : dist_(dist)
    {}

    bool operator()(const int &lhs, const int &rhs) const
    {
        int dist_lhs;
        if (lhs > dist_)
            dist_lhs = lhs - dist_;
        else
            dist_lhs = dist_ - lhs;

        int dist_rhs = rhs - dist_;
        if (rhs > dist_)
            dist_rhs = rhs - dist_;
        else
            dist_rhs = dist_ - rhs;

        if (dist_lhs == dist_rhs)
            return lhs < rhs;
        return dist_lhs < dist_rhs;
    }

private:
    int dist_;
};
