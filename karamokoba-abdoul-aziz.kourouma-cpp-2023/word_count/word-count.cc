#include <fstream>
#include <iostream>
#include <string>

ssize_t word_count(const std::string &filename)
{
    std::string token;
    std::ifstream file_in(filename);

    if (!file_in.is_open())
        return -1;

    ssize_t res = 0;
    while (file_in >> token)
        res++;

    file_in.close();

    return res;
}
