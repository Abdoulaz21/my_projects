#Makefile

CPPFLAGS = -MMD
CC = gcc
CFLAGS = -Wall -Wextra -Werror -pedantic -std=c99
LDFLAGS =
LDLIBS = -fsanitize=address

SRC = threshold1.c threshold2.c threshold3.c annexe.c
OBJ = $(SRC:.c=.o)
DEP = $(SRC:.c=.d)

library: libdlist.a

libdlist.a: $(OBJ)

%.a:
	$(AR) $(ARFLAGS) $@ $^

clean:
	$(RM) $(OBJ) $(DEP) libdlist.a

.PHONY: library clean

-include ${DEP}

#EndMakefile
