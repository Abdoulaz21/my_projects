#include "move.hh"

namespace board
{
    Move::Move(const std::string &s)
    {
        int start_col = (s[0] - 'a');
        int start_line = (s[1] - '1');

        int end_col = (s[2] - 'a');
        int end_line = (s[3] - '1');

        start_pos_.set(start_line * 8 + start_col);
        end_pos_.set(end_line * 8 + end_col);

        char prom = '\0';
        if (s.size() == 5)
            prom = s[4];

        is_promotion_ = (prom) ? true : false;

        if (is_promotion_)
        {
            switch (prom)
            {
            case 'q':
                promotion_type_ = PieceType::QUEEN;
                break;
            case 'r':
                promotion_type_ = PieceType::ROOK;
                break;
            case 'b':
                promotion_type_ = PieceType::BISHOP;
                break;
            case 'n':
                promotion_type_ = PieceType::KNIGHT;
                break;
            default:
                break;
            }
        }
    }

    static inline int get_idx(const std::bitset<64> &pos)
    {
        if (__builtin_popcountll(pos.to_ullong()) == 0)
            return -1;

        return __builtin_ctzll(pos.to_ullong());
    }

    static inline std::string get_col(int i)
    {
        switch (i)
        {
        case 0:
            return "a";
        case 1:
            return "b";
        case 2:
            return "c";
        case 3:
            return "d";
        case 4:
            return "e";
        case 5:
            return "f";
        case 6:
            return "g";
        case 7:
            return "h";
        }

        return "x";
    }

    static inline std::optional<std::string>
    get_promotion_letter(const board::Move &m)
    {
        if (!m.is_promotion_get())
            return std::nullopt;

        auto p = m.promotion_type_get().value();
        switch (p)
        {
        case board::PieceType::QUEEN:
            return "q";
        case board::PieceType::ROOK:
            return "r";
        case board::PieceType::BISHOP:
            return "b";
        case board::PieceType::KNIGHT:
            return "n";
        default:
            return std::nullopt;
        }
    }

    bool Move::operator==(const Move &other) const
    {
        if (start_pos_ != other.start_pos_get())
            return false;
        if (end_pos_ != other.end_pos_get())
            return false;
        if (piece_ != other.piece_get())
            return false;
        if (color_ != other.color_get())
            return false;
        if (piece_captured_ != other.capture_get())
            return false;
        if (en_passant_idx_ != other.en_passant_idx_get())
            return false;
        if (is_promotion_ != other.is_promotion_get())
            return false;
        if (promotion_type_get() != other.promotion_type_get())
            return false;

        return true;
    }

    const std::string Move::to_uci()
    {
        int sidx = get_idx(start_pos_get());
        int eidx = get_idx(end_pos_get());

        int sline = sidx / 8 + 1;
        int scol = sidx % 8;

        int eline = eidx / 8 + 1;
        int ecol = eidx % 8;

        std::string sletter = get_col(scol);
        std::string eletter = get_col(ecol);

        auto promotion = get_promotion_letter(*this);

        if (promotion != std::nullopt)
            return sletter + std::to_string(sline) + eletter
                + std::to_string(eline) + promotion.value();

        return sletter + std::to_string(sline) + eletter
            + std::to_string(eline);
    }

    std::ostream &operator<<(std::ostream &ostr, const board::Move &m)
    {
        int sidx = get_idx(m.start_pos_get());
        int eidx = get_idx(m.end_pos_get());

        int sline = sidx / 8 + 1;
        int scol = sidx % 8;

        int eline = eidx / 8 + 1;
        int ecol = eidx % 8;

        std::string sletter = get_col(scol);
        std::string eletter = get_col(ecol);

        if (!m.is_promotion_get())
            ostr << sletter << sline << eletter << eline;
        else
        {
            auto p = m.promotion_type_get().value();
            std::string c;
            switch (p)
            {
            case board::PieceType::QUEEN:
                c = "q";
                break;
            case board::PieceType::ROOK:
                c = "r";
                break;
            case board::PieceType::BISHOP:
                c = "b";
                break;
            case board::PieceType::KNIGHT:
                c = "n";
                break;
            default:
                c = "X";
                break;
            }
            ostr << sletter << sline << eletter << eline << c;
        }

        return ostr;
    }
} // namespace board
