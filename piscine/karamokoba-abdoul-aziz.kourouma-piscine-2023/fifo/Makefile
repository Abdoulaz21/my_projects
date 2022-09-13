# Makefile

CPPFLAGS = -MMD
CC = gcc
CFLAGS = -Wall -Wextra -Werror -std=c99 -O2 -g -fsanitize=address
LDFLAGS =
LDLIBS = -fsanitize=address

SRC = fifo_init.c fifo_size.c fifo_push.c fifo_head.c fifo_pop.c fifo_clear.c fifo_destroy.c fifo_print.c
OBJ = $(SRC:.c=.o)
DEP = $(SRC:.c=.d)

all: libfifo.a

test: test.o $(OBJ)

library: libfifo.a

libfifo.a: $(OBJ)

%.a:
	$(AR) $(ARFLAGS) $@ $^

.PHONY: all library clean

clean:
	$(RM) $(OBJ)        		# remove object files
	$(RM) $(DEP)        		# remove dependency files
	$(RM) libfifo.a     		# remove library file
	$(RM) test test.o test.d 	# remove test program

-include ${DEP}

# END
