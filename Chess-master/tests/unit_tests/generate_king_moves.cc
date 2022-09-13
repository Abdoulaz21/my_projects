#include <chessboard.hh>
#include <gtest/gtest.h>
#include <rule.hh>

using namespace board;

/* ------------------------------------------------------------------------- */

// X : Initial position of the King
// O : Possible moves
// W : Other white pieces
// B : Other black pieces

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . . . . . . .
 * . . . . . . . .
 * W W W W W W W W
 * W W W W X W W W
 */

TEST(generate_king_moves, white_down)
{
    ChessBoard board;

    auto moves = rule::generate_king_moves(board);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B X B B B
 * B B B B B B B B
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_king_moves, black_down)
{
    ChessBoard board;
    board.set_turn(1);

    auto moves = rule::generate_king_moves(board);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . . . . . . .
 * . . . . . . . .
 * W W W W W W W W
 * X W W W W W W W
 */

TEST(generate_king_moves, white_left_corner)
{
    ChessBoard board;

    std::bitset<64> end_pos;
    end_pos.set(0);

    std::bitset<64> start_pos;
    start_pos.set(4);

    auto type = PieceType::KING;
    auto color = Color::WHITE;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_king_moves(board);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B B X
 * B B B B B B B B
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_king_moves, black_left_corner)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_pos;
    end_pos.set(63);

    std::bitset<64> start_pos;
    start_pos.set(60);

    auto type = PieceType::KING;
    auto color = Color::BLACK;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_king_moves(board);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . . . . . . .
 * . . . . . . . .
 * W W W W W W W W
 * W W W W W W W X
 */

TEST(generate_king_moves, white_right_corner)
{
    ChessBoard board;

    std::bitset<64> end_pos;
    end_pos.set(7);

    std::bitset<64> start_pos;
    start_pos.set(4);

    auto type = PieceType::KING;
    auto color = Color::WHITE;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_king_moves(board);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * X B B B B B B B
 * B B B B B B B B
 * . . . . . . . .
 * . . . . . . . .  NO POSSIBLE MOVES
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_king_moves, black_right_corner)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_pos;
    end_pos.set(57);

    std::bitset<64> start_pos;
    start_pos.set(60);

    auto type = PieceType::KING;
    auto color = Color::BLACK;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_king_moves(board);

    EXPECT_EQ(moves.size(), 0);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * . . . O O O . .
 * . . . O X O . .
 * . . . O O O . .
 * W W W W W W W W
 * W W W W . W W W
 */

TEST(generate_king_moves, white_middle)
{
    ChessBoard board;

    std::bitset<64> end_pos;
    end_pos.set(28);

    std::bitset<64> start_pos;
    start_pos.set(4);

    auto type = PieceType::KING;
    auto color = Color::WHITE;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(19).set(20).set(21).set(27).set(29).set(35).set(36).set(37);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B . B B B
 * B B B B B B B B
 * . . . . . . . .
 * . . . O O O . .
 * . . . O X O . .
 * . . . O O O . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_king_moves, black_middle)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_pos;
    end_pos.set(28);

    std::bitset<64> start_pos;
    start_pos.set(60);

    auto type = PieceType::KING;
    auto color = Color::BLACK;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(19).set(20).set(21).set(27).set(29).set(35).set(36).set(37);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . O O
 * . . . . . . O X
 * . . . . . . O O
 * . . . . . . . .
 * W W W W W W W W
 * W W W W . W W W
 */

TEST(generate_king_moves, white_middle_right)
{
    ChessBoard board;

    std::bitset<64> end_pos;
    end_pos.set(31);

    std::bitset<64> start_pos;
    start_pos.set(4);

    auto type = PieceType::KING;
    auto color = Color::WHITE;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(22).set(23).set(30).set(38).set(39);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B . B B B
 * B B B B B B B B
 * . . . . . . . .
 * . . . . . . O O
 * . . . . . . O X
 * . . . . . . O O
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_king_moves, black_middle_right)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_pos;
    end_pos.set(31);

    std::bitset<64> start_pos;
    start_pos.set(60);

    auto type = PieceType::KING;
    auto color = Color::BLACK;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(22).set(23).set(30).set(38).set(39);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * . . . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 * O O . . . . . .
 * X O . . . . . .
 * O O . . . . . .
 * W W W W W W W W
 * W W W W . W W W
 */

TEST(generate_king_moves, white_middle_left)
{
    ChessBoard board;

    std::bitset<64> end_pos;
    end_pos.set(24);

    std::bitset<64> start_pos;
    start_pos.set(4);

    auto type = PieceType::KING;
    auto color = Color::WHITE;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(16).set(17).set(25).set(32).set(33);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B . B B B
 * B B B B B B B B
 * . . . . . . . .
 * O O . . . . . .
 * X O . . . . . .
 * O O . . . . . .
 * . . . . . . . .
 * . . . . . . . .
 */

TEST(generate_king_moves, black_middle_left)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_pos;
    end_pos.set(24);

    std::bitset<64> start_pos;
    start_pos.set(60);

    auto type = PieceType::KING;
    auto color = Color::BLACK;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(16).set(17).set(25).set(32).set(33);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B B B B B        B B B B B B B B
 * B B B B B B B B        B B B O O O B B
 * . . . . X . . .        . . . O X O . .
 * . . . . . . . .  --->  . . . O O O . .
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .        . . . . . . . .
 * W W W W W W W W        W W W W W W W W
 * W W W W . W W W        W W W W . W W W
 */

TEST(generate_king_moves, white_capture)
{
    ChessBoard board;

    std::bitset<64> end_pos;
    end_pos.set(44);

    std::bitset<64> start_pos;
    start_pos.set(4);

    auto type = PieceType::KING;
    auto color = Color::WHITE;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(0);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(35).set(36).set(37).set(43).set(45).set(51).set(52).set(53);

    EXPECT_EQ(all, ref);
}

/* ------------------------------------------------------------------------- */

/*
 * B B B B . B B B        B B B B . B B B
 * B B B B B B B B        B B B B B B B B
 * . . . . . . . .        . . . . . . . .
 * . . . . . . . .  --->  . . . . . . . .
 * . . . . . . . .        . . . O O O . .
 * . . . . X . . .        . . . O X O . .
 * W W W W W W W W        W W W O O O W W
 * W W W W W W W W        W W W W W W W W
 */

TEST(generate_king_moves, black_capture)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_pos;
    end_pos.set(20);

    std::bitset<64> start_pos;
    start_pos.set(60);

    auto type = PieceType::KING;
    auto color = Color::BLACK;
    Move move = { start_pos, end_pos, type, color };

    board.do_move(move);
    board.set_turn(1);

    auto moves = rule::generate_king_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all = all | moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(11).set(12).set(13).set(19).set(21).set(27).set(28).set(29);

    EXPECT_EQ(all, ref);
}
