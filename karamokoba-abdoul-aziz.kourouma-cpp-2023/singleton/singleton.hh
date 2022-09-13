#pragma once

#include <iostream>
#include <string>

template <class T>
class Singleton
{
public:
    static T *instance()
    {
        static T instance;
        return &instance;
    }

protected:
    Singleton()
    {}
    ~Singleton()
    {}

public:
    Singleton(Singleton const &) = delete;
    Singleton &operator=(Singleton const &) = delete;
};

class Logger : public Singleton<Logger>
{
    friend class Singleton<Logger>;

private:
    Logger(){};
    ~Logger(){};

public:
    void open_log_file(const std::string &file);
    void write_to_log_file();
    void close_log_file();
};
