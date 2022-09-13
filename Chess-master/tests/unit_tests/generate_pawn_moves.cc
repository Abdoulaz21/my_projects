#include <chessboard.hh>
#include <gtest/gtest.h>
#include <rule.hh>

using namespace board;

// X : Initial position of the pawn
// O : Possible moves

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . O . . . . .
 * . . O . . . . .
 * . . X . . . . .
 * . . . . . . . .
 */

TEST(generate_one_pawn_moves, white_basic_move_from_start_pos)
{
    ChessBoard board;
    std::bitset<64> start_pos;
    start_pos.set(10);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 2);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(18).set(26);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . O . . . . .
 * . . X . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_one_pawn_moves, white_basic_move_after_start_pos)
{
    ChessBoard board;
    std::bitset<64> start_pos;
    start_pos.set(18);

    std::bitset<64> end;
    end.set(18);

    std::bitset<64> start;
    start.set(10);

    auto type = PieceType::PAWN;
    auto color = Color::WHITE;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 1);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(26);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . X . . . . .
 * . . O . . . . .
 * . . O . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_one_pawn_moves, black_basic_move_from_start_pos)
{
    ChessBoard board;
    board.set_turn(1);
    std::bitset<64> start_pos;
    start_pos.set(50);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 2);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(42).set(34);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . X . . . . .
 * . . O . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_one_pawn_moves, black_basic_move_after_start_pos)
{
    ChessBoard board;
    board.set_turn(1);
    std::bitset<64> start_pos;
    start_pos.set(42);

    std::bitset<64> end;
    end.set(42);

    std::bitset<64> start;
    start.set(50);

    auto type = PieceType::PAWN;
    auto color = Color::BLACK;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 1);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(34);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

// W : Other white pawns
// B : Other black pawns

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . W . . . . .
 * . W X W W W W W
 * W W W W W W W W
 */

TEST(generate_one_pawn_moves, white_no_possible_moves)
{
    ChessBoard board;
    std::bitset<64> start_pos;
    start_pos.set(10);

    std::bitset<64> end;
    end.set(18);

    std::bitset<64> start;
    start.set(8);

    auto type = PieceType::PAWN;
    auto color = Color::WHITE;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B B B
 * . B X B B B B B
 * . . B . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_one_pawn_moves, black_no_possible_moves)
{
    ChessBoard board;
    board.set_turn(1);
    std::bitset<64> start_pos;
    start_pos.set(50);

    std::bitset<64> end;
    end.set(42);

    std::bitset<64> start;
    start.set(48);

    auto type = PieceType::PAWN;
    auto color = Color::BLACK;
    Move move = { start, end, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .  --->  . . O . . . . .
 * . B . . . . . .        . O O . . . . .
 * W W X W W W W W        W W X W W W W W
 * W W W W W W W W        W W W W W W W W
 */

TEST(generate_one_pawn_moves, white_capture_moves)
{
    ChessBoard board;
    std::bitset<64> start_pos;
    start_pos.set(10);

    std::bitset<64> black_end;
    black_end.set(48);

    std::bitset<64> black_start;
    black_start.set(17);

    auto type = PieceType::PAWN;
    auto color = Color::BLACK;
    Move move = { black_start, black_end, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 3);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(17).set(18).set(26);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B B B        B B B B B B B B
 * B B X B B B B B        B B X B B B B B
 * . . . W . . . .        . . O O . . . .
 * . . . . . . . .        . . O . . . . .
 * . . . . . . . .  --->  . . . . . . . .
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .        . . . . . . . .
 */

TEST(generate_one_pawn_moves, black_capture_moves)
{
    ChessBoard board;
    board.set_turn(1);
    std::bitset<64> start_pos;
    start_pos.set(50);

    std::bitset<64> white_end;
    white_end.set(43);

    std::bitset<64> white_start;
    white_start.set(8);

    auto type = PieceType::PAWN;
    auto color = Color::WHITE;
    Move move = { white_start, white_end, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 3);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(42).set(43).set(34);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * O O O O O O O O
 * O O O O O O O O
 * X X X X X X X X
 * . . . . . . . .
 */

TEST(generate_pawn_moves, all_white_moves)
{
    ChessBoard board;

    auto all_moves = rule::generate_pawn_moves(board);

    EXPECT_EQ(all_moves.size(), 16);

    std::bitset<64> all;
    for (size_t i = 0; i < all_moves.size(); i++)
        all = all | all_moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(16).set(17).set(18).set(19).set(20).set(21).set(22).set(23);
    ref.set(24).set(25).set(26).set(27).set(28).set(29).set(30).set(31);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B B B
 * B B . B B B B B
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . B . . . . .
 * W W X W W W W W
 * W W W W W W W W
 */

TEST(generate_one_pawn_moves, white_start_no_capture_and_no_move)
{
    ChessBoard board;
    std::bitset<64> start_pos;
    start_pos.set(10);

    std::bitset<64> black_end;
    black_end.set(18);

    std::bitset<64> black_start;
    black_start.set(50);

    auto type = PieceType::PAWN;
    auto color = Color::BLACK;
    Move move = { black_start, black_end, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_one_pawn_moves(board, start_pos);

    EXPECT_EQ(moves.size(), 0);
}
