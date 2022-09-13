#include <move-ordering.hh>
#include <rule.hh>

namespace ai
{
    static int get_piece_material(board::PieceType pt)
    {
        switch (pt)
        {
        case board::PieceType::QUEEN:
            return ai::PieceValue::QUEEN;
        case board::PieceType::ROOK:
            return ai::PieceValue::ROOK;
        case board::PieceType::BISHOP:
            return ai::PieceValue::BISHOP;
        case board::PieceType::KNIGHT:
            return ai::PieceValue::KNIGHT;
        case board::PieceType::PAWN:
            return ai::PieceValue::PAWN;
        default:
            return 0;
        }
    }

    template <typename A, typename B>
    static void zip(const std::vector<A> &a, const std::vector<B> &b,
                    std::vector<std::pair<A, B>> &zipped)
    {
        for (size_t i = 0; i < a.size(); ++i)
        {
            zipped.push_back(std::make_pair(a[i], b[i]));
        }
    }

    template <typename A, typename B>
    static void unzip(const std::vector<std::pair<A, B>> &zipped,
                      std::vector<A> &a, std::vector<B> &b)
    {
        for (size_t i = 0; i < a.size(); i++)
        {
            a[i] = zipped[i].first;
            b[i] = zipped[i].second;
        }
    }

    static bool is_under_pawn_attack(board::ChessBoard &board, board::Move &m)
    {
        auto sim = board;
        sim.do_move(m);

        sim.set_turn(sim.turn_get() + 1);
        auto pawn_simulation =
            board::rule::generate_one_pawn_moves(sim, m.end_pos_get());
        for (auto &m : pawn_simulation)
        {
            auto capture = m.capture_get();
            if (capture != std::nullopt
                && capture.value() == board::PieceType::PAWN)
                return true;
        }
        return false;
    }

    std::vector<board::Move> order_moves(board::ChessBoard &board,
                                         std::vector<board::Move> moves)
    {
        std::vector<int> scores;
        for (auto &m : moves)
        {
            int bonus_score = 0;
            auto moved_piece = m.piece_get();
            auto capture = m.capture_get();

            if (capture != std::nullopt)
                bonus_score += 10 * get_piece_material(capture.value())
                    - get_piece_material(moved_piece);

            if (m.is_promotion_get())
                bonus_score +=
                    get_piece_material(m.promotion_type_get().value());

            if (is_under_pawn_attack(board, m))
                bonus_score -= 350;

            scores.emplace_back(bonus_score);
        }

        // Sort moves with decreasing order of moves_score
        std::vector<std::pair<board::Move, int>> zipped;
        zip(moves, scores, zipped);

        std::sort(
            zipped.begin(), zipped.end(),
            [&](const auto &a, const auto &b) { return a.second > b.second; });

        unzip(zipped, moves, scores);

        return moves;
    }
} // namespace ai
