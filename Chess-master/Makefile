BUILDDIR = build

RM += -r

all: chessengine chessengine-static

chessengine:
	cmake -S . -B $(BUILDDIR) -DCMAKE_BUILD_TYPE=Release
	cmake --build $(BUILDDIR) --target chessengine

chessengine-static:
	cmake -S . -B $(BUILDDIR) -DCMAKE_BUILD_TYPE=Release
	cmake --build $(BUILDDIR) --target chessengine-static

debug:
	cmake -S . -B $(BUILDDIR) -DCMAKE_BUILD_TYPE=Debug
	cmake --build $(BUILDDIR) --target chessengine

check:
	cmake -S . -B $(BUILDDIR) -DCMAKE_BUILD_TYPE=Release
	cmake --build $(BUILDDIR) --target tests
	./build/tests

clean:
	cmake --build $(BUILDDIR) --target clean

distclean: clean
	$(RM) $(BUILDDIR)
	clear

.PHONY: doc check
