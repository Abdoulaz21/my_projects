#include "move.hh"

namespace board
{
    inline const std::bitset<64> &Move::start_pos_get() const
    {
        return start_pos_;
    }
    inline std::bitset<64> &Move::start_pos_get()
    {
        return start_pos_;
    }

    inline const std::bitset<64> &Move::end_pos_get() const
    {
        return end_pos_;
    }
    inline std::bitset<64> &Move::end_pos_get()
    {
        return end_pos_;
    }

    inline board::PieceType Move::piece_get() const
    {
        return piece_;
    }
    inline void Move::piece_set(const PieceType &pt)
    {
        piece_ = pt;
    }

    inline board::Color Move::color_get() const
    {
        return color_;
    }
    inline void Move::color_set(const Color &c)
    {
        color_ = c;
    }

    inline const std::optional<board::PieceType> Move::capture_get() const
    {
        return piece_captured_;
    }
    inline void Move::capture_set(const std::optional<PieceType> capture)
    {
        piece_captured_ = capture;
    }

    inline const std::optional<size_t> Move::en_passant_idx_get() const
    {
        return en_passant_idx_;
    }
    inline void Move::en_passant_idx_set(std::optional<size_t> &idx)
    {
        en_passant_idx_ = idx;
    }

    inline bool Move::is_promotion_get() const
    {
        return is_promotion_;
    }

    inline std::optional<PieceType> Move::promotion_type_get() const
    {
        return promotion_type_;
    }
} // namespace board
