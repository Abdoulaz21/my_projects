#include "chessboard.hh"

namespace board
{
    inline const std::bitset<64> &ChessBoard::white_pawns_get()
    {
        return white_pawns_;
    }
    inline const std::bitset<64> &ChessBoard::white_bishops_get()
    {
        return white_bishops_;
    }
    inline const std::bitset<64> &ChessBoard::white_knights_get()
    {
        return white_knights_;
    }
    inline const std::bitset<64> &ChessBoard::white_rooks_get()
    {
        return white_rooks_;
    }
    inline const std::bitset<64> &ChessBoard::white_queen_get()
    {
        return white_queen_;
    }
    inline const std::bitset<64> &ChessBoard::white_king_get()
    {
        return white_king_;
    }
    inline const std::bitset<64> &ChessBoard::black_pawns_get()
    {
        return black_pawns_;
    }
    inline const std::bitset<64> &ChessBoard::black_bishops_get()
    {
        return black_bishops_;
    }
    inline const std::bitset<64> &ChessBoard::black_knights_get()
    {
        return black_knights_;
    }
    inline const std::bitset<64> &ChessBoard::black_rooks_get()
    {
        return black_rooks_;
    }
    inline const std::bitset<64> &ChessBoard::black_queen_get()
    {
        return black_queen_;
    }
    inline const std::bitset<64> &ChessBoard::black_king_get()
    {
        return black_king_;
    }

    inline bool ChessBoard::white_king_castling_get()
    {
        return white_king_castling_;
    }
    inline bool ChessBoard::white_queen_castling_get()
    {
        return white_queen_castling_;
    }
    inline bool ChessBoard::black_king_castling_get()
    {
        return black_king_castling_;
    }
    inline bool ChessBoard::black_queen_castling_get()
    {
        return black_queen_castling_;
    }

    inline bool ChessBoard::queen_castling_get()
    {
        if (is_white_turn())
            return white_queen_castling_;
        else
            return black_queen_castling_;
    }

    inline bool ChessBoard::king_castling_get()
    {
        if (is_white_turn())
            return white_king_castling_;
        else
            return black_king_castling_;
    }

    inline const std::bitset<64> ChessBoard::chessboard_get()
    {
        std::bitset<64> res;
        res |= white_pawns_get() | white_bishops_get() | white_knights_get()
            | white_rooks_get() | white_queen_get() | white_king_get()
            | black_pawns_get() | black_bishops_get() | black_knights_get()
            | black_rooks_get() | black_queen_get() | black_king_get();

        return res;
    }

    inline int ChessBoard::turn_get() const
    {
        return turn_;
    }

    inline void ChessBoard::set_turn(int turn)
    {
        turn_ = turn;
        white_turn_ = (turn_ % 2 == 0);
    }

    inline bool ChessBoard::is_white_turn()
    {
        return white_turn_;
    }

    inline std::optional<std::pair<PieceType, Color>>
    ChessBoard::operator[](int i) const
    {
        if (white_pawns_[i] == true)
            return { { PieceType::PAWN, Color::WHITE } };
        if (white_bishops_[i] == true)
            return { { PieceType::BISHOP, Color::WHITE } };
        if (white_knights_[i] == true)
            return { { PieceType::KNIGHT, Color::WHITE } };
        if (white_rooks_[i] == true)
            return { { PieceType::ROOK, Color::WHITE } };
        if (white_king_[i] == true)
            return { { PieceType::KING, Color::WHITE } };
        if (white_queen_[i] == true)
            return { { PieceType::QUEEN, Color::WHITE } };
        if (black_pawns_[i] == true)
            return { { PieceType::PAWN, Color::BLACK } };
        if (black_bishops_[i] == true)
            return { { PieceType::BISHOP, Color::BLACK } };
        if (black_knights_[i] == true)
            return { { PieceType::KNIGHT, Color::BLACK } };
        if (black_rooks_[i] == true)
            return { { PieceType::ROOK, Color::BLACK } };
        if (black_king_[i] == true)
            return { { PieceType::KING, Color::BLACK } };
        if (black_queen_[i] == true)
            return { { PieceType::QUEEN, Color::BLACK } };

        return std::nullopt;
    }

    inline void ChessBoard::en_passant_left_set(std::optional<size_t> idx)
    {
        if (idx == std::nullopt)
            en_passant_.first = idx;

        std::optional<size_t> val(idx);
        en_passant_.first = val;
    }
    inline void ChessBoard::en_passant_right_set(std::optional<size_t> idx)
    {
        if (idx == std::nullopt)
            en_passant_.second = idx;

        std::optional<size_t> val(idx);
        en_passant_.second = val;
    }
    inline std::pair<std::optional<size_t>, std::optional<size_t>>
    ChessBoard::en_passant_get()
    {
        return en_passant_;
    }

    inline int ChessBoard::count_50_turns_get()
    {
        return count_50_turns_;
    }
} // namespace board
