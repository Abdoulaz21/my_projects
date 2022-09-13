#include "chessboard.hh"

#include <iostream>

#include "rule.hh"

namespace board
{
    ChessBoard::ChessBoard()
    {
        /* White Pieces */
        for (int i = 8; i < 16; i++)
            white_pawns_.set(i);
        white_bishops_.set(2).set(5);
        white_knights_.set(1).set(6);
        white_rooks_.set(0).set(7);
        white_queen_.set(3);
        white_king_.set(4);
        /* Black Pieces */
        for (int i = 48; i < 56; i++)
            black_pawns_.set(i);
        black_bishops_.set(58).set(61);
        black_knights_.set(57).set(62);
        black_rooks_.set(56).set(63);
        black_queen_.set(59);
        black_king_.set(60);

        /* Game Informations */
        turn_ = 0;
        white_turn_ = true;

        /* En-passant */
        en_passant_.first = std::nullopt;
        en_passant_.second = std::nullopt;

        /* Castling */
        white_king_castling_ = true;
        white_queen_castling_ = true;
        black_king_castling_ = true;
        black_queen_castling_ = true;
        count_50_turns_ = 0;
    }

    int ChessBoard::parse_fen_string_board(const std::string &fen_string)
    {
        int idx = 0;
        int col = 0;
        int line = 7;

        while (fen_string[idx] != ' ')
        {
            if (fen_string[idx] == '/')
            {
                line--;
                col = 0;
                idx++;
                continue;
            }

            if (fen_string[idx] == 'p')
                black_pawns_.set(8 * line + col);
            else if (fen_string[idx] == 'r')
                black_rooks_.set(8 * line + col);
            else if (fen_string[idx] == 'n')
                black_knights_.set(8 * line + col);
            else if (fen_string[idx] == 'b')
                black_bishops_.set(8 * line + col);
            else if (fen_string[idx] == 'q')
                black_queen_.set(8 * line + col);
            else if (fen_string[idx] == 'k')
                black_king_.set(8 * line + col);
            else if (fen_string[idx] == 'P')
                white_pawns_.set(8 * line + col);
            else if (fen_string[idx] == 'R')
                white_rooks_.set(8 * line + col);
            else if (fen_string[idx] == 'N')
                white_knights_.set(8 * line + col);
            else if (fen_string[idx] == 'B')
                white_bishops_.set(8 * line + col);
            else if (fen_string[idx] == 'Q')
                white_queen_.set(8 * line + col);
            else if (fen_string[idx] == 'K')
                white_king_.set(8 * line + col);
            else
                col += fen_string[idx] - '0' - 1;
            col++;
            idx++;
        }

        return idx;
    }

    void ChessBoard::parse_fen_string_en_passant(const std::string &fen_string,
                                                 int idx)
    {
        if (fen_string[idx] != '-')
        {
            int col = fen_string[idx] - 'a';
            int line = fen_string[++idx] - '0' - 1;
            int pos = 8 * line + col;
            if (is_white_turn())
            {
                auto white = operator[](pos - 7);
                if (white != std::nullopt
                    && white.value().first == PieceType::PAWN
                    && white.value().second == Color::WHITE)
                    en_passant_right_set(std::optional<size_t>(pos - 7));

                white = operator[](pos - 9);
                if (white != std::nullopt
                    && white.value().first == PieceType::PAWN
                    && white.value().second == Color::WHITE)
                    en_passant_left_set(std::optional<size_t>(pos - 9));
            }
            else
            {
                auto black = operator[](pos + 7);
                if (black != std::nullopt
                    && black.value().first == PieceType::PAWN
                    && black.value().second == Color::BLACK)
                    en_passant_left_set(std::optional<size_t>(pos + 7));

                black = operator[](pos + 9);
                if (black != std::nullopt
                    && black.value().first == PieceType::PAWN
                    && black.value().second == Color::BLACK)
                    en_passant_right_set(std::optional<size_t>(pos + 9));
            }
        }
    }

    ChessBoard::ChessBoard(const std::string &fen_string)
    {
        int idx = parse_fen_string_board(fen_string);

        idx++;
        white_turn_ = (fen_string[idx] == 'w') ? true : false;
        idx += 2;

        white_king_castling_ = false;
        white_queen_castling_ = false;
        black_king_castling_ = false;
        black_queen_castling_ = false;

        while (fen_string[idx] != ' ')
        {
            switch (fen_string[idx])
            {
            case 'K':
                white_king_castling_ = true;
                break;
            case 'Q':
                white_queen_castling_ = true;
                break;
            case 'k':
                black_king_castling_ = true;
                break;
            case 'q':
                black_queen_castling_ = true;
                break;
            default:
                break;
            }
            idx++;
        }
        idx++;

        parse_fen_string_en_passant(fen_string, idx);

        while (fen_string[idx] != ' ')
            idx++;
        idx++;

        std::string raw_turn;
        while (fen_string[idx] != ' ')
        {
            raw_turn += fen_string[idx];
            idx++;
        }

        int turn = std::stoi(raw_turn) * 2;
        if (!is_white_turn())
            turn++;
        this->set_turn(turn);

        count_50_turns_ = 0;
    }

    void ChessBoard::print_board()
    {
        std::cout << "   A B C D E F G H\n";
        std::cout << " +-----------------+\n";
        for (int k = 7; k >= 0; k--)
        {
            std::cout << k + 1 << "| ";
            for (int j = 0; j < 8; j++)
            {
                std::optional<std::pair<PieceType, Color>> res = operator[](
                    k * 8 + j);
                if (res.has_value())
                {
                    if (j % 8 != 0)
                        std::cout << " ";

                    if (res->first == PieceType::PAWN)
                        std::cout << ((res->second == Color::WHITE) ? "\u265F"
                                                                    : "\u2659");

                    else if (res->first == PieceType::BISHOP)
                        std::cout << ((res->second == Color::WHITE) ? "\u265D"
                                                                    : "\u2657");

                    else if (res->first == PieceType::KNIGHT)
                        std::cout << ((res->second == Color::WHITE) ? "\u265E"
                                                                    : "\u2658");

                    else if (res->first == PieceType::ROOK)
                        std::cout << ((res->second == Color::WHITE) ? "\u265C"
                                                                    : "\u2656");

                    else if (res->first == PieceType::KING)
                        std::cout << ((res->second == Color::WHITE) ? "\u265A"
                                                                    : "\u2654");

                    else if (res->first == PieceType::QUEEN)
                        std::cout << ((res->second == Color::WHITE) ? "\u265B"
                                                                    : "\u2655");
                }
                else
                {
                    if (j % 8 != 0)
                        std::cout << " ";
                    std::cout << ".";
                }
            }
            std::cout << " |" << k + 1 << '\n';
        }
        std::cout << " +-----------------+\n";
        std::cout << "   A B C D E F G H\n";
    }

    bool ChessBoard::is_move_legal(const Move &move)
    {
        bool legal = false;
        auto moves = generate_legal_moves();
        for (const auto &m : moves)
        {
            if (m == move)
            {
                legal = true;
                break;
            }
        }

        if (!legal)
            return false;

        return true;
    }

    bool ChessBoard::is_legal_move(const Move &move)
    {
        ChessBoard simulation_board = *this;
        simulation_board.do_move(move);
        simulation_board.set_turn(simulation_board.turn_get() - 1);

        if (simulation_board.is_check())
            return false;
        return true;
    }

    std::vector<Move> ChessBoard::generate_legal_moves()
    {
        moves semi_legal_moves = {};

        moves pawn_moves = rule::generate_pawn_moves(*this);
        semi_legal_moves.insert(semi_legal_moves.end(), pawn_moves.begin(),
                                pawn_moves.end());

        moves knight_moves = rule::generate_knight_moves(*this);
        semi_legal_moves.insert(semi_legal_moves.end(), knight_moves.begin(),
                                knight_moves.end());

        moves king_moves = rule::generate_king_moves(*this);
        semi_legal_moves.insert(semi_legal_moves.end(), king_moves.begin(),
                                king_moves.end());

        moves queen_moves = rule::generate_queen_moves(*this);
        semi_legal_moves.insert(semi_legal_moves.end(), queen_moves.begin(),
                                queen_moves.end());

        moves rook_moves = rule::generate_rook_moves(*this);
        semi_legal_moves.insert(semi_legal_moves.end(), rook_moves.begin(),
                                rook_moves.end());

        moves bishop_moves = rule::generate_bishop_moves(*this);
        semi_legal_moves.insert(semi_legal_moves.end(), bishop_moves.begin(),
                                bishop_moves.end());

        moves res = {};
        for (const auto &m : semi_legal_moves)
            if (this->is_legal_move(m))
                res.emplace_back(m);

        if (is_king_castling())
        {
            Color color = this->is_white_turn() ? Color::WHITE : Color::BLACK;
            std::bitset<64> king_k;
            std::bitset<64> end_pos;
            if (color == Color::WHITE)
            {
                king_k = white_king_get();
                end_pos.set(6);
            }
            else
            {
                king_k = black_king_get();
                end_pos.set(62);
            }
            PieceType type = PieceType::KING;
            res.emplace_back(king_k, end_pos, type, color, std::nullopt);
        }

        if (is_queen_castling())
        {
            Color color = this->is_white_turn() ? Color::WHITE : Color::BLACK;
            std::bitset<64> king_q;
            std::bitset<64> end_pos;
            if (color == Color::WHITE)
            {
                king_q = white_king_get();
                end_pos.set(2);
            }
            else
            {
                king_q = black_king_get();
                end_pos.set(58);
            }
            PieceType type = PieceType::KING;
            res.emplace_back(king_q, end_pos, type, color, std::nullopt);
        }
        return res;
    }

    void ChessBoard::remove_captured_piece(const Move &move)
    {
        auto end_pos = move.end_pos_get();
        int idx = __builtin_ctzll(end_pos.to_ullong());

        if (move.en_passant_idx_get() != std::nullopt)
        {
            auto p = (move.color_get() == Color::WHITE) ? operator[](idx - 8) :
                                                        operator[](idx + 8);
            if (p.value().second == Color::WHITE)
                white_pawns_ ^= (end_pos << 8);
            else
                black_pawns_ ^= (end_pos >> 8);

            return;
        }

        auto piece = operator[](idx);

        auto piece_type = piece.value().first;
        if (piece.value().second == Color::WHITE)
        {
            if (piece_type == PieceType::PAWN)
                white_pawns_ ^= end_pos;
            else if (piece_type == PieceType::KNIGHT)
                white_knights_ ^= end_pos;
            else if (piece_type == PieceType::BISHOP)
                white_bishops_ ^= end_pos;
            else if (piece_type == PieceType::ROOK)
            {
                white_rooks_ ^= end_pos;
                if (idx == 0 && white_queen_castling_)
                    white_queen_castling_ = false;
                else if (idx == 7 && white_king_castling_)
                    white_king_castling_ = false;
            }
            else if (piece_type == PieceType::QUEEN)
                white_queen_ ^= end_pos;
        }
        else
        {
            if (piece_type == PieceType::PAWN)
                black_pawns_ ^= end_pos;
            else if (piece_type == PieceType::KNIGHT)
                black_knights_ ^= end_pos;
            else if (piece_type == PieceType::BISHOP)
                black_bishops_ ^= end_pos;
            else if (piece_type == PieceType::ROOK)
            {
                black_rooks_ ^= end_pos;
                if (idx == 56 && black_queen_castling_)
                    black_queen_castling_ = false;
                else if (idx == 63 && black_king_castling_)
                    black_king_castling_ = false;
            }
            else if (piece_type == PieceType::QUEEN)
                black_queen_ ^= end_pos;
        }
    }

    static inline int two_squares(const std::bitset<64> &start,
                                  const std::bitset<64> &end)
    {
        int start_idx = __builtin_ctzll(start.to_ullong());
        int end_idx = __builtin_ctzll(end.to_ullong());

        return (end_idx - start_idx);
    }

    static inline std::optional<size_t> do_en_passant_move(const Move &move)
    {
        if (move.piece_get() == PieceType::PAWN)
        {
            auto start = move.start_pos_get();
            auto end = move.end_pos_get();

            int start_idx = __builtin_ctzll(start.to_ullong());
            int end_idx = __builtin_ctzll(end.to_ullong());

            if (std::abs(start_idx - end_idx) == 16)
                return std::optional<size_t>(end_idx);
        }

        return std::nullopt;
    }

    bool ChessBoard::do_white_move(const Move &move, bool reset)
    {
        if (move.piece_get() == PieceType::PAWN)
        {
            count_50_turns_ = 0;
            reset = true;

            white_pawns_ ^= move.start_pos_get();
            if (move.is_promotion_get())
            {
                auto promo = move.promotion_type_get().value();
                if (promo == PieceType::QUEEN)
                    white_queen_ |= move.end_pos_get();
                else if (promo == PieceType::ROOK)
                    white_rooks_ |= move.end_pos_get();
                else if (promo == PieceType::BISHOP)
                    white_bishops_ |= move.end_pos_get();
                else if (promo == PieceType::KNIGHT)
                    white_knights_ |= move.end_pos_get();
            }
            else
                white_pawns_ |= move.end_pos_get();
        }
        else if (move.piece_get() == PieceType::KNIGHT)
            white_knights_ =
                (white_knights_ ^ move.start_pos_get()) | move.end_pos_get();
        else if (move.piece_get() == PieceType::BISHOP)
            white_bishops_ =
                (white_bishops_ ^ move.start_pos_get()) | move.end_pos_get();
        else if (move.piece_get() == PieceType::ROOK)
        {
            white_rooks_ =
                (white_rooks_ ^ move.start_pos_get()) | move.end_pos_get();
            if (move.start_pos_get()[7] == 1 && white_king_castling_ == true)
                white_king_castling_ = false;
            if (move.start_pos_get()[0] == 1 && white_queen_castling_ == true)
                white_queen_castling_ = false;
        }
        else if (move.piece_get() == PieceType::QUEEN)
            white_queen_ =
                (white_queen_ ^ move.start_pos_get()) | move.end_pos_get();
        else if (move.piece_get() == PieceType::KING)
        {
            white_king_ =
                (white_king_ ^ move.start_pos_get()) | move.end_pos_get();
            if (two_squares(move.start_pos_get(), move.end_pos_get()) == 2)
                do_wr_king_castling();
            if (two_squares(move.start_pos_get(), move.end_pos_get()) == -2)
                do_wr_queen_castling();
            if (white_king_castling_ || white_queen_castling_)
            {
                white_king_castling_ = false;
                white_queen_castling_ = false;
            }
        }

        return reset;
    }

    bool ChessBoard::do_black_move(const Move &move, bool reset)
    {
        if (move.piece_get() == PieceType::PAWN)
        {
            count_50_turns_ = 0;
            reset = true;

            black_pawns_ ^= move.start_pos_get();
            if (move.is_promotion_get())
            {
                auto promo = move.promotion_type_get().value();
                if (promo == PieceType::QUEEN)
                    black_queen_ |= move.end_pos_get();
                else if (promo == PieceType::ROOK)
                    black_rooks_ |= move.end_pos_get();
                else if (promo == PieceType::BISHOP)
                    black_bishops_ |= move.end_pos_get();
                else if (promo == PieceType::KNIGHT)
                    black_knights_ |= move.end_pos_get();
            }
            else
                black_pawns_ |= move.end_pos_get();
        }
        else if (move.piece_get() == PieceType::KNIGHT)
            black_knights_ =
                (black_knights_ ^ move.start_pos_get()) | move.end_pos_get();
        else if (move.piece_get() == PieceType::BISHOP)
            black_bishops_ =
                (black_bishops_ ^ move.start_pos_get()) | move.end_pos_get();
        else if (move.piece_get() == PieceType::ROOK)
        {
            black_rooks_ =
                (black_rooks_ ^ move.start_pos_get()) | move.end_pos_get();
            if (move.start_pos_get()[63] == 1 && black_king_castling_ == true)
                black_king_castling_ = false;
            if (move.start_pos_get()[56] == 1 && black_queen_castling_ == true)
                black_queen_castling_ = false;
        }
        else if (move.piece_get() == PieceType::QUEEN)
            black_queen_ =
                (black_queen_ ^ move.start_pos_get()) | move.end_pos_get();
        else if (move.piece_get() == PieceType::KING)
        {
            black_king_ =
                (black_king_ ^ move.start_pos_get()) | move.end_pos_get();
            if (two_squares(move.start_pos_get(), move.end_pos_get()) == 2)
                do_br_king_castling();
            if (two_squares(move.start_pos_get(), move.end_pos_get()) == -2)
                do_br_queen_castling();
            if (black_king_castling_ || black_queen_castling_)
            {
                black_king_castling_ = false;
                black_queen_castling_ = false;
            }
        }

        return reset;
    }

    void ChessBoard::do_move(const Move &move)
    {
        if (move.capture_get() != std::nullopt)
            remove_captured_piece(move);

        bool reset = false;
        if (move.capture_get() != std::nullopt)
        {
            count_50_turns_ = 0;
            reset = true;
        }

        auto en_passant = do_en_passant_move(move);
        if (en_passant != std::nullopt)
            this->is_en_passant(move, en_passant.value());

        if (move.color_get() == Color::WHITE)
            reset = do_white_move(move, reset);
        else
            reset = do_black_move(move, reset);

        if (reset == false)
            count_50_turns_++;
        set_turn(turn_ + 1);
    }

    bool ChessBoard::is_en_passant(const Move &move, size_t idx)
    {
        bool res = false;

        Color c = move.color_get();

        auto left = operator[](idx - 1);
        if (left != std::nullopt)
        {
            if (left.value().second != c
                && left.value().first == PieceType::PAWN && (idx % 8 != 0))
            {
                this->en_passant_left_set(idx - 1);
                res = true;
            }
        }

        auto right = operator[](idx + 1);
        if (right != std::nullopt)
        {
            if (right.value().second != c
                && right.value().first == PieceType::PAWN && (idx % 8 != 7))
            {
                this->en_passant_right_set(idx + 1);
                res = true;
            }
        }

        return res;
    }

    bool ChessBoard::is_check()
    {
        std::bitset<64> king;
        if (is_white_turn())
            king = white_king_;
        else
            king = black_king_;

        auto pawn_simulation = rule::generate_one_pawn_moves(*this, king);
        for (const auto &m : pawn_simulation)
        {
            auto capture = m.capture_get();
            if (capture != std::nullopt && capture.value() == PieceType::PAWN)
                return true;
        }

        auto knight_simulation = rule::generate_one_knight_moves(*this, king);
        for (const auto &m : knight_simulation)
        {
            auto capture = m.capture_get();
            if (capture != std::nullopt && capture.value() == PieceType::KNIGHT)
                return true;
        }

        auto king_simulation = rule::generate_king_moves(*this);
        for (const auto &m : king_simulation)
        {
            auto capture = m.capture_get();
            if (capture != std::nullopt && capture.value() == PieceType::KING)
                return true;
        }

        auto rook_simulation = rule::generate_one_rook_moves(*this, king);
        for (const auto &m : rook_simulation)
        {
            auto capture = m.capture_get();
            if (capture != std::nullopt
                && (capture.value() == PieceType::ROOK
                    || capture.value() == PieceType::QUEEN))
                return true;
        }

        auto bishop_simulation = rule::generate_one_bishop_moves(*this, king);
        for (const auto &m : bishop_simulation)
        {
            auto capture = m.capture_get();
            if (capture != std::nullopt
                && (capture.value() == PieceType::BISHOP
                    || capture.value() == PieceType::QUEEN))
                return true;
        }

        return false;
    }

    bool ChessBoard::is_checkmate()
    {
        if (!is_check())
            return false;

        auto simulation_board = *this;

        if (simulation_board.generate_legal_moves().empty())
            return true;
        return false;
    }

    bool ChessBoard::is_draw()
    {
        if (count_50_turns_ == 50)
            return true;
        return false;
    }

    bool ChessBoard::is_pat()
    {
        auto simulation_board = *this;

        if (!is_check() && simulation_board.generate_legal_moves().empty())
            return true;
        return false;
    }

    bool ChessBoard::is_queen_castling()
    {
        if (is_check())
            return false;
        std::bitset<64> board = this->chessboard_get();
        if (is_white_turn())
        {
            if (white_queen_castling_ && (board[1] == 0) && (board[2] == 0)
                && (board[3] == 0))
            {
                bool castling_ok = true;
                for (int i = 0; i < 2; i++)
                {
                    white_king_ >>= 1;
                    if (is_check())
                        castling_ok = false;
                }
                white_king_.reset(2).set(4);
                return castling_ok;
            }
            else
                return false;
        }
        else
        {
            if (black_queen_castling_ && (board[57] == 0) && (board[58] == 0)
                && (board[59] == 0))
            {
                bool castling_ok = true;
                for (int i = 0; i < 2; i++)
                {
                    black_king_ >>= 1;
                    if (is_check())
                        castling_ok = false;
                }
                black_king_.reset(58).set(60);
                return castling_ok;
            }
            else
                return false;
        }
        return true;
    }

    bool ChessBoard::is_king_castling()
    {
        if (is_check())
            return false;
        std::bitset<64> king;
        std::bitset<64> board = this->chessboard_get();
        if (is_white_turn())
        {
            king = white_king_;
            if (white_king_castling_ && (board[5] == 0) && (board[6] == 0))
            {
                bool castling_ok = true;
                for (int i = 0; i < 2; i++)
                {
                    white_king_ <<= 1;
                    if (is_check())
                        castling_ok = false;
                }
                white_king_.reset(6).set(4);
                return castling_ok;
            }
            else
                return false;
        }
        else
        {
            king = black_king_;
            if (black_king_castling_ && (board[61] == 0) && (board[62] == 0))
            {
                bool castling_ok = true;
                for (int i = 0; i < 2; i++)
                {
                    black_king_ <<= 1;
                    if (is_check())
                        castling_ok = false;
                }
                black_king_.reset(62).set(60);
                return castling_ok;
            }
            else
                return false;
        }
        return true;
    }

    void ChessBoard::do_wr_queen_castling()
    {
        white_rooks_.set(3);
        white_rooks_.reset(0);
    }

    void ChessBoard::do_br_queen_castling()
    {
        black_rooks_.set(59);
        black_rooks_.reset(56);
    }

    void ChessBoard::do_wr_king_castling()
    {
        white_rooks_.set(5);
        white_rooks_.reset(7);
    }

    void ChessBoard::do_br_king_castling()
    {
        black_rooks_.set(61);
        black_rooks_.reset(63);
    }

} // namespace board
