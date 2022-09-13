#include <fstream>
#include <iostream>
#include <string>

void replace(const std::string &input_filename,
             const std::string &output_filename, const std::string &src_token,
             const std::string &dst_token)
{
    std::string line = "";

    std::ifstream file_in(input_filename);
    std::ofstream file_out(output_filename);

    if (!file_in)
    {
        std::cerr << "Cannot open input file\n";
        return;
    }

    if (!file_out)
    {
        std::cerr << "Cannot write output file\n";
        return;
    }

    while (getline(file_in, line))
    {
        std::size_t pos = line.find(src_token);
        while (pos != std::string::npos)
        {
            line.replace(pos, src_token.length(), dst_token);
            pos = line.find(src_token, pos + dst_token.length());
        }
        file_out << line << '\n';
    }
}
