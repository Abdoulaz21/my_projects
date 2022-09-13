#include "rule.hh"

#include <iostream>
namespace board
{
    namespace rule
    {
        /*****************
         *     PAWNS     *
         *****************
         */

        static inline moves generate_promoted_moves(
            const std::bitset<64> &start_pos, const std::bitset<64> &end_pos,
            const Color &color, const std::optional<PieceType> &piece_captured)
        {
            moves res = {};
            PieceType pawn = PieceType::PAWN;
            PieceType queen = PieceType::QUEEN;
            PieceType rook = PieceType::ROOK;
            PieceType bishop = PieceType::BISHOP;
            PieceType knight = PieceType::KNIGHT;

            Move queen_move = { start_pos,      end_pos,      pawn, color,
                                piece_captured, std::nullopt, true, queen };
            res.emplace_back(queen_move);

            Move rook_move = { start_pos,      end_pos,      pawn, color,
                               piece_captured, std::nullopt, true, rook };
            res.emplace_back(rook_move);

            Move bishop_move = { start_pos,      end_pos,      pawn, color,
                                 piece_captured, std::nullopt, true, bishop };
            res.emplace_back(bishop_move);

            Move knight_move = { start_pos,      end_pos,      pawn, color,
                                 piece_captured, std::nullopt, true, knight };
            res.emplace_back(knight_move);

            return res;
        }

        static inline moves white_pawn_moves(ChessBoard &board,
                                             const std::bitset<64> &start_pos,
                                             size_t idx, const Color &color)
        {
            moves res = {};
            PieceType type = PieceType::PAWN;

            auto en_passant = board.en_passant_get();
            if (en_passant.first != std::nullopt
                && idx == en_passant.first.value())
            {
                auto end_pos = start_pos << 9;
                std::optional<size_t> opt(idx + 1);
                res.emplace_back(start_pos, end_pos, type, color, type, opt);
            }

            if (en_passant.second != std::nullopt
                && idx == en_passant.second.value())
            {
                auto end_pos = start_pos << 7;
                std::optional<size_t> opt(idx - 1);
                res.emplace_back(start_pos, end_pos, type, color, type, opt);
            }
            bool is_promoted = (idx / 8) == 6;
            if (board[idx + 8] == std::nullopt)
            {
                if ((idx / 8 == 1) && board[idx + 16] == std::nullopt)
                {
                    auto end_pos = start_pos << 16;
                    res.emplace_back(start_pos, end_pos, type, color);
                }

                auto end_pos = start_pos << 8;
                if (is_promoted)
                {
                    auto promo = generate_promoted_moves(start_pos, end_pos,
                                                         color, std::nullopt);
                    res.insert(res.end(), promo.begin(), promo.end());
                }
                else
                    res.emplace_back(start_pos, end_pos, type, color);
            }

            std::optional<std::pair<PieceType, Color>> left_up = std::nullopt;
            if ((idx % 8) != 0 && idx + 7 < 64)
                left_up = board[idx + 7];
            if (left_up != std::nullopt && left_up.value().second != color)
            {
                auto end_pos = start_pos << 7;
                if (is_promoted)
                {
                    auto promo = generate_promoted_moves(
                        start_pos, end_pos, color, left_up.value().first);
                    res.insert(res.end(), promo.begin(), promo.end());
                }
                else
                    res.emplace_back(start_pos, end_pos, type, color,
                                     left_up.value().first);
            }
            std::optional<std::pair<PieceType, Color>> right_up = std::nullopt;
            if ((idx % 8) != 7 && idx + 9 < 64)
                right_up = board[idx + 9];
            if (right_up != std::nullopt && right_up.value().second != color)
            {
                auto end_pos = start_pos << 9;
                if (is_promoted)
                {
                    auto promo = generate_promoted_moves(
                        start_pos, end_pos, color, right_up.value().first);
                    res.insert(res.end(), promo.begin(), promo.end());
                }
                else
                    res.emplace_back(start_pos, end_pos, type, color,
                                     right_up.value().first);
            }

            return res;
        }

        static inline moves black_pawn_moves(ChessBoard &board,
                                             const std::bitset<64> &start_pos,
                                             size_t idx, const Color &color)
        {
            moves res = {};
            PieceType type = PieceType::PAWN;

            auto en_passant = board.en_passant_get();
            if (en_passant.first != std::nullopt
                && idx == en_passant.first.value())
            {
                auto end_pos = start_pos >> 7;
                std::optional<size_t> opt(idx + 1);
                res.emplace_back(start_pos, end_pos, type, color, type, opt);
            }

            if (en_passant.second != std::nullopt
                && idx == en_passant.second.value())
            {
                auto end_pos = start_pos >> 9;
                std::optional<size_t> opt(idx - 1);
                res.emplace_back(start_pos, end_pos, type, color, type, opt);
            }
            bool is_promoted = (idx / 8) == 1;
            if (board[idx - 8] == std::nullopt)
            {
                if (idx >= 48 && idx <= 55 && board[idx - 16] == std::nullopt)
                {
                    auto end_pos = start_pos >> 16;
                    res.emplace_back(start_pos, end_pos, type, color);
                }

                auto end_pos = start_pos >> 8;
                if (is_promoted)
                {
                    auto promo = generate_promoted_moves(start_pos, end_pos,
                                                         color, std::nullopt);
                    res.insert(res.end(), promo.begin(), promo.end());
                }
                else
                    res.emplace_back(start_pos, end_pos, type, color);
            }

            std::optional<std::pair<PieceType, Color>> left_up = std::nullopt;
            if ((idx % 8) != 7 && idx >= 7)
                left_up = board[idx - 7];
            if (left_up != std::nullopt && left_up.value().second != color)
            {
                auto end_pos = start_pos >> 7;
                if (is_promoted)
                {
                    auto promo = generate_promoted_moves(
                        start_pos, end_pos, color, left_up.value().first);
                    res.insert(res.end(), promo.begin(), promo.end());
                }
                else
                    res.emplace_back(start_pos, end_pos, type, color,
                                     left_up.value().first);
            }
            std::optional<std::pair<PieceType, Color>> right_up = std::nullopt;
            if ((idx % 8) != 0 && idx >= 9)
                right_up = board[idx - 9];
            if (right_up != std::nullopt && right_up.value().second != color)
            {
                auto end_pos = start_pos >> 9;
                if (is_promoted)
                {
                    auto promo = generate_promoted_moves(
                        start_pos, end_pos, color, right_up.value().first);
                    res.insert(res.end(), promo.begin(), promo.end());
                }
                else
                    res.emplace_back(start_pos, end_pos, type, color,
                                     right_up.value().first);
            }

            return res;
        }

        moves generate_one_pawn_moves(ChessBoard &board,
                                      const std::bitset<64> &start_pos)
        {
            Color color = board.is_white_turn() ? Color::WHITE : Color::BLACK;

            size_t idx = __builtin_ctzll(start_pos.to_ullong());

            if (color == Color::WHITE)
                return white_pawn_moves(board, start_pos, idx, color);

            return black_pawn_moves(board, start_pos, idx, color);
        }

        moves generate_pawn_moves(ChessBoard &board)
        {
            moves all_pawns_moves = {};

            std::bitset<64> pawns;
            if (board.is_white_turn())
                pawns = board.white_pawns_get();
            else
                pawns = board.black_pawns_get();

            int size = __builtin_popcountll(pawns.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(pawns.to_ullong());

                std::bitset<64> current;
                current.set(last_bit_index);
                auto one_pawn_moves = generate_one_pawn_moves(board, current);
                all_pawns_moves.insert(all_pawns_moves.end(),
                                       one_pawn_moves.begin(),
                                       one_pawn_moves.end());

                pawns.reset(last_bit_index);
            }

            board.en_passant_left_set(std::nullopt);
            board.en_passant_right_set(std::nullopt);

            return all_pawns_moves;
        }

        /*****************
         *     KING      *
         *****************
         */

        moves generate_king_moves(ChessBoard &board)
        {
            std::bitset<64> start_pos = board.is_white_turn()
                ? board.white_king_get()
                : board.black_king_get();

            size_t idx = __builtin_ctzll(start_pos.to_ullong());

            moves res = {};
            PieceType type = PieceType::KING;
            Color color = board.is_white_turn() ? Color::WHITE : Color::BLACK;

            std::bitset<64> attack_board =
                MoveGenerator::instance().king_moves_table_get()[idx];

            int size = __builtin_popcountll(attack_board.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(attack_board.to_ullong());
                auto board_actual = board[last_bit_index];
                std::bitset<64> end_pos;
                end_pos.set(last_bit_index);
                if (board_actual == std::nullopt)
                {
                    res.emplace_back(start_pos, end_pos, type, color);
                }
                else if (board_actual.value().second != color)
                    res.emplace_back(start_pos, end_pos, type, color,
                                     board_actual.value().first);
                attack_board.reset(last_bit_index);
            }
            return res;
        }

        /*****************
         *    KNIGHTS    *
         *****************
         */

        moves generate_one_knight_moves(ChessBoard &board,
                                        const std::bitset<64> &start_pos)
        {
            moves res = {};
            PieceType type = PieceType::KNIGHT;
            Color color = board.is_white_turn() ? Color::WHITE : Color::BLACK;

            int index = __builtin_ctzll(start_pos.to_ullong());

            std::bitset<64> attack_board =
                MoveGenerator::instance().knights_moves_table_get()[index];
            int size = __builtin_popcountll(attack_board.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(attack_board.to_ullong());
                auto board_actual = board[last_bit_index];
                std::bitset<64> end_pos;
                end_pos.set(last_bit_index);
                if (board_actual == std::nullopt)
                {
                    res.emplace_back(start_pos, end_pos, type, color);
                }
                else if (board_actual.value().second != color)
                    res.emplace_back(start_pos, end_pos, type, color,
                                     board_actual.value().first);
                attack_board.reset(last_bit_index);
            }
            return res;
        }

        moves generate_knight_moves(ChessBoard &board)
        {
            std::bitset<64> knights;
            if (board.is_white_turn())
                knights = board.white_knights_get();
            else
                knights = board.black_knights_get();

            moves all_knights_moves = {};
            int size = __builtin_popcountll(knights.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(knights.to_ullong());
                std::bitset<64> current;
                current.set(last_bit_index);
                auto one_knight_moves =
                    generate_one_knight_moves(board, current);
                all_knights_moves.insert(all_knights_moves.end(),
                                         one_knight_moves.begin(),
                                         one_knight_moves.end());
                knights.reset(last_bit_index);
            }

            return all_knights_moves;
        }

        /******************
         * SLIDING PIECES *
         ******************
         */

        /*****************
         *     ROOKS     *
         *****************
         */

        moves generate_one_rook_moves(ChessBoard &board,
                                      const std::bitset<64> &start_pos)
        {
            int idx = __builtin_ctzll(start_pos.to_ullong());

            moves res = {};
            auto rook_type = PieceType::ROOK;
            auto rook_color =
                (board.is_white_turn()) ? Color::WHITE : Color::BLACK;

            auto b = board.chessboard_get();
            SlidePieces::instance().position_set(idx, b);
            std::bitset<64> attacks =
                SlidePieces::instance().rook_attacks(b, idx);

            int size = __builtin_popcountll(attacks.to_ullong());

            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(attacks.to_ullong());
                auto board_actual = board[last_bit_index];
                std::bitset<64> end_pos;
                end_pos.set(last_bit_index);
                if (board_actual == std::nullopt)
                {
                    res.emplace_back(start_pos, end_pos, rook_type, rook_color);
                }
                else if (board_actual.value().second != rook_color)
                    res.emplace_back(start_pos, end_pos, rook_type, rook_color,
                                     board_actual.value().first);
                attacks.reset(last_bit_index);
            }
            return res;
        }

        moves generate_rook_moves(ChessBoard &board)
        {
            moves all_rooks_moves = {};

            std::bitset<64> rooks;
            if (board.is_white_turn())
                rooks = board.white_rooks_get();
            else
                rooks = board.black_rooks_get();

            int size = __builtin_popcountll(rooks.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(rooks.to_ullong());
                std::bitset<64> current;
                current.set(last_bit_index);
                auto one_rook_moves = generate_one_rook_moves(board, current);
                all_rooks_moves.insert(all_rooks_moves.end(),
                                       one_rook_moves.begin(),
                                       one_rook_moves.end());
                rooks.reset(last_bit_index);
            }

            return all_rooks_moves;
        }

        /*****************
         *    BISHOPS    *
         *****************
         */

        moves generate_one_bishop_moves(ChessBoard &board,
                                        const std::bitset<64> &start_pos)
        {
            int idx = __builtin_ctzll(start_pos.to_ullong());

            moves res = {};
            auto bishop_type = PieceType::BISHOP;
            auto bishop_color =
                (board.is_white_turn()) ? Color::WHITE : Color::BLACK;

            auto b = board.chessboard_get();
            SlidePieces::instance().position_set(idx, b);
            std::bitset<64> attacks =
                SlidePieces::instance().bishop_attacks(b, idx);

            int size = __builtin_popcountll(attacks.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(attacks.to_ullong());
                auto board_actual = board[last_bit_index];
                std::bitset<64> end_pos;
                end_pos.set(last_bit_index);
                if (board_actual == std::nullopt)
                {
                    res.emplace_back(start_pos, end_pos, bishop_type,
                                     bishop_color);
                }
                else if (board_actual.value().second != bishop_color)
                    res.emplace_back(start_pos, end_pos, bishop_type,
                                     bishop_color, board_actual.value().first);
                attacks.reset(last_bit_index);
            }
            return res;
        }

        moves generate_bishop_moves(ChessBoard &board)
        {
            moves all_bishops_moves = {};

            std::bitset<64> bishops;
            if (board.is_white_turn())
                bishops = board.white_bishops_get();
            else
                bishops = board.black_bishops_get();

            int size = __builtin_popcountll(bishops.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(bishops.to_ullong());
                std::bitset<64> current;
                current.set(last_bit_index);
                auto one_bishop_moves =
                    generate_one_bishop_moves(board, current);
                all_bishops_moves.insert(all_bishops_moves.end(),
                                         one_bishop_moves.begin(),
                                         one_bishop_moves.end());
                bishops.reset(last_bit_index);
            }

            return all_bishops_moves;
        }

        /*****************
         *     QUEENS    *
         *****************
         */

        moves generate_queen_moves(ChessBoard &board)
        {
            moves all_queen_moves = {};

            std::bitset<64> queen;
            if (board.is_white_turn())
                queen = board.white_queen_get();
            else
                queen = board.black_queen_get();

            PieceType pt = PieceType::QUEEN;
            int size = __builtin_popcountll(queen.to_ullong());
            for (int i = 0; i < size; i++)
            {
                int last_bit_index = __builtin_ctzll(queen.to_ullong());
                std::bitset<64> current;
                current.set(last_bit_index);
                auto one_bishop_moves =
                    generate_one_bishop_moves(board, current);
                for (auto &m : one_bishop_moves)
                {
                    m.piece_set(pt);
                    all_queen_moves.emplace_back(m);
                }

                auto one_rook_moves = generate_one_rook_moves(board, current);
                for (auto &m : one_rook_moves)
                {
                    m.piece_set(pt);
                    all_queen_moves.emplace_back(m);
                }
                queen.reset(last_bit_index);
            }

            return all_queen_moves;
        }

    } // namespace rule
} // namespace board
