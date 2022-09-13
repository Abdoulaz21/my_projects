#include "move-generator.hh"

namespace board
{
    inline std::vector<std::bitset<64>> MoveGenerator::knights_moves_table_get()
    {
        return knights_moves_table_;
    }

    inline std::vector<std::bitset<64>> MoveGenerator::king_moves_table_get()
    {
        return king_moves_table_;
    }

    inline std::vector<std::bitset<64>> MoveGenerator::pawns_moves_table_get()
    {
        return pawns_moves_table_;
    }

} // namespace board
