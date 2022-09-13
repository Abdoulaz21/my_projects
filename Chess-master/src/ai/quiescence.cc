#include <quiescence.hh>

namespace ai
{
    int quiescence_search(board::ChessBoard &board, int alpha, int beta)
    {
        int score = evaluate(board);
        if (score >= beta)
            return beta;
        if (score > alpha)
            alpha = score;

        auto moves = board.generate_legal_moves();
        std::vector<board::Move> capture_moves;
        for (auto &m : moves)
            if (m.capture_get() != std::nullopt)
                capture_moves.emplace_back(m);
        capture_moves = order_moves(board, capture_moves);

        for (auto &m : capture_moves)
        {
            auto sim = board;
            sim.do_move(m);
            score = -quiescence_search(sim, -beta, -alpha);

            if (score >= beta)
                return beta;
            if (score > alpha)
                alpha = score;
        }

        return alpha;
    }
} // namespace ai
