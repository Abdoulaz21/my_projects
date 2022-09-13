#Makefile

CPPFLAGS = -MMD
CC = gcc
CFLAGS = -Wall -Wextra -Werror -pedantic -std=c99
LDFLAGS =
LDLIBS = -fsanitize=address

SRC = create.c add.c pop.c del.c print.c
OBJ = $(SRC:.c=.o)
DEP = $(SRC:.c=.d)

all: libheap.a

libheap.a: $(OBJ)

%.a:
	$(AR) $(ARFLAGS) $@ $^

clean:
	$(RM) $(OBJ) $(DEP) libheap.a

.PHONY: all clean

-include ${DEP}

#EndMakefile
