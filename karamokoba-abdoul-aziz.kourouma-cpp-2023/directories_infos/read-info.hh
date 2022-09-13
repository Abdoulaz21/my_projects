#pragma once

#include <fstream>
#include <istream>
#include <string>

#include "directory-info.hh"

DirectoryInfo *read_info(std::ifstream &file_in);
