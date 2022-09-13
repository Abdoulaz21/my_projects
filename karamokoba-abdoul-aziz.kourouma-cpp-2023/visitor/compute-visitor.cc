#include "compute-visitor.hh"

namespace visitor
{
    void ComputeVisitor::visit(const tree::Tree &e)
    {
        e.accept(*this);
    }

    void ComputeVisitor::visit(const tree::Node &e)
    {
        auto op = e.value_get()[0];

        e.rhs_get()->accept(*this);
        int rhs = value_;
        e.lhs_get()->accept(*this);
        int lhs = value_;

        if (op == '+')
            value_ = lhs + rhs;
        else if (op == '-')
            value_ = lhs - rhs;
        else if (op == '*')
            value_ = lhs * rhs;
        else if (op == '/')
        {
            if (rhs != 0)
                value_ = lhs / rhs;
            else
                throw std::overflow_error("Divide by zero exception");
        }
    }
    void ComputeVisitor::visit(const tree::Leaf &e)
    {
        value_ = std::stoi(e.value_get());
    }

    int ComputeVisitor::value_get()
    {
        return value_;
    }
} // namespace visitor
