#include "termcolor.hh"
namespace termcolor
{
    my_ostream operator<<(std::ostream &out, background value)
    {
        out << "\x1B[" << static_cast<int>(value) << "m";
        return my_ostream(out);
    }

    my_ostream operator<<(std::ostream &out, foreground value)
    {
        out << "\x1B[" << static_cast<int>(value) << "m";
        return my_ostream(out);
    }
} // namespace termcolor
