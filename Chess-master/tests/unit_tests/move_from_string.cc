#include <gtest/gtest.h>
#include <move.hh>

TEST(move_from_string, basic_move)
{
    board::Move m("e2e4");

    std::bitset<64> ref_start;
    ref_start.set(12);

    std::bitset<64> ref_end;
    ref_end.set(28);

    EXPECT_FALSE(m.is_promotion_get());
    EXPECT_EQ(m.start_pos_get(), ref_start);
    EXPECT_EQ(m.end_pos_get(), ref_end);
}

TEST(move_from_string, queen_promotion)
{
    board::Move m("d7d8q");

    std::bitset<64> ref_start;
    ref_start.set(51);

    std::bitset<64> ref_end;
    ref_end.set(59);

    EXPECT_TRUE(m.is_promotion_get());
    EXPECT_EQ(m.promotion_type_get().value(), board::PieceType::QUEEN);
    EXPECT_EQ(m.start_pos_get(), ref_start);
    EXPECT_EQ(m.end_pos_get(), ref_end);
}

TEST(move_from_string, rook_promotion)
{
    board::Move m("b7a8r");

    std::bitset<64> ref_start;
    ref_start.set(49);

    std::bitset<64> ref_end;
    ref_end.set(56);

    EXPECT_TRUE(m.is_promotion_get());
    EXPECT_EQ(m.promotion_type_get().value(), board::PieceType::ROOK);
    EXPECT_EQ(m.start_pos_get(), ref_start);
    EXPECT_EQ(m.end_pos_get(), ref_end);
}

TEST(move_from_string, bishop_promotion)
{
    board::Move m("h2h1b");

    std::bitset<64> ref_start;
    ref_start.set(15);

    std::bitset<64> ref_end;
    ref_end.set(7);

    EXPECT_TRUE(m.is_promotion_get());
    EXPECT_EQ(m.promotion_type_get().value(), board::PieceType::BISHOP);
    EXPECT_EQ(m.start_pos_get(), ref_start);
    EXPECT_EQ(m.end_pos_get(), ref_end);
}
TEST(move_from_string, knight_promotion)
{
    board::Move m("c2d1n");

    std::bitset<64> ref_start;
    ref_start.set(10);

    std::bitset<64> ref_end;
    ref_end.set(3);

    EXPECT_TRUE(m.is_promotion_get());
    EXPECT_EQ(m.promotion_type_get().value(), board::PieceType::KNIGHT);
    EXPECT_EQ(m.start_pos_get(), ref_start);
    EXPECT_EQ(m.end_pos_get(), ref_end);
}
