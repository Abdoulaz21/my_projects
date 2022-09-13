#include <chessboard.hh>
#include <gtest/gtest.h>
#include <move.hh>
#include <rule.hh>

using namespace board;

TEST(is_en_passant, no_en_passant)
{
    ChessBoard board;

    std::bitset<64> end_pos;
    end_pos.set(26);

    std::bitset<64> start_pos;
    start_pos.set(10);

    auto type = PieceType::PAWN;
    auto color = Color::WHITE;
    Move move = { start_pos, end_pos, type, color };

    bool b = board.is_en_passant(move, 26); // idx => end_pos

    EXPECT_FALSE(b);
    EXPECT_EQ(board.en_passant_get().first, std::nullopt);
    EXPECT_EQ(board.en_passant_get().second, std::nullopt);
}

TEST(is_en_passant, left_en_passant)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(33);
    std::bitset<64> start_one;
    start_one.set(49);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(25);
    std::bitset<64> start_two;
    start_two.set(33);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    bool b = board.is_en_passant(move, 26);

    EXPECT_TRUE(b);
    EXPECT_EQ(board.en_passant_get().first.value(), 25);
    EXPECT_EQ(board.en_passant_get().second, std::nullopt);
}

TEST(is_en_passant, right_en_passant)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(35);
    std::bitset<64> start_one;
    start_one.set(51);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(27);
    std::bitset<64> start_two;
    start_two.set(35);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    bool b = board.is_en_passant(move, 26);

    EXPECT_TRUE(b);
    EXPECT_EQ(board.en_passant_get().first, std::nullopt);
    EXPECT_EQ(board.en_passant_get().second.value(), 27);
}

TEST(is_en_passant, double_en_passant)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(33);
    std::bitset<64> start_one;
    start_one.set(49);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(25);
    std::bitset<64> start_two;
    start_two.set(33);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);
    board.set_turn(1);

    std::bitset<64> end_three;
    end_three.set(35);
    std::bitset<64> start_three;
    start_three.set(51);

    Move move_three = { start_three, end_three, type, black };

    board.do_move(move_three);
    board.set_turn(1);

    std::bitset<64> end_four;
    end_four.set(27);
    std::bitset<64> start_four;
    start_four.set(35);

    Move move_four = { start_four, end_four, type, black };

    board.do_move(move_four);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    bool b = board.is_en_passant(move, 26);

    EXPECT_TRUE(b);
    EXPECT_EQ(board.en_passant_get().first.value(), 25);
    EXPECT_EQ(board.en_passant_get().second.value(), 27);
}

TEST(is_en_passant, left_en_passant_move)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(33);
    std::bitset<64> start_one;
    start_one.set(49);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(25);
    std::bitset<64> start_two;
    start_two.set(33);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    board.do_move(move);

    auto moves = rule::generate_pawn_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all |= moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(40).set(42).set(43).set(44).set(45).set(46).set(47);
    ref.set(32).set(34).set(35).set(36).set(37).set(38).set(39);
    ref.set(17).set(18);

    EXPECT_EQ(all, ref);
}

TEST(is_en_passant, right_en_passant_move)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(35);
    std::bitset<64> start_one;
    start_one.set(51);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(27);
    std::bitset<64> start_two;
    start_two.set(35);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    board.do_move(move);

    auto moves = rule::generate_pawn_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all |= moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(40).set(41).set(42).set(44).set(45).set(46).set(47);
    ref.set(32).set(33).set(34).set(36).set(37).set(38).set(39);
    ref.set(18).set(19);

    EXPECT_EQ(all, ref);
}

TEST(is_en_passant, double_en_passant_move)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(33);
    std::bitset<64> start_one;
    start_one.set(49);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(25);
    std::bitset<64> start_two;
    start_two.set(33);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);
    board.set_turn(1);

    std::bitset<64> end_three;
    end_three.set(35);
    std::bitset<64> start_three;
    start_three.set(51);

    Move move_three = { start_three, end_three, type, black };

    board.do_move(move_three);
    board.set_turn(1);

    std::bitset<64> end_four;
    end_four.set(27);
    std::bitset<64> start_four;
    start_four.set(35);

    Move move_four = { start_four, end_four, type, black };

    board.do_move(move_four);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    board.do_move(move);

    auto moves = rule::generate_pawn_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all |= moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(40).set(42).set(44).set(45).set(46).set(47);
    ref.set(32).set(34).set(36).set(37).set(38).set(39);
    ref.set(17).set(18).set(19);

    EXPECT_EQ(all, ref);
}

TEST(is_en_passant, left_en_passant_missed)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(33);
    std::bitset<64> start_one;
    start_one.set(49);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(25);
    std::bitset<64> start_two;
    start_two.set(33);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    board.do_move(move);

    auto moves_before = rule::generate_pawn_moves(board);
    board.do_move(moves_before[moves_before.size() - 1]);
    board.set_turn(1);

    auto moves = rule::generate_pawn_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all |= moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(40).set(42).set(43).set(44).set(45).set(46);
    ref.set(32).set(34).set(35).set(36).set(37).set(38).set(39);
    ref.set(17);

    EXPECT_EQ(all, ref);
}

TEST(is_en_passant, capture_piece)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(33);
    std::bitset<64> start_one;
    start_one.set(49);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);
    board.set_turn(1);

    std::bitset<64> end_two;
    end_two.set(25);
    std::bitset<64> start_two;
    start_two.set(33);

    Move move_two = { start_two, end_two, type, black };

    board.do_move(move_two);

    std::bitset<64> end;
    end.set(26);
    std::bitset<64> start;
    start.set(10);

    Move move = { start, end, type, white };

    board.do_move(move);

    auto moves = rule::generate_pawn_moves(board);
    board.do_move(moves[0]);

    auto w = board.white_pawns_get();
    auto b = board.black_pawns_get();

    std::bitset<64> ref_w;
    ref_w.set(8).set(9).set(11).set(12).set(13).set(14).set(15);
    std::bitset<64> ref_b;
    ref_b.set(48).set(18).set(50).set(51).set(52).set(53).set(54).set(55);

    EXPECT_EQ(w, ref_w);
    EXPECT_EQ(b, ref_b);
}

TEST(is_en_passant, tricky_move)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(32);
    std::bitset<64> start_one;
    start_one.set(48);

    auto type = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, type, black };

    board.do_move(move_one);

    std::bitset<64> end;
    end.set(31);
    std::bitset<64> start;
    start.set(15);

    Move move = { start, end, type, white };

    board.do_move(move);

    auto moves = rule::generate_pawn_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all |= moves[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(41).set(42).set(43).set(44).set(45).set(46).set(47);
    ref.set(33).set(34).set(35).set(36).set(37).set(38).set(39);
    ref.set(24);

    EXPECT_EQ(all, ref);
}

TEST(is_en_passant, other_pieces)
{
    ChessBoard board;
    board.set_turn(1);

    std::bitset<64> end_one;
    end_one.set(30);
    std::bitset<64> start_one;
    start_one.set(62);

    auto knight = PieceType::KNIGHT;
    auto pawn = PieceType::PAWN;
    auto white = Color::WHITE;
    auto black = Color::BLACK;

    Move move_one = { start_one, end_one, knight, black };

    board.do_move(move_one);

    std::bitset<64> end;
    end.set(31);
    std::bitset<64> start;
    start.set(15);

    Move move = { start, end, pawn, white };

    board.do_move(move);
    auto test = board.en_passant_get();
    EXPECT_EQ(test.first, std::nullopt);
    EXPECT_EQ(test.second, std::nullopt);

    auto moves = rule::generate_pawn_moves(board);
    auto moves2 = rule::generate_knight_moves(board);

    std::bitset<64> all;
    for (size_t i = 0; i < moves.size(); i++)
        all |= moves[i].end_pos_get();

    std::bitset<64> all2;
    for (size_t i = 0; i < moves2.size(); i++)
        all2 |= moves2[i].end_pos_get();

    std::bitset<64> ref;
    ref.set(40).set(41).set(42).set(43).set(44).set(45).set(46).set(47);
    ref.set(32).set(33).set(34).set(35).set(36).set(37).set(38).set(39);

    std::bitset<64> ref2;
    ref2.set(13).set(15).set(20).set(36).set(45).set(47);
    ref2.set(40).set(42);

    EXPECT_EQ(all, ref);
    EXPECT_EQ(all2, ref2);
}
