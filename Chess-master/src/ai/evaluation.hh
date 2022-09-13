#pragma once

#include <ai.hh>

namespace ai
{
    namespace PieceValue
    {
        constexpr int QUEEN = 900;
        constexpr int ROOK = 500;
        constexpr int BISHOP = 320;
        constexpr int KNIGHT = 300;
        constexpr int PAWN = 100;
    } // namespace PieceValue

    namespace PieceHeatMap
    {
        /* White Side Maps */
        constexpr int w_pawn[] = {
            0,  0,  0,   0,  0,  0,   0,  0,  5,  10, 10, -20, -20, 10, 10, 5,
            5,  -5, -10, 0,  0,  -10, -5, 5,  0,  0,  0,  20,  20,  0,  0,  0,
            5,  5,  10,  25, 25, 10,  5,  5,  10, 10, 20, 30,  30,  20, 10, 10,
            50, 50, 50,  50, 50, 50,  50, 50, 0,  0,  0,  0,   0,   0,  0,  0
        };

        constexpr int w_knight[] = {
            5,   0,   -20, -40, -50, -40, -30, -30, -30, -30, -40, -50, -30,
            -30, 5,   10,  15,  15,  10,  5,   -30, -40, -20, 0,   5,   15,
            20,  20,  15,  5,   -30, -30, 0,   15,  20,  20,  15,  0,   0,
            -20, -40, -30, 0,   10,  15,  15,  10,  0,   -30, -30, 5,   -50,
            -40, -30, -30, -30, -30, -40, -50, -40, -20, 0,   0,   0
        };

        constexpr int w_bishop[] = {
            0,   0,   5,   -10, -20, -10, -10, -10, -10, -10, -10, -20, -10,
            -10, 10,  10,  10,  10,  10,  10,  -10, -10, 5,   0,   0,   5,
            10,  10,  5,   5,   -10, -10, 0,   10,  10,  10,  10,  0,   0,
            0,   -10, -10, 0,   5,   10,  10,  5,   0,   -10, -10, 5,   -20,
            -10, -10, -10, -10, -10, -10, -20, -10, 0,   0,   0,   0
        };

        constexpr int w_rook[] = { -5, 0,  0,  0,  5,  5,  0, 0,  0,  0,  0,
                                   0,  -5, -5, 0,  0,  0,  0, 0,  0,  0,  0,
                                   0,  0,  0,  0,  -5, -5, 0, 0,  0,  0,  -5,
                                   -5, 0,  0,  0,  0,  0,  0, -5, -5, 10, 10,
                                   10, 10, 5,  -5, 0,  0,  0, 0,  0,  0,  0,
                                   0,  0,  0,  0,  0,  0,  5, 10, 10 };

        constexpr int w_queen[] = {
            0,   0,   0,   -10, -20, -10, -10, -5,  -5,  -10, -10, -20, -5,
            -10, 5,   5,   5,   5,   5,   0,   -10, -10, 0,   5,   0,   5,
            5,   5,   5,   0,   -5,  0,   0,   5,   5,   5,   5,   0,   0,
            0,   -10, -10, 0,   5,   5,   5,   5,   0,   -10, -5,  0,   -20,
            -10, -10, -5,  -5,  -10, -10, -20, -10, 0,   0,   0,   0
        };

        constexpr int w_king_middle[] = {
            0,   0,   20,  20,  20,  30,  10,  0,   0,   10,  30,  20,  -20,
            -10, -20, -20, -20, -20, -20, -20, -10, 20,  20,  0,   0,   -40,
            -50, -50, -40, -40, -30, -20, -30, -30, -40, -40, -30, -30, -40,
            -40, -30, -30, -40, -40, -50, -50, -40, -40, -30, -30, -40, -30,
            -40, -40, -50, -50, -40, -40, -30, -30, -40, -40, -50, -50
        };

        /* Black Side Maps */
        constexpr int b_pawn[] = {
            0,  0,  0,  0,   0,   0,  0,  0,  50, 50, 50,  50, 50, 50,  50, 50,
            10, 10, 20, 30,  30,  20, 10, 10, 5,  5,  10,  25, 25, 10,  5,  5,
            0,  0,  0,  20,  20,  0,  0,  0,  5,  -5, -10, 0,  0,  -10, -5, 5,
            5,  10, 10, -20, -20, 10, 10, 5,  0,  0,  0,   0,  0,  0,   0,  0
        };

        constexpr int b_knight[] = {
            -50, -40, -30, -30, -30, -30, -40, -50, -40, -20, 0,   0,   0,
            0,   -20, -40, -30, 0,   10,  15,  15,  10,  0,   -30, -30, 5,
            15,  20,  20,  15,  5,   -30, -30, 0,   15,  20,  20,  15,  0,
            -30, -30, 5,   10,  15,  15,  10,  5,   -30, -40, -20, 0,   5,
            5,   0,   -20, -40, -50, -40, -30, -30, -30, -30, -40, -50
        };

        constexpr int b_bishop[] = {
            -20, -10, -10, -10, -10, -10, -10, -20, -10, 0,   0,   0,   0,
            0,   0,   -10, -10, 0,   5,   10,  10,  5,   0,   -10, -10, 5,
            5,   10,  10,  5,   5,   -10, -10, 0,   10,  10,  10,  10,  0,
            -10, -10, 10,  10,  10,  10,  10,  10,  -10, -10, 5,   0,   0,
            0,   0,   5,   -10, -20, -10, -10, -10, -10, -10, -10, -20
        };

        constexpr int b_rook[] = { 0,  0,  0,  0,  0,  0,  0,  0,  5, 10, 10,
                                   10, 10, 10, 10, 5,  -5, 0,  0,  0, 0,  0,
                                   0,  -5, -5, 0,  0,  0,  0,  0,  0, -5, -5,
                                   0,  0,  0,  0,  0,  0,  -5, -5, 0, 0,  0,
                                   0,  0,  0,  -5, -5, 0,  0,  0,  0, 0,  0,
                                   -5, 0,  0,  0,  5,  5,  0,  0,  0 };

        constexpr int b_queen[] = {
            -20, -10, -10, -5,  -5,  -10, -10, -20, -10, 0,   0,   0,  0,
            0,   0,   -10, -10, 0,   5,   5,   5,   5,   0,   -10, -5, 0,
            5,   5,   5,   5,   0,   -5,  0,   0,   5,   5,   5,   5,  0,
            -5,  -10, 5,   5,   5,   5,   5,   0,   -10, -10, 0,   5,  0,
            0,   0,   0,   -10, -20, -10, -10, -5,  -5,  -10, -10, -20
        };

        constexpr int b_king_middle[] = {
            -30, -40, -40, -50, -50, -40, -40, -30, -30, -40, -40, -50, -50,
            -40, -40, -30, -30, -40, -40, -50, -50, -40, -40, -30, -30, -40,
            -40, -50, -50, -40, -40, -30, -20, -30, -30, -40, -40, -30, -30,
            -20, -10, -20, -20, -20, -20, -20, -20, -10, 20,  20,  0,   0,
            0,   0,   20,  20,  20,  30,  10,  0,   0,   10,  30,  20
        };
    } // namespace PieceHeatMap

    constexpr double endgame_start =
        PieceValue::ROOK * 2 + PieceValue::BISHOP + PieceValue::KNIGHT;

    int evaluate(board::ChessBoard &board);

    double endgame_weight_get(int material_no_pawn);

    int evaluate_pieces_heat_map(board::ChessBoard &board, bool is_white);
    int evaluate_piece_heat_map(const int heat_map[],
                                const std::bitset<64> &pieces);

    int force_king_to_corner_endgame(const std::bitset<64> &friendly_king,
                                     const std::bitset<64> &opponent_king,
                                     double endgame_weight);
} // namespace ai