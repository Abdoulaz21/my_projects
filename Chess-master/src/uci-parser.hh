#pragma once

#include <chessboard.hh>
#include <move.hh>
#include <string>
#include <vector>

namespace ai
{
    using moves = std::vector<board::Move>;

    /** Reads lines until `fen`  command is found
     * \param get_board The string got from
     *      get_board() function
     * \returns a fen string
     */
    std::string parse_fen(std::string get_board);

    /** Reads lines until `moves` command is found and add the moves to the
     * returned vector \param get_board The string got from get_board() function
     * \returns a vector of strings
     */
    std::vector<std::string> get_vect_moves(std::string get_board);

    board::ChessBoard parse_moves(board::ChessBoard &board,
                                  std::vector<std::string> &uci_moves);
} // namespace ai
