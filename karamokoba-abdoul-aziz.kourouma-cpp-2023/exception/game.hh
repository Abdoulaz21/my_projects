#pragma once

#include <string>

#include "invalid_argument.hh"
#include "player.hh"

class Game
{
public:
    void play(Player &p1, Player &p2)
    {
        if (p1.get_name() == p2.get_name())
            throw InvalidArgumentException("Stop playing by yourself!");
    }
};
