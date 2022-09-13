#include <sstream>
#include <uci-parser.hh>

namespace ai
{
    std::string parse_fen(std::string get_board)
    {
        std::string res;
        size_t fen_pos = get_board.find("fen");
        size_t moves_pos = get_board.find("moves");

        if (moves_pos != std::string::npos)
            res = get_board.substr(fen_pos + 4, moves_pos);
        else
            res = get_board.substr(fen_pos + 4);

        return res;
    }

    std::vector<std::string> get_vect_moves(std::string get_board)
    {
        std::istringstream oss(get_board);

        std::vector<std::string> res = {};

        size_t moves_pos = get_board.find("moves");
        if (moves_pos != std::string::npos)
        {
            std::string tmp;
            while (oss >> tmp)
            {
                if (tmp != "moves")
                    continue;
                while (oss >> tmp)
                    res.emplace_back(tmp);
            }
        }

        return res;
    }

    static std::optional<size_t>
    get_en_passant_idx(board::ChessBoard &simulation_board,
                       const board::Color &color, size_t start)
    {
        if (color == board::Color::WHITE)
        {
            auto ep = simulation_board.en_passant_get();
            if (ep.first != std::nullopt && start == ep.first.value())
                return std::optional<size_t>(start + 1);

            if (ep.second != std::nullopt && start == ep.second.value())
                return std::optional<size_t>(start - 1);
        }
        else
        {
            auto ep = simulation_board.en_passant_get();
            if (ep.first != std::nullopt && start == ep.first.value())
                return std::optional<size_t>(start + 1);

            if (ep.second != std::nullopt && start == ep.second.value())
                return std::optional<size_t>(start - 1);
        }

        return std::nullopt;
    }

    board::ChessBoard parse_moves(board::ChessBoard &board,
                                  std::vector<std::string> &uci_moves)
    {
        for (auto &m : uci_moves)
        {
            board::Move move = board::Move(m);

            int idx = 0;
            for (int i = 0; i < 64; i++)
            {
                if (move.start_pos_get()[i])
                    break;
                idx++;
            }

            auto moved_piece = board[idx];
            move.piece_set(moved_piece.value().first);
            move.color_set(moved_piece.value().second);

            int end_idx = 0;
            for (int i = 0; i < 64; i++)
            {
                if (move.end_pos_get()[i])
                    break;
                end_idx++;
            }

            move.capture_set(std::nullopt);

            auto en_passant_idx =
                get_en_passant_idx(board, move.color_get(), idx);
            if (en_passant_idx != std::nullopt)
            {
                move.en_passant_idx_set(en_passant_idx);
                auto capture_en_passant = board[en_passant_idx.value()];
                if (capture_en_passant != std::nullopt)
                    move.capture_set(capture_en_passant.value().first);
            }
            else
            {
                auto captured_piece = board[end_idx];
                if (captured_piece != std::nullopt)
                    move.capture_set(captured_piece.value().first);
            }

            board.en_passant_left_set(std::nullopt);
            board.en_passant_right_set(std::nullopt);

            board.do_move(move);
        }
        return board;
    }
} // namespace ai
