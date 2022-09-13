#include <stdio.h>

struct complex
{
    float real;
    float img;
};

void print_complex(struct complex a)
{
    float r = a.real;
    float i = a.img;
    if (i >= 0.00)
        printf("complex(%f + %fi)\n", r, i);
    else
        printf("complex(%f - %fi)\n", r, -i);
}

struct complex neg_complex(struct complex a)
{
    struct complex b;
    b.real = -(a.real);
    b.img = -(a.img);
    return b;
}

struct complex add_complex(struct complex a, struct complex b)
{
    struct complex c;
    c.real = a.real + b.real;
    c.img = a.img + b.img;
    return c;
}

struct complex sub_complex(struct complex a, struct complex b)
{
    struct complex c;
    c.real = a.real - b.real;
    c.img = a.img - b.img;
    return c;
}

struct complex mul_complex(struct complex a, struct complex b)
{
    struct complex c;
    c.real = (a.real * b.real) - (a.img * b.img);
    c.img = (a.real * b.img) + (a.img * b.real);
    return c;
}

struct complex div_complex(struct complex a, struct complex b)
{
    struct complex c;
    float rnume = (a.real * b.real) + (a.img * b.img);
    float inume = (a.img * b.real) - (a.real * b.img);
    float deno = (b.real * b.real) + (b.img * b.img);
    c.real = rnume / deno;
    c.img = inume / deno;
    return c;
}
