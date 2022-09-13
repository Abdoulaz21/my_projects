#include "read-info.hh"

#include <iostream>
#include <sstream>

static bool is_a_number(const std::string &str)
{
    for (char const &c : str)
    {
        if (std::isdigit(c) == 0)
            return false;
    }
    return true;
}

DirectoryInfo *read_info(std::ifstream &file_in)
{
    std::string name = "";
    int size = 0;
    unsigned int rights = 0;
    std::string owner = "";

    std::string word = "";
    std::string line = "";
    std::getline(file_in, line);
    std::istringstream ss(line);
    int count = 0;
    while (ss >> word)
    {
        if (count == 0)
            name = word;
        else if (count == 1)
        {
            if (is_a_number(word))
                size = std::stoi(word);
            else
                break;
        }
        else if (count == 2)
        {
            if (is_a_number(word))
            {
                try
                {
                    rights = std::stoi(word, nullptr, 8);
                }
                catch (const std::exception &e)
                {
                    break;
                }
            }
            else
                break;
        }
        else
            owner = word;
        count++;
    }
    if (count == 4)
        return new DirectoryInfo(name, size, rights, owner);
    return nullptr;
}
