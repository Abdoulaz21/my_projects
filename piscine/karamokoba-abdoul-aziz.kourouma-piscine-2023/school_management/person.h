#ifndef PERSON_H
#define PERSON_H

#include <stddef.h>

struct student
{
    const char *login;
    size_t promotion;
    double average;
};

struct teacher
{
    const char *name;
    const char *speciality;
    double salary;
};

struct staff
{
    const char *name;
    const char *role;
    double salary;
};

enum person_type
{
    PERSON_TYPE_STUDENT = 1,
    PERSON_TYPE_TEACHER = 1 << 1,
    PERSON_TYPE_STAFF = 1 << 2
};

union any_person
{
    struct student student;
    struct teacher teacher;
    struct staff staff;
};

struct person
{
    union any_person data;
    enum person_type type;
};

#endif
