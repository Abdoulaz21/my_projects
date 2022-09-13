#include <chessboard.hh>
#include <gtest/gtest.h>

/*****************
 *      KING      *
 *****************/
TEST(do_move, white_king)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(4);

    auto type = board::PieceType::KING;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.white_king_get()[4], false);
    EXPECT_EQ(board.white_king_get()[27], true);
}

TEST(do_move, black_king)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(60);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.black_king_get()[60], false);
    EXPECT_EQ(board.black_king_get()[27], true);
}

/* ------------------------------------------------------------------------- */

/*****************
 *     QUEEN      *
 *****************/
TEST(do_move, white_queen)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(3);

    auto type = board::PieceType::QUEEN;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.white_queen_get()[3], false);
    EXPECT_EQ(board.white_queen_get()[27], true);
}

TEST(do_move, black_queen)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(59);

    auto type = board::PieceType::QUEEN;
    auto color = board::Color::BLACK;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.black_queen_get()[59], false);
    EXPECT_EQ(board.black_queen_get()[27], true);
}

/* ------------------------------------------------------------------------- */

/*****************
 *     BISHOPS    *
 *****************/
TEST(do_move, white_bishops)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(2);

    auto type = board::PieceType::BISHOP;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.white_bishops_get()[2], false);
    EXPECT_EQ(board.white_bishops_get()[27], true);
}

TEST(do_move, black_bishops)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(58);

    auto type = board::PieceType::BISHOP;
    auto color = board::Color::BLACK;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.black_bishops_get()[58], false);
    EXPECT_EQ(board.black_bishops_get()[27], true);
}

/* ------------------------------------------------------------------------- */

/*****************
 *     ROOKS      *
 *****************/
TEST(do_move, white_rooks)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(0);

    auto type = board::PieceType::ROOK;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.white_rooks_get()[0], false);
    EXPECT_EQ(board.white_rooks_get()[27], true);
}

TEST(do_move, black_rooks)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(56);

    auto type = board::PieceType::ROOK;
    auto color = board::Color::BLACK;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.black_rooks_get()[56], false);
    EXPECT_EQ(board.black_rooks_get()[27], true);
}

/* ------------------------------------------------------------------------- */

/*****************
 *    KNIGHTS     *
 *****************/
TEST(do_move, white_knights)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(1);

    auto type = board::PieceType::KNIGHT;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.white_knights_get()[1], false);
    EXPECT_EQ(board.white_knights_get()[27], true);
}

TEST(do_move, black_knights)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(57);

    auto type = board::PieceType::KNIGHT;
    auto color = board::Color::BLACK;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.black_knights_get()[57], false);
    EXPECT_EQ(board.black_knights_get()[27], true);
}

/* ------------------------------------------------------------------------- */

/*****************
 *     PAWNS      *
 *****************/
TEST(do_move, white_pawns)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(8);

    auto type = board::PieceType::PAWN;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.white_pawns_get()[8], false);
    EXPECT_EQ(board.white_pawns_get()[27], true);
}

TEST(do_move, black_pawns)
{
    board::ChessBoard board;
    std::bitset<64> end_pos;
    end_pos.set(27);
    std::bitset<64> start_pos;
    start_pos.set(48);

    auto type = board::PieceType::PAWN;
    auto color = board::Color::BLACK;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    EXPECT_EQ(board.black_pawns_get()[48], false);
    EXPECT_EQ(board.black_pawns_get()[27], true);
}
