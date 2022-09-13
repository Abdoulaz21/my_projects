CXX=g++
CPPFLAGS=-I.
CXXFLAGS=-Wall -Wextra -Werror -pedantic -std=c++17
DEBUG_FLAGS=-g3 -O0 -fsanitize=address
LD_DEBUG_FLAGS=-fsanitize=address

SRC=misc/addrinfo/addrinfo.cc \
    misc/addrinfo/addrinfo-error.cc \

SRC_BASIC_CLIENT=${SRC} \
              basic-client.cc

OBJ_BASIC_CLIENT=${SRC_BASIC_CLIENT:.cc=.o}

BASIC_CLIENT=basic-client

all: ${BASIC_CLIENT}

${BASIC_CLIENT}: ${OBJ_BASIC_CLIENT}
	${CXX} ${LDFLAGS} -o $@ $^

debug: debug-client
debug-client: CXXFLAGS += ${DEBUG_FLAGS}
debug-client: LDFLAGS += ${LD_DEBUG_FLAGS}
debug-client: ${OBJ_BASIC_CLIENT}
	${CXX} ${LDFLAGS} -o $@ $^

doc: Doxyfile
	doxygen

.PHONY: all clean debug

clean:
	${RM} ${BASIC_CLIENT}
	${RM} ${OBJ_BASIC_CLIENT}
	${RM} debug_client
	${RM} -r doc
