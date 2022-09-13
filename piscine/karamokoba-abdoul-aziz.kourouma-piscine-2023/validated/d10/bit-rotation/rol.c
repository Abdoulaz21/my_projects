#include <stdio.h>

unsigned char rol(unsigned char value, unsigned char roll)
{
    unsigned char mask = 8 * sizeof(value) - 1;
    roll &= mask;
    return value << roll | value >> (-roll & mask);
}
