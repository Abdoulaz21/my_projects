#include <chessboard.hh>
#include <gtest/gtest.h>

TEST(is_legal_move, white_king_himself_check_move)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(8);

    std::bitset<64> e;
    e.set(16);

    auto type = board::PieceType::PAWN;
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

    std::bitset<64> sss;
    sss.set(4);

    std::bitset<64> eee;
    eee.set(28);

    type = board::PieceType::KING;
    color = board::Color::WHITE;
    move = { sss, eee, type, color };

    EXPECT_FALSE(board.is_legal_move(move));
}

TEST(is_legal_move, white_pawn_first_turn)
{
    board::ChessBoard board;

    std::bitset<64> s;
    s.set(8);

    std::bitset<64> e;
    e.set(16);

    auto type = board::PieceType::PAWN;
    auto color = board::Color::WHITE;
    board::Move move = { s, e, type, color };

    EXPECT_TRUE(board.is_legal_move(move));
}
