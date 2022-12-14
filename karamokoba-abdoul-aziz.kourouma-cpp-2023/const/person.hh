#pragma once

#include <string>

class Person
{
public:
    Person(const std::string &name, unsigned int age)
        : name_(name)
        , age_(age)
    {}

    std::string name_get() const;

    void name_set(const std::string &name);

    unsigned int age_get() const;

    void age_set(unsigned int age);

private:
    std::string name_;
    unsigned int age_;
};
