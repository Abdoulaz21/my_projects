#include "slide-pieces.hh"

#include <iostream>

namespace board
{
    void SlidePieces::print_bitset(const std::bitset<64> &b)
    {
        for (int i = 7; i >= 0; i--)
        {
            for (int j = 0; j < 8; j++)
            {
                bool val = b[i * 8 + j];
                if (val == true)
                    std::cout << "X" << ' ';
                else
                    std::cout << '.' << ' ';
            }

            std::cout << '\n';
        }
    }

    const std::vector<std::bitset<64>> SlidePieces::init_rookmask()
    {
        std::vector<std::bitset<64>> rookmask;

        for (int square = 0; square < 64; square++)
        {
            std::bitset<64> res;
            int line = square / 8;
            int column = square % 8;

            for (int i = line, j = 1; j < 7; j++)
                res.set(i * 8 + j);

            for (int j = column, i = 1; i < 7; i++)
                res.set(i * 8 + j);

            res.reset(square);
            rookmask.push_back(res);
        }

        return rookmask;
    }

    const std::vector<std::bitset<64>> SlidePieces::init_bishopmask()
    {
        std::vector<std::bitset<64>> bishopmask;
        for (int square = 0; square < 64; square++)
        {
            std::bitset<64> res;
            int line = square / 8;
            int column = square % 8;

            // up right diagonal
            for (int i = line + 1, j = column + 1; i < 7 && j < 7; i++, j++)
            {
                res.set(i * 8 + j);
            }

            // up left diagonal
            for (int i = line + 1, j = column - 1; i < 7 && j > 0; i++, j--)
            {
                res.set(i * 8 + j);
            }

            // down right diagonal
            for (int i = line - 1, j = column + 1; i > 0 && j < 7; i--, j++)
            {
                res.set(i * 8 + j);
            }

            for (int i = line - 1, j = column - 1; i > 0 && j > 0; i--, j--)
            {
                res.set(i * 8 + j);
            }

            res.reset(square);
            bishopmask.push_back(res);
        }

        return bishopmask;
    }

    const std::bitset<64>
    SlidePieces::init_rook_attack(int square, const std::bitset<64> &occupancy)
    {
        std::bitset<64> res;
        int line = square / 8;
        int column = square % 8;

        for (int i = line, j = column + 1; j <= 7; j++)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        for (int j = column, i = line + 1; i <= 7; i++)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        for (int i = line, j = column - 1; j >= 0; j--)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        for (int j = column, i = line - 1; i >= 0; i--)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        return res;
    }

    const std::bitset<64>
    SlidePieces::init_bishop_attack(int square,
                                    const std::bitset<64> &occupancy)
    {
        std::bitset<64> res;
        int line = square / 8;
        int column = square % 8;

        // up right diagonal
        for (int i = line + 1, j = column + 1; i <= 7 && j <= 7; i++, j++)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        // up left diagonal
        for (int i = line + 1, j = column - 1; i <= 7 && j >= 0; i++, j--)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        // down right diagonal
        for (int i = line - 1, j = column + 1; i >= 0 && j <= 7; i--, j++)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        for (int i = line - 1, j = column - 1; i >= 0 && j >= 0; i--, j--)
        {
            res.set(i * 8 + j);
            if (occupancy[i * 8 + j] == 1)
                break;
        }

        return res;
    }

    const std::bitset<64> SlidePieces::get_others_pieces(std::bitset<64> &board)
    {
        board.reset(position_);
        return board;
    }

    int SlidePieces::right_shift_rooks(int index)
    {
        return 64 - rooks_shift_values[index];
    }

    int SlidePieces::right_shift_bishops(int index)
    {
        return 64 - bishops_shift_values[index];
    }

    static inline int get_lsb(const std::bitset<64> &mask)
    {
        return __builtin_ctzll(mask.to_ullong());
    }

    std::bitset<64> SlidePieces::get_occupancy(int index, int bits_set,
                                               std::bitset<64> &mask)
    {
        std::bitset<64> res;
        for (int i = 0; i < bits_set; i++)
        {
            int pos = get_lsb(mask);

            if (pos >= 0 && pos < 64)
                mask.reset(pos);
            std::bitset<64> all_one;

            if (index & (1 << i))
            {
                all_one.set(pos);
                res |= all_one;
            }
        }

        return res;
    }

    const std::bitset<64>
    SlidePieces::blockers_magic_rooks(const std::bitset<64> &occupancy,
                                      int square)
    {
        return occupancy.to_ullong() * rooks_magics_numbers[square];
    }

    const std::bitset<64>
    SlidePieces::blockers_magic_bishops(const std::bitset<64> &occupancy,
                                        int square)
    {
        return occupancy.to_ullong() * bishops_magics_numbers[square];
    }

    void SlidePieces::init_hash_table_rooks()
    {
        std::vector<std::vector<std::bitset<64>>> rooks_table;
        for (int square = 0; square < 64; square++)
        {
            std::vector<std::bitset<64>> rook_blockers(
                1 << rooks_shift_values[square]);
            int bits_set = rookmask_[square].count();
            for (int blocking_pieces = 0;
                 blocking_pieces < (1 << rooks_shift_values[square]);
                 blocking_pieces++)
            {
                std::bitset<64> current_mask = rookmask_[square];

                std::bitset<64> current_blockers =
                    get_occupancy(blocking_pieces, bits_set, current_mask);

                unsigned long long key = (current_blockers.to_ullong()
                                          * rooks_magics_numbers[square])
                    >> right_shift_rooks(square);
                rook_blockers[key] = init_rook_attack(square, current_blockers);
            }
            rooks_table.push_back(rook_blockers);
        }
        rooks_table_ = rooks_table;
    }

    void SlidePieces::init_hash_table_bishops()
    {
        std::vector<std::vector<std::bitset<64>>> bishops_table;
        for (int square = 0; square < 64; square++)
        {
            std::vector<std::bitset<64>> bishop_blockers(
                1 << bishops_shift_values[square]);
            int bits_set = bishopmask_[square].count();
            for (int blocking_pieces = 0;
                 blocking_pieces < (1 << bishops_shift_values[square]);
                 blocking_pieces++)
            {
                std::bitset<64> current_mask = bishopmask_[square];

                std::bitset<64> current_blockers =
                    get_occupancy(blocking_pieces, bits_set, current_mask);
                unsigned long long key = (current_blockers.to_ullong()
                                          * bishops_magics_numbers[square])
                    >> right_shift_bishops(square);
                bishop_blockers[key] =
                    init_bishop_attack(square, current_blockers);
            }

            bishops_table.push_back(bishop_blockers);
        }

        bishops_table_ = bishops_table;
    }

    const std::bitset<64> SlidePieces::rook_attacks(std::bitset<64> &occupancy,
                                                    int square)
    {
        occupancy &= rookmask_[square];
        unsigned long long key =
            occupancy.to_ullong() * rooks_magics_numbers[square];
        key >>= right_shift_rooks(square);
        return rooks_table_[square][key];
    }

    const std::bitset<64>
    SlidePieces::bishop_attacks(std::bitset<64> &occupancy, int square)
    {
        occupancy &= bishopmask_[square];
        unsigned long long key =
            occupancy.to_ullong() * bishops_magics_numbers[square];
        key >>= right_shift_bishops(square);

        return bishops_table_[square][key];
    }
} // namespace board
