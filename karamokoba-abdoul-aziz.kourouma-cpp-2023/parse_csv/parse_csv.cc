#include "parse_csv.hh"

std::vector<std::vector<std::string>> parse_csv(const std::string &file_name)
{
    std::vector<std::vector<std::string>> matrix;
    std::string str;
    char delim = ',';

    std::ifstream file_in(file_name);
    if (!file_in)
        throw std::ios_base::failure("Error opening file");

    unsigned int size = 0;
    unsigned int line = 1;
    while (std::getline(file_in, str))
    {
        std::vector<std::string> vect = {};
        std::string tmp;
        std::istringstream str_in(str);
        while (std::getline(str_in, tmp, delim))
        {
            vect.push_back(tmp);
        }
        if (str[str.length() - 1] == delim)
            vect.push_back("");
        if (size == 0)
            size = vect.size();
        else
        {
            if (size != vect.size())
            {
                std::string err = "Non consistent number of columns at line ";
                err += line + '0';
                throw std::ios_base::failure(err);
            }
        }
        matrix.push_back(vect);

        line++;
    }
    return matrix;
}
