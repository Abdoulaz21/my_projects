#include "rotx.hh"

#include <iostream>

void RotX::process()
{
    std::string &my_str = Encrypt::input_;
    if (val_ != 0)
    {
        unsigned len = my_str.length();

        for (unsigned i = 0; i < len; i++)
        {
            int x = val_;
            char c = my_str[i];
            if (c >= 'a' and c <= 'z')
            {
                while (x < 0)
                    x += 26;
                if (x > 26)
                    x = x % 26;
                if (c + x > 'z')
                    c = c - 26 + x;
                else
                    c = c + x;
            }
            else if (c >= 'A' and c <= 'Z')
            {
                while (x < 0)
                    x += 26;
                if (x > 26)
                    x = x % 26;
                if (c + x > 'Z')
                    c = c - 26 + x;
                else
                    c = c + x;
            }
            else if (c >= '0' and c <= '9')
            {
                while (x < 0)
                    x += 10;
                if (x >= 10)
                    x = x % 10;
                if (c + x > '9')
                    c = c - 10 + x;
                else
                    c = c + x;
            }
            my_str[i] = c;
        }
    }
    std::cout << my_str << '\n';
}
