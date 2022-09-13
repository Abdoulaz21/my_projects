# Makefile
# 'make'        builds binary 'csv'
# 'make clean'  removes all generated files

CC = gcc
CFLAGS = -Wextra -Wall -Werror -std=c99 -pedantic

SRC = csv.c
OBJ = ${SRC:.c=.o}
BIN = csv

all: $(BIN)

$(BIN): $(OBJ)

clean:
	$(RM) $(OBJ) $(BIN)

# END
