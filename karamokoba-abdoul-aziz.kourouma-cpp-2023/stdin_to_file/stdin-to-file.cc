#include <fstream>
#include <iostream>
#include <string>

void stdin_to_file()
{
    std::string token;

    std::ofstream file_out("file.out");

    if (file_out.is_open())
    {
        while (std::cin >> token)
            file_out << token << '\n';

        file_out.close();
    }
}
