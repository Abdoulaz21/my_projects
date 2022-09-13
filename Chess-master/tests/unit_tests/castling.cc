#include <chessboard.hh>
#include <gtest/gtest.h>

TEST(DISABLED_castling, white_king_castling)
{
    std::string str = "";
    board::ChessBoard board(str);
    EXPECT_EQ(board.white_king_get()[4], true);
    EXPECT_EQ(board.white_king_get()[6], false);


    EXPECT_TRUE(board.is_king_castling());
    // board.do_king_castling();

    // std::bitset<64> king_expected;
    // king_expected.set(6);

    // std::bitset<64> white_rooks;
    // white_rooks.set(0).set(5);
    // EXPECT_EQ(board.white_king_get()[6],true);
    // EXPECT_EQ(board.white_king_get()[4], false);

    std::bitset<64> end_pos;
    end_pos.set(15);
    std::bitset<64> start_pos;
    start_pos.set(7);

    auto type = board::PieceType::ROOK;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_pos, type, color };

    board.do_move(move);

    board.set_turn(2);
    EXPECT_FALSE(board.king_castling_get());
    EXPECT_TRUE(board.queen_castling_get());

    EXPECT_FALSE(board.is_king_castling());
    EXPECT_TRUE(board.is_queen_castling());

    // EXPECT_FALSE(;
}

TEST(DISABLED_castling, white_queen_castling)
{
    std::string str = "";
    board::ChessBoard board(str);
    board.print_board();
    EXPECT_EQ(board.white_king_get()[4], true);
    EXPECT_EQ(board.white_king_get()[2], false);

    EXPECT_TRUE(board.is_queen_castling());

    std::bitset<64> end_expected;
    end_expected.set(6);

    std::bitset<64> start_pos = board.white_king_get();

    std::bitset<64> white_rooks;
    white_rooks.set(0).set(5);

    auto type = board::PieceType::KING;
    auto color = board::Color::WHITE;
    board::Move move = { start_pos, end_expected, type, color };

    board.do_move(move);

    EXPECT_EQ(board.white_rooks_get()[5], true);
    EXPECT_EQ(board.white_rooks_get()[7], false);
    EXPECT_EQ(board.white_king_get()[6], true);
    EXPECT_EQ(board.white_king_get()[4], false);

    board.print_board();
}

TEST(castling, capture)
{
    std::string fen = "r7/8/3k4/8/8/8/8/R3K3 b Q - 0 1 1";
    board::ChessBoard board(fen);
    EXPECT_TRUE(board.white_queen_castling_get());

    std::bitset<64> start_pos;
    start_pos.set(56);

    std::bitset<64> end_pos;
    end_pos.set(0);

    auto type = board::PieceType::ROOK;
    auto color = board::Color::BLACK;

    board::Move move = { start_pos, end_pos, type, color, type };
    board.do_move(move);
    board.print_board();

    EXPECT_FALSE(board.white_queen_castling_get());

}
