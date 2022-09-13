CXX=g++
CPPFLAGS=-I.
CXXFLAGS=-Wall -Wextra -Werror -pedantic -std=c++17
DEBUG_FLAGS=-g3 -O0 -fsanitize=address
LD_DEBUG_FLAGS=-fsanitize=address

SRC=misc/addrinfo/addrinfo.cc \
    misc/addrinfo/addrinfo-error.cc \

SRC_BASIC_SRV=${SRC} \
              basic-server.cc

OBJ_BASIC_SRV=${SRC_BASIC_SRV:.cc=.o}

BASIC_SRV=basic-server

all: ${BASIC_SRV}

${BASIC_SRV}: ${OBJ_BASIC_SRV}
	${CXX} ${LDFLAGS} -o $@ $^

debug: debug-server

debug-server: CXXFLAGS += ${DEBUG_FLAGS}
debug-server: LDFLAGS += ${LD_DEBUG_FLAGS}
debug-server: ${OBJ_BASIC_SRV}
	${CXX} ${LDFLAGS} -o $@ $^

doc: Doxyfile
	doxygen

.PHONY: all clean debug

clean:
	${RM} ${BASIC_SRV}
	${RM} ${OBJ_BASIC_SRV}
	${RM} debug_server
	${RM} -r doc
