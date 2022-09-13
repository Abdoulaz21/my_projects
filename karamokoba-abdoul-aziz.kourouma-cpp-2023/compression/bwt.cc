#include "bwt.hh"

#include <iostream>

static void my_swap(std::string &s, int l, int r)
{
    char tmp = s[l];
    s[l] = s[r];
    s[r] = tmp;
}

const std::string BWT::perform(std::string content)
{
    int len = content.length();
    for (int i = 0; i < len; i++)
    {
        if (content[i] == this->end_char_)
        {
            std::cerr << "Error: special char must not be in bwt str" << '\n';
            exit(1);
        }
    }
    content += this->end_char_;
    len = content.length();
    std::string possibility[2048];

    possibility[0] = content;
    for (int i = 1; i < len; i++)
    {
        for (unsigned int i = 1; i < content.length(); i++)
        {
            my_swap(content, i, i - 1);
        }
        possibility[i] = content;
    }

    std::string tmp;
    for (int i = 1; i < len; ++i)
    {
        for (int j = 0; j < len - i; ++j)
        {
            if (possibility[j] > possibility[j + 1])
            {
                tmp = possibility[j];
                possibility[j] = possibility[j + 1];
                possibility[j + 1] = tmp;
            }
        }
    }

    std::string res = "";
    for (int p = 0; p < len; p++)
    {
        res += possibility[p][len - 1];
    }

    return res;
}
