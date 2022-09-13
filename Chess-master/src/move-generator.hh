#pragma once

#include <bitset>
#include <vector>

#include "chessboard.hh"

namespace board
{
    class MoveGenerator
    {
    public:
        MoveGenerator(MoveGenerator &other) = delete;
        MoveGenerator(MoveGenerator &&) = delete;
        MoveGenerator &operator=(const MoveGenerator &) = delete;
        MoveGenerator &operator=(MoveGenerator) = delete;

        static MoveGenerator &instance()
        {
            static MoveGenerator instance_;
            return instance_;
        }

        // constexpr void init_knights_moves_up();
        // constexpr void init_knights_moves_down();

        std::vector<std::bitset<64>> knights_moves_table_get();
        std::vector<std::bitset<64>> king_moves_table_get();
        std::vector<std::bitset<64>> pawns_moves_table_get();

    protected:
        MoveGenerator();

    private:
        std::vector<std::bitset<64>> knights_moves_table_;
        std::vector<std::bitset<64>> king_moves_table_;
        std::vector<std::bitset<64>> pawns_moves_table_;
    };

} // namespace board
#include "move-generator.hxx"
