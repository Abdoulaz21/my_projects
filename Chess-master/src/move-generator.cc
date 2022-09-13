#include "move-generator.hh"

namespace board
{
    static std::vector<std::bitset<64>> init_knights_moves()
    {
        std::vector<std::bitset<64>> knights_moves(64);
        for (int i = 0; i < 64; i++)
        {
            int column = i % 8;
            int line = i / 8;
            std::bitset<64> current_index;
            if (column >= 2 && line < 7)
                current_index.set(i + 6);
            if (column >= 2 && line > 0)
                current_index.set(i - 10);
            if (column <= 5 && line < 7)
                current_index.set(i + 10);
            if (column <= 5 && line > 0)
                current_index.set(i - 6);
            if (line >= 2 && column > 0)
                current_index.set(i - 17);
            if (line >= 2 && column < 7)
                current_index.set(i - 15);
            if (line <= 5 && column > 0)
                current_index.set(i + 15);
            if (line <= 5 && column < 7)
                current_index.set(i + 17);

            knights_moves[i] = current_index;
        }
        return knights_moves;
    }

    static std::vector<std::bitset<64>> init_king_moves()
    {
        std::vector<std::bitset<64>> king_moves(64);
        for (int i = 0; i < 64; i++)
        {
            int column = i % 8;
            int line = i / 8;
            std::bitset<64> current_index;
            if (column >= 1)
            {
                current_index.set(i - 1);
                if (line < 7)
                    current_index.set(i + 7);
                if (line > 0)
                    current_index.set(i - 9);
            }
            if (column <= 6)
            {
                current_index.set(i + 1);
                if (line < 7)
                    current_index.set(i + 9);
                if (line > 0)
                    current_index.set(i - 7);
            }
            if (line < 7)
                current_index.set(i + 8);
            if (line > 0)
                current_index.set(i - 8);

            king_moves[i] = current_index;
        }
        return king_moves;
    }

    MoveGenerator::MoveGenerator()
    {
        knights_moves_table_ = init_knights_moves();
        king_moves_table_ = init_king_moves();
        pawns_moves_table_ = { {} };
    }

} // namespace board
