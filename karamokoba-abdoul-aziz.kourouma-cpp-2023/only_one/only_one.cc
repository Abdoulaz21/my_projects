#include <algorithm>
#include <iostream>
#include <vector>

int main(void)
{
    std::vector<std::string> names;
    std::string response;
    std::string entered;
    int count = 0;
    do
    {
        std::cout << "Enter a name:" << '\n';
        std::cin >> entered;
        std::vector<std::string>::iterator it =
            std::find(names.begin(), names.end(), entered);
        if (it == names.end())
        {
            names.insert(names.end(), entered);
            count++;
        }
        else
        {
            std::cout << "Value already exists." << '\n';
        }
        std::cout << "Continue ? (y,n) ";
        std::cin >> response;
    } while (response == "y");

    std::cout << '\n' << "Names:" << '\n';
    std::sort(names.begin(), names.end());
    for (auto name : names)
        std::cout << "- " << name << '\n';
}
