#include <iostream>
#include <perft-parser.hh>

namespace parsing
{
    namespace perft
    {
        void parse_perft(const std::string &filename)
        {
            std::string fen_string = open_perft_file(filename);

            if (!check_fen_string(fen_string))
                throw std::runtime_error("Invalid FEN String.");

            board::ChessBoard board(fen_string);

            int depth = fen_string.back() - '0';

            std::cout << calculate_moves(board, depth) << '\n';
        }

        std::string open_perft_file(const std::string &filename)
        {
            std::ifstream file;
            file.open(filename);
            if (!file.is_open())
                throw std::runtime_error("Unable to open perft file");

            std::string fen_string;
            std::getline(file, fen_string, '\n');

            return fen_string;
        }

        bool check_fen_string(const std::string &fen_string)
        {
            const std::regex fen_string_pattern(
                "^((r|n|b|q|k|p|R|N|B|Q|R|K|P|[1-8]){1,8}/"
                "){7}(r|n|b|q|k|p|R|N|B|Q|R|K|P|[1-8]){1,8} (w|b) "
                "((K|Q|k|q){1,4}|-) ([a-h][1-8]|-) [0-9]+ [0-9]+ [1-9][0-9]*$");

            return std::regex_match(fen_string, fen_string_pattern);
        }

        int calculate_moves(board::ChessBoard &board, int depth)
        {
            if (depth == 0)
                return 1;

            int moves_available = 0;
            auto moves = board.generate_legal_moves();

            for (const auto &m : moves)
            {
                // if (depth == 1)
                //     std::cout << m << "\n";
                auto sim = board;
                sim.do_move(m);
                moves_available += calculate_moves(sim, depth - 1);
            }

            return moves_available;
        }
    } // namespace perft
} // namespace parsing
