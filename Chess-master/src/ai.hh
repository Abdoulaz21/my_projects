#pragma once

#include <chessboard.hh>
#include <evaluation.hh>
#include <limits>
#include <move-ordering.hh>
#include <quiescence.hh>

#define AI_DEPTH 3

namespace ai
{
    constexpr int INF = 99999999;
    constexpr int NINF = -INF;

    int search_rec(board::ChessBoard &board, int depth, int alpha, int beta);
    board::Move search(board::ChessBoard &board);
} // namespace ai
