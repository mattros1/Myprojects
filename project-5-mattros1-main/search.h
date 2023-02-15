#include <stdio.h>
#include "hashmap.h"
#include <string.h>
#include <stdlib.h>
#include <math.h>

double totals[3];
#ifndef SEARCH_H
#define SEARCH_H


struct hashmap* training(FILE * fp,struct hashmap* hm, char * document_id);
void stop_word(struct hashmap * hm);
double getWeight(struct hashmap * hm, char * word, char * docId);
void getRank(struct hashmap * hm, char * word);
int read_query(struct hashmap * hm);
double findIDF(struct hashmap * hm, char * word);
#endif 
