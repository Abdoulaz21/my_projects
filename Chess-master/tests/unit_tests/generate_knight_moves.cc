#include <chessboard.hh>
#include <gtest/gtest.h>
#include <rule.hh>

using namespace board;

/* ------------------------------------------------------------------------- */

// X : Initial position of the Knight
// O : Possible moves
// W : Other white pieces
// B : Other black pieces

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * O . O . . O . O
 * W W W W W W W W
 * W X W W W W X W
 */

TEST(generate_knight_moves, white_start_pos)
{
    ChessBoard board;

    auto moves = rule::generate_knight_moves(board);

    EXPECT_EQ(moves.size(), 4);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(16).set(18).set(21).set(23);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B X B B B B X B
 * B B B B B B B B
 * O . O . . O . O
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_knight_moves, black_start_pos)
{
    ChessBoard board;
    board.set_turn(1);

    auto moves = rule::generate_knight_moves(board);

    EXPECT_EQ(moves.size(), 4);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(40).set(42).set(45).set(47);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . O . . . . . .
 * . . O . . . . .
 * X . . . . . . .
 * W W W W W W W W
 * W . W W W W W W
 */

TEST(generate_one_knight_moves, white_left_side)
{
    ChessBoard board;
    std::bitset<64> start_pos;
    start_pos.set(16);

    std::bitset<64> end;
    end.set(16);

    std::bitset<64> start;
    start.set(1);

    auto type = PieceType::KNIGHT;
    auto color = Color::WHITE;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_one_knight_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 3);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(33).set(26).set(1);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B . B
 * B B B B B B B B
 * . . . . . . . X
 * . . . . . O . .
 * . . . . . . O .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_one_knight_moves, black_right_side)
{
    ChessBoard board;
    board.set_turn(1);
    std::bitset<64> start_pos;
    start_pos.set(47);

    std::bitset<64> end;
    end.set(47);

    std::bitset<64> start;
    start.set(62);

    auto type = PieceType::KNIGHT;
    auto color = Color::BLACK;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_one_knight_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 3);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(37).set(30).set(62);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B B B        B B B B B B B B
 * B B B B B B B B        B B O B O B B B
 * . . . . . . . .        . O . . . O . .
 * . . . X . . . .        . . . X . . . .
 * . . . . . . . .        . O . . . O . .
 * . . . . . . . .        . . O . O . . .
 * W W W W W W W W        W W W W W W W W
 * W . W W W W W W        W . W W W W W W
 */

TEST(generate_one_knight_moves, white_middle)
{
    ChessBoard board;
    std::bitset<64> start_pos;
    start_pos.set(35);

    std::bitset<64> end;
    end.set(35);

    std::bitset<64> start;
    start.set(1);

    auto type = PieceType::KNIGHT;
    auto color = Color::WHITE;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_one_knight_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 8);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(18).set(20).set(25).set(29).set(41).set(45).set(50).set(52);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B B B        B B B B B B . B
 * B B B B B B B B        B B B B B B B B
 * . . . . . . . .        . . O . O . . .
 * . . . . . . . .        . O . . . O . .
 * . . . X . . . .        . . . X . . . .
 * . . . . . . . .        . O . . . O . .
 * W W W W W W W W        W W O W O W W W
 * W W W W W W W W        W W W W W W W W
 */

TEST(generate_one_knight_moves, black_middle)
{
    ChessBoard board;
    board.set_turn(1);
    std::bitset<64> start_pos;
    start_pos.set(27);

    std::bitset<64> end;
    end.set(27);

    std::bitset<64> start;
    start.set(62);

    auto type = PieceType::KNIGHT;
    auto color = Color::BLACK;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_one_knight_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 8);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(10).set(12).set(17).set(21).set(33).set(37).set(42).set(44);

    EXPECT_EQ(all, ref);
}
