#include "rle.hh"

#include <iostream>

const std::string RLE::perform(std::string content)
{
    int len = content.length();
    for (int i = 0; i < len; i++)
    {
        if (content[i] == this->spe_char_)
        {
            std::cerr << "Error: special char must not be in rle str" << '\n';
            exit(1);
        }
    }

    auto nbr_occ_min = 4;
    std::string res = "";
    for (unsigned int i = 0; i < content.length(); i++)
    {
        int count = 1;
        while (i < content.length() - 1 && content[i] == content[i + 1])
        {
            count++;
            i++;
        }
        while (count > 9)
        {
            res += this->spe_char_ + std::to_string(9) + content[i];
            count -= 9;
        }
        if (count >= nbr_occ_min)
        {
            res += this->spe_char_ + std::to_string(count) + content[i];
        }
        else
        {
            for (int p = 0; p < count; p++)
            {
                res += content[i];
            }
        }
    }

    return res;
}
