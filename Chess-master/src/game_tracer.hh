#pragma once

#include <dlfcn.h>
#include <memory>
#include <vector>

#include "chessboard.hh"
#include "listener.hh"
#include "pgn-move.hh"
#include "pgn-parser.hh"

namespace board
{
    using moves = std::vector<Move>;

    class GameTracer
    {
    public:
        GameTracer(std::string &pgn_filepath,
                   std::vector<std::string> &listener_paths);

        void play_game();
        void play_loop_game();

        moves pgn_to_move(std::vector<PgnMove> &pgn);

        void add_listener(listener::Listener *listener);

        void remove_listener();

        ~GameTracer();

    private:
        std::vector<listener::Listener *> listeners_;
        std::vector<void *> handle_vector_;
        ChessBoard chessboard_;
        moves moves_;
    };

} // namespace board
