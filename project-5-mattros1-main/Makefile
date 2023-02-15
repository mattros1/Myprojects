CC = gcc
CCFLAGS = -lm -Wall -Wextra -Werror -pedantic -std=gnu99 -pedantic -Wmissing-prototypes -Wstrict-prototypes -Wold-style-definition -g

all:
	$(CC) $(CCFLAGS) search.c hashmap.c -o search

clean:
	rm search
	rm -f search_scores.txt