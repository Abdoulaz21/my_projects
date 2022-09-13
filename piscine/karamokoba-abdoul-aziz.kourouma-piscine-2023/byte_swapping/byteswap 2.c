#include <stdint.h>
#include <stdio.h>

uint16_t my_htons(uint16_t n)
{
    uint16_t h = (n & 0xff00) >> 8;
    uint16_t l = n & 0xff;
    uint16_t r = l << 8 | h;
    return r;
}

uint32_t my_htonl(uint32_t n)
{
    uint32_t r = (n >> 24) & 0xff;
    r |= (n >> 8) & 0xff00;
    r |= (n << 8) & 0xff0000;
    r |= (n << 24) & 0xff000000;
    return r;
}
