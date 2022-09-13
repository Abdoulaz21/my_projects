#include <cmath>
#include <evaluation.hh>

namespace ai
{
    static int count_material(board::ChessBoard &board, const board::Color c)
    {
        int material = 0;
        if (c == board::Color::WHITE)
        {
            material += board.white_pawns_get().count() * PieceValue::PAWN;
            material += board.white_knights_get().count() * PieceValue::KNIGHT;
            material += board.white_bishops_get().count() * PieceValue::BISHOP;
            material += board.white_rooks_get().count() * PieceValue::ROOK;
            material += board.white_queen_get().count() * PieceValue::QUEEN;
        }
        else
        {
            material += board.black_pawns_get().count() * PieceValue::PAWN;
            material += board.black_knights_get().count() * PieceValue::KNIGHT;
            material += board.black_bishops_get().count() * PieceValue::BISHOP;
            material += board.black_rooks_get().count() * PieceValue::ROOK;
            material += board.black_queen_get().count() * PieceValue::QUEEN;
        }

        return material;
    }

    /* Higher Return Value = Advantage
     * Lower Return Value = Disadvantage
     **/
    int evaluate(board::ChessBoard &board)
    {
        int white_score = 0;
        int black_score = 0;

        int white_material = count_material(board, board::Color::WHITE);
        int black_material = count_material(board, board::Color::BLACK);

        int white_material_no_pawns = white_material
            - (board.white_pawns_get().count() * PieceValue::PAWN);
        int black_material_no_pawns = black_material
            - (board.black_pawns_get().count() * PieceValue::PAWN);
        double white_endgmae_weight =
            endgame_weight_get(white_material_no_pawns);
        double black_endgmae_weight =
            endgame_weight_get(black_material_no_pawns);

        white_material += evaluate_pieces_heat_map(board, true);
        black_material += evaluate_pieces_heat_map(board, false);

        white_score += white_material;
        black_score += black_material;

        if (board.is_white_turn())
            white_score += force_king_to_corner_endgame(board.white_king_get(),
                                                        board.black_king_get(),
                                                        white_endgmae_weight);
        else
            black_score += force_king_to_corner_endgame(board.black_king_get(),
                                                        board.white_king_get(),
                                                        black_endgmae_weight);

        int evaluation = white_material - black_material;
        int sign = (board.is_white_turn()) ? 1 : -1;
        return sign * evaluation;
    }

    double endgame_weight_get(int material_no_pawn)
    {
        const double coef = 1 / endgame_start;
        return 1 - std::min(1.0, material_no_pawn * coef);
    }

    int evaluate_pieces_heat_map(board::ChessBoard &board, bool is_white)
    {
        int score = 0;

        if (is_white)
        {
            score += evaluate_piece_heat_map(PieceHeatMap::w_pawn,
                                             board.white_pawns_get());
            score += evaluate_piece_heat_map(PieceHeatMap::w_knight,
                                             board.white_knights_get());
            score += evaluate_piece_heat_map(PieceHeatMap::w_bishop,
                                             board.white_bishops_get());
            score += evaluate_piece_heat_map(PieceHeatMap::w_rook,
                                             board.white_rooks_get());
            score += evaluate_piece_heat_map(PieceHeatMap::w_queen,
                                             board.white_queen_get());
            score += evaluate_piece_heat_map(PieceHeatMap::w_king_middle,
                                             board.white_king_get());
        }
        else
        {
            score += evaluate_piece_heat_map(PieceHeatMap::b_pawn,
                                             board.black_pawns_get());
            score += evaluate_piece_heat_map(PieceHeatMap::b_knight,
                                             board.black_knights_get());
            score += evaluate_piece_heat_map(PieceHeatMap::b_bishop,
                                             board.black_bishops_get());
            score += evaluate_piece_heat_map(PieceHeatMap::b_rook,
                                             board.black_rooks_get());
            score += evaluate_piece_heat_map(PieceHeatMap::b_queen,
                                             board.black_queen_get());
            score += evaluate_piece_heat_map(PieceHeatMap::b_king_middle,
                                             board.black_king_get());
        }

        return score;
    }

    int evaluate_piece_heat_map(const int heat_map[],
                                const std::bitset<64> &pieces)
    {
        int score = 0;
        for (int i = 0; i < 64; i++)
            if (pieces[i])
                score += heat_map[i];
        return score;
    }

    int force_king_to_corner_endgame(const std::bitset<64> &friendly_king,
                                     const std::bitset<64> &opponent_king,
                                     double endgame_weight)
    {
        int score = 0;

        int friendly_king_idx = 0;
        for (; friendly_king_idx < 64; friendly_king_idx++)
            if (friendly_king[friendly_king_idx])
                break;
        int friendly_king_col = friendly_king_idx % 8;
        int friendly_king_line = friendly_king_idx / 8;

        int opponent_king_idx = 0;
        for (; opponent_king_idx < 64; opponent_king_idx++)
            if (opponent_king[opponent_king_idx])
                break;
        int opponent_king_col = opponent_king_idx % 8;
        int opponent_king_line = opponent_king_idx / 8;

        int opponent_king_dst_center_col =
            std::max(3 - opponent_king_col, opponent_king_col - 4);
        int opponent_king_dst_center_line =
            std::max(3 - opponent_king_line, opponent_king_line - 4);
        score += opponent_king_dst_center_col + opponent_king_dst_center_line;

        int dst_col_kings = std::abs(friendly_king_col - opponent_king_col);
        int dst_line_kings = std::abs(friendly_king_line - opponent_king_line);
        score += 14 - (dst_col_kings + dst_line_kings);

        return score * 10 * endgame_weight;
    }
} // namespace ai
