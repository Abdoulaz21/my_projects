#include <iostream>
#include <option-parser.hh>

namespace parsing
{
    namespace option_parser
    {
        bool parse_options(int argc, char **argv)
        {
            using namespace boost::program_options;
            try
            {
                options_description desc{ "Allowed options" };
                desc.add_options()("help,h", "show usage")(
                    "pgn", value<std::string>(), "path to the PGN file")(
                    "listeners,l",
                    value<std::vector<std::string>>()->multitoken(),
                    "list of paths to listener plugins")(
                    "perft", value<std::string>(), "path to a perft file");

                variables_map vm;
                store(parse_command_line(argc, argv, desc), vm);
                notify(vm);

                if (vm.count("help"))
                {
                    std::cout << desc << '\n';
                    return false;
                }
                else if (vm.count("pgn"))
                {
                    std::string filepath = vm["pgn"].as<std::string>();
                    std::vector<std::string> vect = {};

                    if (vm.count("listeners"))
                    {
                        auto listener =
                            vm["listeners"].as<std::vector<std::string>>();
                        vect.insert(vect.end(), listener.begin(),
                                    listener.end());
                    }

                    board::GameTracer game{ filepath, vect };
                    game.play_game();
                    return false;
                }
                else if (vm.count("perft"))
                {
                    std::string filename = vm["perft"].as<std::string>();
                    parsing::perft::parse_perft(filename);
                    return false;
                }
                else
                    return true;
            }
            catch (const error &e)
            {
                std::cerr << e.what() << '\n';
            }
            return false;
        }
    } // namespace option_parser
} // namespace parsing
