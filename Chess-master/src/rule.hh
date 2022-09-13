#pragma once

#include <vector>

#include "chessboard.hh"
#include "move-generator.hh"
#include "move.hh"
#include "slide-pieces.hh"

namespace board
{
    using moves = std::vector<Move>;

    namespace rule
    {
        moves generate_one_pawn_moves(ChessBoard &board,
                                      const std::bitset<64> &start_pos);
        moves generate_pawn_moves(ChessBoard &board);

        moves generate_one_knight_moves(ChessBoard &board,
                                        const std::bitset<64> &start_pos);
        moves generate_knight_moves(ChessBoard &board);

        moves generate_king_moves(ChessBoard &board);

        moves generate_one_rook_moves(ChessBoard &board,
                                      const std::bitset<64> &start_pos);
        moves generate_rook_moves(ChessBoard &board);

        moves generate_one_bishop_moves(ChessBoard &board,
                                        const std::bitset<64> &start_pos);
        moves generate_bishop_moves(ChessBoard &board);

        moves generate_queen_moves(ChessBoard &board);
    } // namespace rule
} // namespace board
