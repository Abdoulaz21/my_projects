#include <stdexcept>

#include "leaf.hh"
#include "node.hh"
#include "tree.hh"
#include "visitor.hh"

namespace visitor
{
    class ComputeVisitor : public Visitor
    {
    public:
        void visit(const tree::Tree &e);
        void visit(const tree::Node &e);
        void visit(const tree::Leaf &e);
        int value_get();

    private:
        int value_;
    };
} // namespace visitor
