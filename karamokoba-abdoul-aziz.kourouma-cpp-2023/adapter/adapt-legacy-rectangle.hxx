#include <cmath>
#include <iostream>

#include "adapt-legacy-rectangle.hh"

AdaptLegacyRectangle::AdaptLegacyRectangle(LegacyRectangle &rect)
    : rect_(rect)
{}

AdaptLegacyRectangle::~AdaptLegacyRectangle()
{}

void AdaptLegacyRectangle::print() const
{
    std::cout << "x: " << rect_.x1_get() << " y: " << rect_.y1_get() << '\n'
              << "height: " << std::abs(rect_.y1_get() - rect_.y2_get()) << '\n'
              << "width: " << std::abs(rect_.x1_get() - rect_.x2_get()) << '\n';
}

unsigned AdaptLegacyRectangle::area() const
{
    return std::abs(rect_.x1_get() - rect_.x2_get())
        * std::abs(rect_.y1_get() - rect_.y2_get());
}
