#pragma once

#include <bitset>
#include <ostream>

#include "color.hh"
#include "piece-type.hh"

namespace board
{
    class Move
    {
    public:
        Move(const std::bitset<64> &start_pos, const std::bitset<64> &end_pos,
             const PieceType &piece, const Color &color,
             std::optional<PieceType> piece_captured = std::nullopt,
             std::optional<size_t> en_passant_idx = std::nullopt,
             bool is_promotion = false,
             std::optional<PieceType> promotion_type = std::nullopt)
            : start_pos_(start_pos)
            , end_pos_(end_pos)
            , piece_(piece)
            , color_(color)
            , piece_captured_(piece_captured)
            , en_passant_idx_(en_passant_idx)
            , is_promotion_(is_promotion)
            , promotion_type_(promotion_type)
        {}

        Move(const std::string &s);

        const std::bitset<64> &start_pos_get() const;
        std::bitset<64> &start_pos_get();

        const std::bitset<64> &end_pos_get() const;
        std::bitset<64> &end_pos_get();

        PieceType piece_get() const;
        void piece_set(const PieceType &pt);

        Color color_get() const;
        void color_set(const Color &c);

        const std::optional<PieceType> capture_get() const;
        void capture_set(const std::optional<PieceType> capture);

        const std::optional<size_t> en_passant_idx_get() const;
        void en_passant_idx_set(std::optional<size_t> &idx);

        bool is_promotion_get() const;

        std::optional<PieceType> promotion_type_get() const;

        bool operator==(const Move &other) const;

        const std::string to_uci();

    private:
        std::bitset<64> start_pos_;
        std::bitset<64> end_pos_;
        PieceType piece_;
        Color color_;
        std::optional<PieceType> piece_captured_;
        std::optional<size_t> en_passant_idx_;
        bool is_promotion_;
        std::optional<PieceType> promotion_type_;
    };

    std::ostream &operator<<(std::ostream &ostr, const board::Move &m);
} // namespace board

#include "move.hxx"
