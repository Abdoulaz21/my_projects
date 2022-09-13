#include <chessboard.hh>
#include <gtest/gtest.h>

/* WHITE KING */
TEST(is_check, white_king_no_check_right_knight)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(4);

    std::bitset<64> e;
    e.set(36);

    auto type = board::PieceType::KING;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(62);

    std::bitset<64> ee;
    ee.set(45);

    type = board::PieceType::KNIGHT;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_FALSE(board.is_check());
}

TEST(is_check, white_king_no_check_front_pawn)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(4);

    std::bitset<64> e;
    e.set(28);

    auto type = board::PieceType::KING;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(52);

    std::bitset<64> ee;
    ee.set(36);

    type = board::PieceType::PAWN;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_FALSE(board.is_check());
}

TEST(is_check, white_king_check_left_pawn)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(4);

    std::bitset<64> e;
    e.set(28);

    auto type = board::PieceType::KING;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(51);

    std::bitset<64> ee;
    ee.set(35);

    type = board::PieceType::PAWN;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, white_king_check_right_pawn)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(4);

    std::bitset<64> e;
    e.set(28);

    auto type = board::PieceType::KING;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(53);

    std::bitset<64> ee;
    ee.set(37);

    type = board::PieceType::PAWN;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, white_king_check_knight)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(4);

    std::bitset<64> e;
    e.set(28);

    auto type = board::PieceType::KING;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(62);

    std::bitset<64> ee;
    ee.set(45);

    type = board::PieceType::KNIGHT;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, white_king_check_queen)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(12);

    std::bitset<64> e;
    e.set(39);

    auto type = board::PieceType::PAWN;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(28);

    std::bitset<64> ee;
    ee.set(59);

    type = board::PieceType::QUEEN;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, white_king_check_rook)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(12);

    std::bitset<64> e;
    e.set(39);

    auto type = board::PieceType::PAWN;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(28);

    std::bitset<64> ee;
    ee.set(56);

    type = board::PieceType::ROOK;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, white_king_check_bishop)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(11);

    std::bitset<64> e;
    e.set(39);

    auto type = board::PieceType::PAWN;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(32);

    std::bitset<64> ee;
    ee.set(58);

    type = board::PieceType::BISHOP;
    color = board::Color::BLACK;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}
