CC ?= gcc
CFLAGS = -std=c99 -Wall -Wextra -Werror -pedantic

LIB = libschool.a
OBJ = list.o person.o
SRC = list.c person.c


libschool.a: lib

school.o:
	$(CC) $(CFLAGS) -c $(SRC)

lib: school.o
	 ar csr $(LIB) $(OBJ)

clean:
	rm -rf $(LIB) $(OBJ)
