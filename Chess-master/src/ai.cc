#include <ai.hh>

namespace ai
{
    int search_rec(board::ChessBoard &board, int depth, int alpha, int beta)
    {
        if (!depth)
            return quiescence_search(board, alpha, beta);

        if (board.is_pat())
            return 0;
        if (board.is_checkmate())
            return NINF - depth;

        auto moves = board.generate_legal_moves();
        moves = ai::order_moves(board, moves);
        for (auto &m : moves)
        {
            auto sim = board;
            sim.do_move(m);

            int score = -search_rec(sim, depth - 1, -beta, -alpha);
            if (score >= beta)
                return beta;

            alpha = std::max(alpha, score);
        }

        return alpha;
    }

    board::Move search(board::ChessBoard &board)
    {
        auto moves = board.generate_legal_moves();
        moves = ai::order_moves(board, moves);
        std::vector<int> moves_score;

        for (auto &m : moves)
        {
            auto sim = board;
            sim.do_move(m);
            int score = -search_rec(sim, AI_DEPTH, NINF, INF);

            moves_score.emplace_back(score);
        }

        int idx = std::max_element(moves_score.begin(), moves_score.end())
            - moves_score.begin();

        return moves[idx];
    }
} // namespace ai
