#pragma once

#include <ai.hh>

namespace ai
{
    std::vector<board::Move> order_moves(board::ChessBoard &board,
                                         std::vector<board::Move> moves);
} // namespace ai
