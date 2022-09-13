#include "person.hh"

#include <iostream>

std::string Person::name_get() const
{
    return name_;
}

void Person::name_set(const std::string &name)
{
    name_ = name;
}

unsigned int Person::age_get() const
{
    return age_;
}

void Person::age_set(unsigned int age)
{
    age_ = age;
}
