CXX=g++
CPPFLAGS=-I.
CXXFLAGS=-Wall -Wextra -Werror -pedantic -std=c++17
DEBUG_FLAGS=-g3 -O0 -fsanitize=address
LD_DEBUG_FLAGS=-fsanitize=address

SRC=misc/addrinfo/addrinfo.cc \
    misc/addrinfo/addrinfo-error.cc \

SRC_EPOLL_SRV=${SRC} \
              epoll-server.cc

OBJ_EPOLL_SRV=${SRC_EPOLL_SRV:.cc=.o}

EPOLL_SRV=epoll-server

all: ${EPOLL_SRV}

${EPOLL_SRV}: ${OBJ_EPOLL_SRV}
	${CXX} ${LDFLAGS} -o $@ $^

debug: debug-epoll

debug-epoll: CXXFLAGS += ${DEBUG_FLAGS}
debug-epoll: LDFLAGS += ${LD_DEBUG_FLAGS}
debug-epoll: ${OBJ_EPOLL_SRV}
	${CXX} ${LDFLAGS} -o $@ $^

.PHONY: all clean debug

doc: Doxyfile
	doxygen

clean:
	${RM} ${EPOLL_SRV}
	${RM} ${OBJ_EPOLL_SRV}
	${RM} debug-epoll
	${RM} -r doc
