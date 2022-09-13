#pragma once

#include <algorithm>
#include <bitset>
#include <cmath>
#include <optional>
#include <utility>
#include <vector>

#include "color.hh"
#include "move.hh"
#include "piece-type.hh"

namespace board
{
    class ChessBoard
    {
    public:
        ChessBoard();
        ChessBoard(const std::string &fen_string);

        int parse_fen_string_board(const std::string &fen_string);
        void parse_fen_string_en_passant(const std::string &fen_string,
                                         int idx);

        const std::bitset<64> chessboard_get();

        // white pieces getters
        const std::bitset<64> &white_pawns_get();
        const std::bitset<64> &white_bishops_get();
        const std::bitset<64> &white_knights_get();
        const std::bitset<64> &white_rooks_get();
        const std::bitset<64> &white_queen_get();
        const std::bitset<64> &white_king_get();

        // black pieces getters
        const std::bitset<64> &black_pawns_get();
        const std::bitset<64> &black_bishops_get();
        const std::bitset<64> &black_knights_get();
        const std::bitset<64> &black_rooks_get();
        const std::bitset<64> &black_queen_get();
        const std::bitset<64> &black_king_get();

        // Moving Pieces
        bool is_move_legal(const Move &move);
        bool is_legal_move(const Move &move);
        std::vector<Move> generate_legal_moves();
        void remove_captured_piece(const Move &move);
        void do_move(const Move &move);
        bool do_white_move(const Move &move, bool reset);
        bool do_black_move(const Move &move, bool reset);

        // En-passant
        void en_passant_left_set(std::optional<size_t> idx);
        void en_passant_right_set(std::optional<size_t> idx);
        std::pair<std::optional<size_t>, std::optional<size_t>>
        en_passant_get();
        bool is_en_passant(const Move &move, size_t idx);

        // Special Cases
        bool is_check();
        bool is_checkmate();
        bool is_draw();
        bool is_pat();

        // manage turn
        int turn_get() const;
        void set_turn(int turn);

        bool is_white_turn();

        // pretty-print board
        void print_board();

        bool white_king_castling_get();
        bool white_queen_castling_get();
        bool black_king_castling_get();
        bool black_queen_castling_get();

        bool is_queen_castling();
        bool is_king_castling();

        void do_wr_king_castling();
        void do_wr_queen_castling();
        void do_br_king_castling();
        void do_br_queen_castling();

        bool queen_castling_get();
        bool king_castling_get();

        int count_50_turns_get();

        std::optional<std::pair<PieceType, Color>> operator[](int i) const;

    private:
        /* White Pieces */
        std::bitset<64> white_pawns_;
        std::bitset<64> white_bishops_;
        std::bitset<64> white_knights_;
        std::bitset<64> white_rooks_;
        std::bitset<64> white_queen_;
        std::bitset<64> white_king_;

        /* Black Pieces */
        std::bitset<64> black_pawns_;
        std::bitset<64> black_bishops_;
        std::bitset<64> black_knights_;
        std::bitset<64> black_rooks_;
        std::bitset<64> black_queen_;
        std::bitset<64> black_king_;

        /* Game Informations */
        unsigned turn_;
        bool white_turn_;
        std::pair<std::optional<size_t>, std::optional<size_t>> en_passant_;

        /* Castling */
        bool white_king_castling_;
        bool white_queen_castling_;
        bool black_king_castling_;
        bool black_queen_castling_;

        /* 50 turns */
        int count_50_turns_;
    };
} // namespace board

#include "chessboard.hxx"
