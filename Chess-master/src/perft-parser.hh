#pragma once

#include <chessboard.hh>
#include <fstream>
#include <regex>

namespace parsing
{
    namespace perft
    {
        void parse_perft(const std::string &filename);

        std::string open_perft_file(const std::string &filename);

        bool check_fen_string(const std::string &fen);

        int calculate_moves(board::ChessBoard &board, int depth);
    } // namespace perft
} // namespace parsing
