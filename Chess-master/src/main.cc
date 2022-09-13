#include <ai.hh>
#include <chrono>
#include <option-parser.hh>
#include <uci-parser.hh>
#include <uci.hh>

int main(int argc, char **argv)
{
    bool is_options = parsing::option_parser::parse_options(argc, argv);

    auto time_ = ai::UCITime();

    while (is_options)
    {
        board::ChessBoard b;
        std::string board_string = ai::get_board("Magnus Broken", time_);

        if (board_string.find("fen") != std::string::npos)
        {
            std::string fen = ai::parse_fen(board_string);
            b = board::ChessBoard(fen);
        }

        auto moves_string = ai::get_vect_moves(board_string);
        b = ai::parse_moves(b, moves_string);

        auto s_time = std::chrono::high_resolution_clock::now();
        board::Move move = ai::search(b);
        auto e_time = std::chrono::high_resolution_clock::now();

        ai::play_move(move.to_uci());

        auto execution_time =
            std::chrono::duration_cast<std::chrono::milliseconds>(e_time
                                                                  - s_time);
        std::cerr << "Search Duration: " << execution_time.count() << "ms\n";
    }

    return 0;
}
