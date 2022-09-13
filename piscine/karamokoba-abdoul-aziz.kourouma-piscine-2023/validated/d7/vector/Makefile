# Makefile

CPPFLAGS = -MMD
CC = gcc
CFLAGS = -Wall -Wextra -std=c99 -pedantic -fsanitize=address
LDFLAGS =
LDLIBS = -fsanitize=address

SRC = vector_part1.c vector_part2.c
OBJ = ${SRC:.c=.o}
DEP = ${SRC:.c=.d}

all: libvector.a

main: ${OBJ}

libvector.a: vector_part1.o vector_part2.o

%.a:
	$(AR) $(ARFLAGS) $@ $^

.PHONY: clean

clean:
	${RM} ${OBJ}        # remove object files
	${RM} ${DEP}        # remove dependency files
	${RM} main          # remove main program
	${RM} libvector.a   # remove library file

-include ${DEP}

# END
