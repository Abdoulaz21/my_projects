#include <slide-pieces.hh>

namespace board
{
    inline void SlidePieces::position_set(const int idx, std::bitset<64> &board)
    {
        position_ = idx;
        map_blockers_ = get_others_pieces(board);
    }
} // namespace board
