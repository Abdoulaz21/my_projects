#include <chessboard.hh>
#include <gtest/gtest.h>

TEST(DISABLED_is_draw, black_king_stalemate)
{
    std::string fen = "k7/2Q5/8/8/2K5/8/8/8 b - - 0 1 1";
    board::ChessBoard board(fen);

    // board.print_board();

    EXPECT_TRUE(board.is_pat());
}

TEST(is_draw, black_king_no_stalemate)
{
    std::string fen = "7k/2Q5/8/8/2K5/8/8/8 b - - 0 1 1";
    board::ChessBoard board(fen);

    // board.print_board();

    EXPECT_FALSE(board.is_draw());
}

TEST(is_draw, 50_turns_no_capture_neither_pawns)
{
    std::string fen = "7k/2Q5/8/8/2K5/8/8/8 b - - 0 1 1";
    board::ChessBoard board(fen);

    std::bitset<64> mv_king_1_s;
    mv_king_1_s.set(63);

    std::bitset<64> mv_king_1_e;
    mv_king_1_e.set(62);

    std::bitset<64> mv_king_2_s;
    mv_king_2_s.set(62);

    std::bitset<64> mv_king_2_e;
    mv_king_2_e.set(63);

    std::bitset<64> mv_queen_1_s;
    mv_queen_1_s.set(50);

    std::bitset<64> mv_queen_1_e;
    mv_queen_1_e.set(58);

    std::bitset<64> mv_queen_2_s;
    mv_queen_2_s.set(58);

    std::bitset<64> mv_queen_2_e;
    mv_queen_2_e.set(50);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move_king_front = { mv_king_1_s, mv_king_1_e, type, color };
    board::Move move_king_back = { mv_king_2_s, mv_king_2_e, type, color };

    auto type2 = board::PieceType::QUEEN;
    auto color2 = board::Color::WHITE;
    board::Move move_queen_front = { mv_queen_1_s, mv_queen_1_e, type2,
                                     color2 };
    board::Move move_queen_back = { mv_queen_2_s, mv_queen_2_e, type2, color2 };

    for (int i = 0; i < 12; i++)
    {
        board.do_move(move_king_front);
        board.do_move(move_queen_front);
        board.do_move(move_king_back);
        board.do_move(move_queen_back);
    }

    EXPECT_FALSE(board.is_draw());

    board.do_move(move_king_front);
    board.do_move(move_queen_front);

    EXPECT_TRUE(board.is_draw());
}

TEST(is_draw, 50_turns_pawns_move_at_49_turns)
{
    std::string fen = "7k/2Q5/8/8/2K5/6P1/8/8 b - - 0 1 1";
    board::ChessBoard board(fen);

    std::bitset<64> mv_king_1_s;
    mv_king_1_s.set(63);

    std::bitset<64> mv_king_1_e;
    mv_king_1_e.set(62);

    std::bitset<64> mv_king_2_s;
    mv_king_2_s.set(62);

    std::bitset<64> mv_king_2_e;
    mv_king_2_e.set(63);

    std::bitset<64> mv_queen_1_s;
    mv_queen_1_s.set(50);

    std::bitset<64> mv_queen_1_e;
    mv_queen_1_e.set(58);

    std::bitset<64> mv_queen_2_s;
    mv_queen_2_s.set(58);

    std::bitset<64> mv_queen_2_e;
    mv_queen_2_e.set(50);

    std::bitset<64> mv_pawn_s;
    mv_pawn_s.set(22);

    std::bitset<64> mv_pawn_e;
    mv_pawn_e.set(30);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move_king_front = { mv_king_1_s, mv_king_1_e, type, color };
    board::Move move_king_back = { mv_king_2_s, mv_king_2_e, type, color };

    auto type2 = board::PieceType::QUEEN;
    auto color2 = board::Color::WHITE;
    board::Move move_queen_front = { mv_queen_1_s, mv_queen_1_e, type2,
                                     color2 };
    board::Move move_queen_back = { mv_queen_2_s, mv_queen_2_e, type2, color2 };

    auto type3 = board::PieceType::PAWN;
    auto color3 = board::Color::WHITE;
    board::Move move_white_pawn = { mv_pawn_s, mv_pawn_e, type3, color3 };

    for (int i = 0; i < 12; i++)
    {
        board.do_move(move_king_front);
        board.do_move(move_queen_front);
        board.do_move(move_king_back);
        board.do_move(move_queen_back);
    }

    EXPECT_FALSE(board.is_draw());

    board.do_move(move_king_front);
    board.do_move(move_white_pawn);

    EXPECT_FALSE(board.is_draw());
}

TEST(is_draw, 50_turns_pawns_move_capture_at_last_turn)
{
    std::string fen = "7k/2Q5/8/8/2Kn4/6P1/8/8 b - - 0 1 1";
    board::ChessBoard board(fen);

    std::bitset<64> mv_king_1_s;
    mv_king_1_s.set(63);

    std::bitset<64> mv_king_1_e;
    mv_king_1_e.set(62);

    std::bitset<64> mv_king_2_s;
    mv_king_2_s.set(62);

    std::bitset<64> mv_king_2_e;
    mv_king_2_e.set(63);

    std::bitset<64> mv_queen_1_s;
    mv_queen_1_s.set(50);

    std::bitset<64> mv_queen_1_e;
    mv_queen_1_e.set(58);

    std::bitset<64> mv_queen_2_s;
    mv_queen_2_s.set(58);

    std::bitset<64> mv_queen_2_e;
    mv_queen_2_e.set(50);

    std::bitset<64> mv_pawn_s;
    mv_pawn_s.set(22);

    std::bitset<64> mv_pawn_e;
    mv_pawn_e.set(30);

    std::bitset<64> mv_white_king_capture_s;
    mv_white_king_capture_s.set(26);

    std::bitset<64> mv_white_king_capture_e;
    mv_white_king_capture_e.set(27);

    auto type = board::PieceType::KING;
    auto color = board::Color::BLACK;
    board::Move move_king_front = { mv_king_1_s, mv_king_1_e, type, color };
    board::Move move_king_back = { mv_king_2_s, mv_king_2_e, type, color };

    auto type2 = board::PieceType::QUEEN;
    auto color2 = board::Color::WHITE;
    board::Move move_queen_front = { mv_queen_1_s, mv_queen_1_e, type2,
                                     color2 };
    board::Move move_queen_back = { mv_queen_2_s, mv_queen_2_e, type2, color2 };

    board::Move move_capture = { mv_white_king_capture_s,
                                 mv_white_king_capture_e, type, color2,
                                 board::PieceType::KNIGHT };
    for (int i = 0; i < 12; i++)
    {
        board.do_move(move_king_front);
        board.do_move(move_queen_front);
        board.do_move(move_king_back);
        board.do_move(move_queen_back);
    }

    EXPECT_FALSE(board.is_draw());

    board.do_move(move_king_front);
    board.do_move(move_capture);

    EXPECT_FALSE(board.is_draw());
}
