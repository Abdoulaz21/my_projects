#include <chessboard.hh>
#include <gtest/gtest.h>

/* BLACK KING */
TEST(is_check, black_king_check_left_pawn)
{
    board::ChessBoard board;
    board.set_turn(1);

    std::bitset<64> s;
    s.set(60);

    std::bitset<64> e;
    e.set(36);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(11);

    std::bitset<64> ee;
    ee.set(27);

    type = board::PieceType::PAWN;
    color = board::Color::WHITE;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, black_king_check_right_pawn)
{
    board::ChessBoard board;
    board.set_turn(1);

    std::bitset<64> s;
    s.set(60);

    std::bitset<64> e;
    e.set(36);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(13);

    std::bitset<64> ee;
    ee.set(29);

    type = board::PieceType::PAWN;
    color = board::Color::WHITE;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, black_king_check_knight)
{
    board::ChessBoard board;
    board.set_turn(1);

    std::bitset<64> s;
    s.set(60);

    std::bitset<64> e;
    e.set(36);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(6);

    std::bitset<64> ee;
    ee.set(21);

    type = board::PieceType::KNIGHT;
    color = board::Color::WHITE;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, black_king_check_queen)
{
    board::ChessBoard board;
    board.set_turn(1);

    std::bitset<64> s;
    s.set(60);

    std::bitset<64> e;
    e.set(36);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(3);

    std::bitset<64> ee;
    ee.set(18);

    type = board::PieceType::QUEEN;
    color = board::Color::WHITE;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, black_king_check_rook)
{
    board::ChessBoard board;
    board.set_turn(1);

    std::bitset<64> s;
    s.set(60);

    std::bitset<64> e;
    e.set(36);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(0);

    std::bitset<64> ee;
    ee.set(32);

    type = board::PieceType::ROOK;
    color = board::Color::WHITE;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}

TEST(is_check, black_king_check_bishop)
{
    board::ChessBoard board;
    board.set_turn(1);

    std::bitset<64> s;
    s.set(60);

    std::bitset<64> e;
    e.set(36);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move = { s, e, type, color };

    board.do_move(move);

    std::bitset<64> ss;
    ss.set(5);

    std::bitset<64> ee;
    ee.set(22);

    type = board::PieceType::BISHOP;
    color = board::Color::WHITE;
    move = { ss, ee, type, color };

    board.do_move(move);

    EXPECT_TRUE(board.is_check());
}
