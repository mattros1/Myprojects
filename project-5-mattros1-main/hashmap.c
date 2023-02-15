#include<stdio.h>
#include "hashmap.h"
#include "search.h"
#include <string.h>
#include <stdlib.h>
#include <math.h>
struct hashmap* hm_create(int num_buckets){
     //create the hashmap
  struct hashmap * hm = calloc(sizeof(struct hashmap),1);
  hm->num_buckets=num_buckets;
  hm->num_elements=0;
  hm -> map = calloc(sizeof(struct llnode *), num_buckets);
  int bucket=0;
  while(bucket!=num_buckets){
    struct llnode * head=calloc(sizeof(struct llnode),1);
    head->next=NULL;
    head->document_id=NULL;
    head->word=NULL;
    head->num_occurrences=0;
    hm->map[bucket]=head;
    bucket++;
  }
 //TODO the rest of the setup!
  
  return hm;
}

int hm_get(struct hashmap* hm, char* word, char* document_id){
    int bucket=hash(hm,word);
    struct llnode * ll = hm->map[bucket];
    if(word==NULL){
      return -1;
    }
    while(ll!=NULL){
      if(ll->word!=NULL){
        if(strcmp(word,ll->word)==0 && strcmp(document_id,ll->document_id)==0){
     //     printf("new: %s checked: %s has : %d \n",document_id, ll->document_id,ll->num_occurrences);
          return ll->num_occurrences;
        }
      }
          ll=ll->next;
    }

    return -1;

}


void hash_table_insert(struct hashmap* hm, char* word, char* document_id){
   
    int exists=0;
    struct llnode *ll =hm->map[hash(hm,word)];
     if(word==NULL){
      return;
    }
    while(ll!=NULL){
      if(ll->word!=NULL) {
        if(strcmp(word,ll->word)==0 && strcmp(document_id,ll->document_id)==0){
          ll->num_occurrences++;
          exists++;
          return;
        }
      }
      ll=ll->next;
    }
    struct llnode *newNode=calloc(sizeof(struct llnode),1);
    char * newWord=calloc(strlen(word)+1,1);
    char * newdDocId=calloc(strlen(document_id)+1,1);
    strcpy(newWord,word);
    strcpy(newdDocId,document_id);
    newNode->word=newWord;
    newNode->document_id=newdDocId;
    newNode->num_occurrences=1;
    newNode->next = hm->map[hash(hm,word)];
    hm->map[hash(hm,word)]=newNode;
    hm->num_elements++;
    return;
}

void hm_remove(struct hashmap* hm, char* word, char* document_id) {
    int index = hash(hm, word);
    struct llnode* prev = NULL;
    struct llnode* curr = hm->map[index];
    while (curr != NULL) {
      if(curr->word!=NULL && curr->document_id !=NULL && word!=NULL){
        if (strcmp(curr->word, word) == 0 &&
            strcmp(curr->document_id, document_id) == 0) {
            if (prev == NULL) {
                hm->map[index] = curr->next;
            } else {
                prev->next = curr->next;
            }
            free(curr->word);
            free(curr->document_id);
            free(curr);
            hm->num_elements--;
            return;
        }
      }
        prev = curr;
        curr = curr->next;
    }
    return;
}

void hm_destroy(struct hashmap* hm){  
  
  int i;
//   hm_print(hm);
  for(i=0; i< hm->num_buckets;i++){
    struct llnode * ll=hm->map[i];
    struct llnode * next;
    while(ll!=NULL){
      next=ll->next;
  //    printf("word removed: %s document removed from: %s \n",ll->word,ll->document_id);
      free(ll->word);
      free(ll->document_id);
      free(ll);
      ll=next;
    }
  }
  free(hm->map);
  free(hm);
}

int hash(struct hashmap* hm, char* word){
  double total=0;
  char * c;
  if(word!=NULL){
    for(c=word;*c;c++){
      total+=*c;
    }
  }
  return (int)(((int)total)%hm->num_buckets);
}
void hm_print(struct hashmap* hm) {
	struct llnode* current;
	for (int i = 0; i < hm->num_buckets; i++) {
		current = hm->map[i];
		while (current != NULL) {
      if(current->word!=NULL)
			printf("%s\n", current->word);
			current = current->next;
		}
	}
}
