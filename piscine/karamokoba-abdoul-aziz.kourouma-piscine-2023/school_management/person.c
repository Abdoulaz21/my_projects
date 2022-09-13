#include <stdio.h>
#include <stdlib.h>

#include "list.h"

static void display_info(struct person *people)
{
    if (people->type == PERSON_TYPE_STUDENT)
    {
        struct student stud = people->data.student;
        printf("type: student\nlogin: %s\npromotion: %zu\naverage: %.2f\n\n",
               stud.login, stud.promotion, stud.average);
    }
    else if (people->type == PERSON_TYPE_TEACHER)
    {
        struct teacher teach = people->data.teacher;
        printf("type: teacher\nname: %s\nspeciality: %s\nsalary: %.2f\n\n",
               teach.name, teach.speciality, teach.salary);
    }
    else
    {
        struct staff staff = people->data.staff;
        printf("type: staff\nname: %s\nrole: %s\nsalary: %.2f\n\n", staff.name,
               staff.role, staff.salary);
    }
}

int list_dump(const struct list *list)
{
    if (list == NULL)
        return 0;
    else if (list->head == NULL)
    {
        return 1;
    }

    struct list_node *sentinelle = list->head;
    struct person *people = sentinelle->person;
    display_info(people);
    sentinelle = sentinelle->next;
    while (sentinelle != list->head)
    {
        people = sentinelle->person;
        display_info(people);
        sentinelle = sentinelle->next;
    }
    return 1;
}

int list_dump_ordered(const struct list *list)
{
    if (list == NULL)
        return 0;
    else if (list->head == NULL)
        return 1;

    struct list_node *sentinelle = list->head;
    struct person *people = NULL;
    size_t len_list = list_size(list);

    for (size_t i = 0; i < 3; i++)
    {
        for (size_t j = 0; j < len_list; j++)
        {
            people = sentinelle->person;
            if (i == 0 && people->type == PERSON_TYPE_TEACHER)
                display_info(people);
            else if (i == 1 && people->type == PERSON_TYPE_STAFF)
                display_info(people);
            else if (i == 2 && people->type == PERSON_TYPE_STUDENT)
                display_info(people);
            sentinelle = sentinelle->next;
        }
        sentinelle = list->head;
    }

    return 1;
}

ssize_t list_count(const struct list *list, enum person_type type)
{
    if (list == NULL)
        return -1;
    else if (list->head == NULL)
        return 0;

    ssize_t number_of_type = 0;
    struct list_node *sentinelle = list->head->next;
    while (sentinelle != list->head)
    {
        struct person *people = sentinelle->person;
        if (people->type == type)
            number_of_type++;
        sentinelle = sentinelle->next;
    }
    struct person *people_last = sentinelle->person;
    if (people_last->type == type)
        number_of_type++;

    return number_of_type;
}

double list_revenue(const struct list *list)
{
    if (list == NULL)
        return -1;
    else if (list->head == NULL)
        return 0;

    double revenue = 0;
    struct list_node *sentinelle = list->head;
    struct person *people = NULL;
    while (sentinelle->next != list->head)
    {
        people = sentinelle->person;
        if (people->type == PERSON_TYPE_STUDENT)
            revenue += 10000;
        else if (people->type == PERSON_TYPE_TEACHER)
            revenue -= people->data.teacher.salary;
        else
            revenue -= people->data.staff.salary;
        sentinelle = sentinelle->next;
    }
    people = sentinelle->person;
    if (people->type == PERSON_TYPE_STUDENT)
        revenue += 10000;
    else if (people->type == PERSON_TYPE_TEACHER)
        revenue -= people->data.teacher.salary;
    else
        revenue -= people->data.staff.salary;

    return revenue;
}

double list_average(const struct list *list, size_t promotion)
{
    if (list == NULL)
        return -1;
    else if (list->head == NULL)
        return 0;

    double average = 0;
    double nbr_student = 0;
    struct list_node *sentinelle = list->head;
    struct person *people = sentinelle->person;
    if (people->type == PERSON_TYPE_STUDENT
        && people->data.student.promotion == promotion)
    {
        nbr_student++;
        average += people->data.student.average;
    }
    sentinelle = sentinelle->next;
    while (sentinelle != list->head)
    {
        people = sentinelle->person;
        if (people->type == PERSON_TYPE_STUDENT
            && people->data.student.promotion == promotion)
        {
            nbr_student++;
            average += people->data.student.average;
        }
        sentinelle = sentinelle->next;
    }

    return average / nbr_student;
}
