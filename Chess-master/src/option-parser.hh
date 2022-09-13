#pragma once

#include <boost/program_options.hpp>
#include <game_tracer.hh>
#include <perft-parser.hh>
#include <pgn-parser.hh>

namespace parsing
{
    namespace option_parser
    {
        bool parse_options(int argc, char **argv);
    } // namespace option_parser
} // namespace parsing
