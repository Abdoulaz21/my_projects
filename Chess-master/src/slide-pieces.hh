#pragma once

#include <array>
#include <bitset>
#include <unordered_map>
#include <vector>

#include "piece-type.hh"

namespace board
{
    class SlidePieces
    {
    public:

        SlidePieces(SlidePieces &other) = delete;
        SlidePieces(SlidePieces &&) = delete;
        SlidePieces &operator=(const SlidePieces &) = delete;
        SlidePieces &operator=(SlidePieces &&) = delete;

        static SlidePieces &instance()
        {
            static SlidePieces instance_;
            return instance_;
        }

        void print_bitset(const std::bitset<64> &b);

        const std::vector<std::bitset<64>> init_rookmask();

        const std::vector<std::bitset<64>> init_bishopmask();

        const std::bitset<64> get_others_pieces(std::bitset<64> &board);

        const std::vector<std::bitset<64>> &rookmask_get()
        {
            return rookmask_;
        }

        const std::vector<std::bitset<64>> &bishopmask_get()
        {
            return bishopmask_;
        }

        const std::bitset<64> &map_blockers_get()
        {
            return map_blockers_;
        }

        void position_set(const int idx, std::bitset<64> &board);

        std::bitset<64> get_occupancy(int index, int bits_set,
                                      std::bitset<64> &mask);

        // int get_lsb(const std::bitset<64> &mask);

        const std::bitset<64>
        init_bishop_attack(int square, const std::bitset<64> &occupancy);

        const std::bitset<64>
        init_rook_attack(int square, const std::bitset<64> &occupancy);

        int right_shift_rooks(int index);

        int right_shift_bishops(int index);

        void init_hash_table_rooks();

        void init_hash_table_bishops();

        const std::bitset<64>
        blockers_magic_rooks(const std::bitset<64> &occupancy, int square);

        const std::bitset<64>
        blockers_magic_bishops(const std::bitset<64> &occupancy, int square);

        const std::bitset<64> bishop_attacks(std::bitset<64> &occupancy,
                                             int square);

        const std::bitset<64> rook_attacks(std::bitset<64> &occupancy,
                                           int square);

    protected:
        SlidePieces()
        {
            std::bitset<64> board;
            position_ = 0;

            rookmask_ = init_rookmask();
            init_hash_table_rooks();

            bishopmask_ = init_bishopmask();
            init_hash_table_bishops();

            map_blockers_ = get_others_pieces(board);
        }

    private:
        std::vector<std::bitset<64>> rookmask_;
        std::vector<std::bitset<64>> bishopmask_;
        std::bitset<64> map_blockers_;
        int position_;
        std::vector<std::vector<std::bitset<64>>> rooks_table_;
        std::vector<std::vector<std::bitset<64>>> bishops_table_;

        static constexpr std::array<uint64_t, 64> rooks_magics_numbers = {
            0x8a80104000800020ULL, 0x140002000100040ULL,  0x2801880a0017001ULL,
            0x100081001000420ULL,  0x200020010080420ULL,  0x3001c0002010008ULL,
            0x8480008002000100ULL, 0x2080088004402900ULL, 0x800098204000ULL,
            0x2024401000200040ULL, 0x100802000801000ULL,  0x120800800801000ULL,
            0x208808088000400ULL,  0x2802200800400ULL,    0x2200800100020080ULL,
            0x801000060821100ULL,  0x80044006422000ULL,   0x100808020004000ULL,
            0x12108a0010204200ULL, 0x140848010000802ULL,  0x481828014002800ULL,
            0x8094004002004100ULL, 0x4010040010010802ULL, 0x20008806104ULL,
            0x100400080208000ULL,  0x2040002120081000ULL, 0x21200680100081ULL,
            0x20100080080080ULL,   0x2000a00200410ULL,    0x20080800400ULL,
            0x80088400100102ULL,   0x80004600042881ULL,   0x4040008040800020ULL,
            0x440003000200801ULL,  0x4200011004500ULL,    0x188020010100100ULL,
            0x14800401802800ULL,   0x2080040080800200ULL, 0x124080204001001ULL,
            0x200046502000484ULL,  0x480400080088020ULL,  0x1000422010034000ULL,
            0x30200100110040ULL,   0x100021010009ULL,     0x2002080100110004ULL,
            0x202008004008002ULL,  0x20020004010100ULL,   0x2048440040820001ULL,
            0x101002200408200ULL,  0x40802000401080ULL,   0x4008142004410100ULL,
            0x2060820c0120200ULL,  0x1001004080100ULL,    0x20c020080040080ULL,
            0x2935610830022400ULL, 0x44440041009200ULL,   0x280001040802101ULL,
            0x2100190040002085ULL, 0x80c0084100102001ULL, 0x4024081001000421ULL,
            0x20030a0244872ULL,    0x12001008414402ULL,   0x2006104900a0804ULL,
            0x1004081002402ULL
        };

        static constexpr std::array<uint64_t, 64> bishops_magics_numbers = {
            0x40040844404084ULL,   0x2004208A004208ULL,   0x10190041080202ULL,
            0x108060845042010ULL,  0x581104180800210ULL,  0x2112080446200010ULL,
            0x1080820820060210ULL, 0x3C0808410220200ULL,  0x4050404440404ULL,
            0x21001420088ULL,      0x24D0080801082102ULL, 0x1020A0A020400ULL,
            0x40308200402ULL,      0x4011002100800ULL,    0x401484104104005ULL,
            0x801010402020200ULL,  0x400210C3880100ULL,   0x404022024108200ULL,
            0x810018200204102ULL,  0x4002801A02003ULL,    0x85040820080400ULL,
            0x810102C808880400ULL, 0xE900410884800ULL,    0x8002020480840102ULL,
            0x220200865090201ULL,  0x2010100A02021202ULL, 0x152048408022401ULL,
            0x20080002081110ULL,   0x4001001021004000ULL, 0x800040400A011002ULL,
            0xE4004081011002ULL,   0x1C004001012080ULL,   0x8004200962A00220ULL,
            0x8422100208500202ULL, 0x2000402200300C08ULL, 0x8646020080080080ULL,
            0x80020A0200100808ULL, 0x2010004880111000ULL, 0x623000A080011400ULL,
            0x42008C0340209202ULL, 0x209188240001000ULL,  0x400408A884001800ULL,
            0x110400A6080400ULL,   0x1840060A44020800ULL, 0x90080104000041ULL,
            0x201011000808101ULL,  0x1A2208080504F080ULL, 0x8012020600211212ULL,
            0x500861011240000ULL,  0x180806108200800ULL,  0x4000020E01040044ULL,
            0x300000261044000AULL, 0x802241102020002ULL,  0x20906061210001ULL,
            0x5A84841004010310ULL, 0x4010801011C04ULL,    0xA010109502200ULL,
            0x4A02012000ULL,       0x500201010098B028ULL, 0x8040002811040900ULL,
            0x28000010020204ULL,   0x6000020202D0240ULL,  0x8918844842082200ULL,
            0x4010011029020020ULL
        };

        static constexpr std::array<int, 64> rooks_shift_values = {
            12, 11, 11, 11, 11, 11, 11, 12, 11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11, 11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11, 11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11, 12, 11, 11, 11, 11, 11, 11, 12

        };

        static constexpr std::array<int, 64> bishops_shift_values = {
            6, 5, 5, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 7, 7, 7,
            5, 5, 5, 5, 7, 9, 9, 7, 5, 5, 5, 5, 7, 9, 9, 7, 5, 5, 5, 5, 7, 7,
            7, 7, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5, 5, 6
        };
    };
} // namespace board

#include "slide-pieces.hxx"
