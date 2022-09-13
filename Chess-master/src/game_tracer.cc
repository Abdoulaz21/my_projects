#include "game_tracer.hh"

#include <iostream>

namespace board
{
    GameTracer::GameTracer(std::string &pgn_filepath,
                           std::vector<std::string> &listener_paths)
    {
        ChessBoard board;
        chessboard_ = board;

        std::vector<board::PgnMove> pgn_moves =
            pgn_parser::parse_pgn(pgn_filepath);

        moves_ = pgn_to_move(pgn_moves);

        if (listener_paths.size() == 0)
            return;

        for (auto &path : listener_paths)
        {
            void *handle = dlopen(path.c_str(), RTLD_LAZY);
            if (!handle)
                std::cerr << "impossible to find the library\n";
            dlerror();

            auto symbol = dlsym(handle, "listener_create");

            if (!symbol)
            {
                std::cerr << "error with dlsym\n";
                dlclose(handle);
                return;
            }

            listener::Listener *listener =
                reinterpret_cast<listener::Listener *(*)()>(symbol)();

            add_listener(listener);
            handle_vector_.emplace_back(handle);
        }
    }

    GameTracer::~GameTracer()
    {
        for (size_t i = 0; i < handle_vector_.size(); i++)
        {
            delete listeners_[i];
            dlclose(handle_vector_[i]);
        }
    }

    void GameTracer::add_listener(listener::Listener *listener)
    {
        listeners_.emplace_back(listener);
    }

    void GameTracer::remove_listener()
    {
        listeners_.pop_back();
    }

    static inline int get_pos(Position &pos)
    {
        int col = 0;
        if (pos.file_get() == File::A)
            col = 0;
        else if (pos.file_get() == File::B)
            col = 1;
        else if (pos.file_get() == File::C)
            col = 2;
        else if (pos.file_get() == File::D)
            col = 3;
        else if (pos.file_get() == File::E)
            col = 4;
        else if (pos.file_get() == File::F)
            col = 5;
        else if (pos.file_get() == File::G)
            col = 6;
        else if (pos.file_get() == File::H)
            col = 7;

        int line = 0;
        if (pos.rank_get() == Rank::ONE)
            line = 0;
        else if (pos.rank_get() == Rank::TWO)
            line = 1;
        else if (pos.rank_get() == Rank::THREE)
            line = 2;
        else if (pos.rank_get() == Rank::FOUR)
            line = 3;
        else if (pos.rank_get() == Rank::FIVE)
            line = 4;
        else if (pos.rank_get() == Rank::SIX)
            line = 5;
        else if (pos.rank_get() == Rank::SEVEN)
            line = 6;
        else if (pos.rank_get() == Rank::EIGHT)
            line = 7;

        int p = 8 * line + col;
        return p;
    }

    static inline std::optional<size_t>
    get_en_passant_idx(ChessBoard &simulation_board, Color &color, size_t start)
    {
        if (color == Color::WHITE)
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

    moves GameTracer::pgn_to_move(std::vector<PgnMove> &pgn)
    {
        moves res = {};
        int turn = 0;
        ChessBoard simulation_board;

        for (auto &p : pgn)
        {
            auto pgn_start = p.start_get();
            auto pgn_end = p.end_get();

            auto start = get_pos(pgn_start);
            std::bitset<64> start_pos;
            start_pos.set(start);
            auto end = get_pos(pgn_end);
            std::bitset<64> end_pos;
            end_pos.set(end);

            auto type = p.piece_get();
            auto color = (turn % 2) == 0 ? Color::WHITE : Color::BLACK;

            auto en_passant_idx =
                get_en_passant_idx(simulation_board, color, start);

            std::optional<PieceType> capture_type = std::nullopt;
            if (p.capture_get())
            {
                if (en_passant_idx != std::nullopt)
                {
                    auto capture_en_passant =
                        simulation_board[en_passant_idx.value()];
                    if (capture_en_passant != std::nullopt)
                        capture_type = capture_en_passant.value().first;
                }

                auto capture = simulation_board[end];
                if (capture != std::nullopt)
                    capture_type = capture.value().first;
            }

            auto pgn_promotion = p.promotion_get();
            bool promotion = false;
            std::optional<PieceType> promotion_type = std::nullopt;
            if (pgn_promotion != std::nullopt)
            {
                promotion = true;
                promotion_type = pgn_promotion.value();
            }

            Move move = { start_pos, end_pos,       type,
                          color,     capture_type,  en_passant_idx,
                          promotion, promotion_type };

            simulation_board.do_move(move);
            turn++;
            res.emplace_back(move);
        }

        return res;
    }

    static inline int two_squares(std::bitset<64> &start, std::bitset<64> &end)
    {
        int start_idx = __builtin_ctzll(start.to_ullong());
        int end_idx = __builtin_ctzll(end.to_ullong());

        return (end_idx - start_idx);
    }

    static inline bool is_castling_done(Move &move)
    {
        if (move.piece_get() == PieceType::KING)
        {
            if (two_squares(move.start_pos_get(), move.end_pos_get()) == 2)
                return true;
            if (two_squares(move.start_pos_get(), move.end_pos_get()) == -2)
                return true;
        }
        return false;
    }

    static inline Position convert_to_pos(std::bitset<64> &bs)
    {
        int col = 0;
        int line = 7;
        for (int i = 7; i >= 0; i--)
        {
            for (int j = 0; j < 8; j++)
            {
                if (bs[i * 8 + j])
                {
                    line = i;
                    col = j;
                    break;
                }
            }
        }

        File file = File::A;
        if (col == 7)
            file = File::H;
        else if (col == 6)
            file = File::G;
        else if (col == 5)
            file = File::F;
        else if (col == 4)
            file = File::E;
        else if (col == 3)
            file = File::D;
        else if (col == 2)
            file = File::C;
        else if (col == 1)
            file = File::B;
        else if (col == 0)
            file = File::A;

        Rank rank = Rank::ONE;
        if (line == 7)
            rank = Rank::EIGHT;
        else if (line == 6)
            rank = Rank::SEVEN;
        else if (line == 5)
            rank = Rank::SIX;
        else if (line == 4)
            rank = Rank::FIVE;
        else if (line == 3)
            rank = Rank::FOUR;
        else if (line == 2)
            rank = Rank::THREE;
        else if (line == 1)
            rank = Rank::TWO;
        else if (line == 0)
            rank = Rank::ONE;

        return Position(file, rank);
    }

    static inline Color opposite_color(Move &move)
    {
        if (move.color_get() == Color::WHITE)
            return Color::BLACK;
        return Color::WHITE;
    }

    void GameTracer::play_loop_game()
    {
        for (auto &move : moves_)
        {
            bool b = chessboard_.is_move_legal(move);
            if (b == false)
            {
                for (auto &l : listeners_)
                {
                    l->on_player_disqualified(move.color_get());
                    l->on_game_finished();
                    exit(0);
                }
            }

            chessboard_.do_move(move);
            if (is_castling_done(move))
            {
                for (auto &l : listeners_)
                {
                    l->on_piece_moved(PieceType::KING,
                                      convert_to_pos(move.start_pos_get()),
                                      convert_to_pos(move.end_pos_get()));
                    if (two_squares(move.start_pos_get(), move.end_pos_get())
                        == 2)
                        l->on_kingside_castling(move.color_get());
                    if (two_squares(move.start_pos_get(), move.end_pos_get())
                        == -2)
                        l->on_queenside_castling(move.color_get());
                }
            }
            else
            {
                for (auto &l : listeners_)
                    l->on_piece_moved(move.piece_get(),
                                      convert_to_pos(move.start_pos_get()),
                                      convert_to_pos(move.end_pos_get()));
                if (move.capture_get() != std::nullopt)
                {
                    for (auto &l : listeners_)
                        l->on_piece_taken(move.capture_get().value(),
                                          convert_to_pos(move.end_pos_get()));
                }
                if (move.is_promotion_get())
                    for (auto &l : listeners_)
                        l->on_piece_promoted(
                            move.promotion_type_get().value(),
                            convert_to_pos(move.end_pos_get()));
            }

            if (chessboard_.is_checkmate())
            {
                for (auto &l : listeners_)
                {
                    l->on_player_mat(opposite_color(move));
                    l->on_game_finished();
                    exit(0);
                }
            }
            else
            {
                if (chessboard_.is_check())
                    for (auto &l : listeners_)
                        l->on_player_check(opposite_color(move));
                if (chessboard_.is_pat() || chessboard_.is_draw())
                {
                    if (chessboard_.is_pat())
                        for (auto &l : listeners_)
                            l->on_player_pat(opposite_color(move));
                    for (auto &l : listeners_)
                    {
                        l->on_draw();
                        l->on_game_finished();
                        exit(0);
                    }
                }
            }
        }
    }

    void GameTracer::play_game()
    {
        for (auto &l : listeners_)
            l->on_game_started();

        this->play_loop_game();
    }

} // namespace board
