#pragma once

#include <iostream>
#include <memory>

namespace termcolor
{
    enum class background : unsigned int
    {
        black = 40,
        red = 41,
        green = 42,
        yellow = 43,
        blue = 44,
        magenta = 45,
        cyan = 46,
        white = 47
    };

    enum class foreground : unsigned int
    {
        black = 30,
        red = 31,
        green = 32,
        yellow = 33,
        blue = 34,
        magenta = 35,
        cyan = 36,
        white = 37
    };

    class my_ostream
    {
    public:
        my_ostream(std::ostream &out)
            : out_(out.rdbuf())
        {}

        ~my_ostream()
        {
            out_ << "\x1B[0m";
        }

        template <class T>
        my_ostream &operator<<(const T &value)
        {
            out_ << value;
            return *this;
        }

        my_ostream &operator<<(std::ostream &(*func)(std::ostream &))
        {
            out_ << func;
            return *this;
        }

        my_ostream &operator<<(background value)
        {
            out_ << "\x1B[" << static_cast<int>(value) << "m";
            return *this;
        }

        my_ostream &operator<<(foreground value)
        {
            out_ << "\x1B[" << static_cast<int>(value) << "m";
            return *this;
        }

        // std::ostream &get_out();

    private:
        std::ostream out_;
    };

    my_ostream operator<<(std::ostream &out, background value);
    my_ostream operator<<(std::ostream &out, foreground value);
} // namespace termcolor
