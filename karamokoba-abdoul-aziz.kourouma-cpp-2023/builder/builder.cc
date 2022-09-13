#include "builder.hh"

#include <array>
#include <iostream>
#include <memory>
#include <string>

#include "car.hh"
#include "components.hh"

// Builder
Car Builder::get_car() const
{
    Car car = Car();

    car.wheels_ = { get_wheel(), get_wheel(), get_wheel(), get_wheel() };
    car.engine_ = get_engine();
    car.body_ = get_body();

    return car;
}

// Jeep
std::unique_ptr<Wheel> JeepBuilder::get_wheel() const
{
    return std::make_unique<Wheel>(22);
}

std::unique_ptr<Engine> JeepBuilder::get_engine() const
{
    return std::make_unique<Engine>(400);
}

std::unique_ptr<Body> JeepBuilder::get_body() const
{
    return std::make_unique<Body>("SUV");
}

// Nissan
std::unique_ptr<Wheel> NissanBuilder::get_wheel() const
{
    return std::make_unique<Wheel>(16);
}

std::unique_ptr<Engine> NissanBuilder::get_engine() const
{
    return std::make_unique<Engine>(85);
}

std::unique_ptr<Body> NissanBuilder::get_body() const
{
    return std::make_unique<Body>("hatchback");
}
