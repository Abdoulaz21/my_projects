#include "vehicle.hh"

#include <iostream>

Vehicle::Vehicle(const std::string &model, int fuel)
    : model_(model)
    , engine_(fuel)
{}

bool Vehicle::start()
{
    return engine_.start();
}

void Vehicle::cruise(int consumed)
{
    engine_.use(consumed);
}

void Vehicle::stop()
{
    engine_.stop();
}

void Vehicle::fill(int fuel)
{
    engine_.fill(fuel);
}
