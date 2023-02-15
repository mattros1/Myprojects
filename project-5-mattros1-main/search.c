#include<stdio.h>
#include "hashmap.h"
#include "search.h"
#include <string.h>
#include <stdlib.h>
#include <math.h>

double totals[3];
int main(void){
    int x=0;
        int bucket;
        printf("Please enter the number of buckets for the hashmap: ");
        scanf("%d", &bucket);
        fflush(stdin);
            if(bucket<0){
                fprintf(stdout,"You cannot have a negative number of buckets");
                exit(1);
            }
            struct hashmap* hm = hm_create(bucket);
            FILE *fptr = fopen("./p5docs/D1.txt", "r");
            FILE *fptr2 = fopen("./p5docs/D2.txt", "r");
            FILE *fptr3 = fopen("./p5docs/D3.txt", "r");
            hm=training(fptr,hm, "D1");
            hm=training(fptr2,hm, "D2");
            hm=training(fptr3,hm, "D3");
            stop_word(hm);
        while(x!=-1){
            x=read_query(hm);
            totals[0]=0;
            totals[1]=0;
            totals[2]=0;
        }   

    
    return 0;
}
struct hashmap* training(FILE * fp,struct hashmap* hm, char * document_id){
    char word[21];
    while (fscanf(fp,"%20s",word)==1){

        int exists= hm_get(hm,word,document_id); //use hm_get to check if the word exists in the hashmap already.
      //  printf("Word:%s EXISTS=%d IN : %s\n",word,exists,document_id);

    //if word is not in the hashmap, add it in with num_occurrences=1. 
        if(exists==-1){   //if the word doesn't exist, then add it in. 
            hash_table_insert(hm,word,document_id);  //put the word in the hashmap with num_occurences=1. 
    //        printf("THIS SHOULD =1 : %d\n", hm_get(hm,word,document_id));
        }else{ //if the word exists, then just increment the num_occurences by 1. 
            hash_table_insert(hm,word,document_id);  
     //       printf("THIS SHOULD BE MORE THAN 1: %d\n", hm_get(hm,word,document_id));
        }


  }  
    fclose(fp);   //close the file pointer. 
    return hm;   //return 0 after all the words in the file have been processed. 
}
double findIDF(struct hashmap * hm, char * word){
    double numDocs=3;
    int docFreq=0;
    double idf=0.0;
    if(word==NULL){
        return 0.0;
    }
    for(int i=0;i< hm->num_buckets;i++){
        struct llnode * ll = hm->map[i];
        if(ll != NULL && ll->word!=NULL){
   
                while(ll!=NULL){
                    if(ll->word!=NULL){
                        if(strcmp(ll->word,word)==0){
                            docFreq++;
                        }
                    }
                     ll=ll->next;
                }
        }
    }
  //  printf("WORD: %s docFreq: %d\n",word,docFreq);
    if(docFreq==0){
        idf=log(numDocs/(docFreq+1));
    }
    else{
        idf=log(numDocs/docFreq);
    }
  //  printf("Word: %s idf: %lf\n",word,idf);
    return idf;
}
void stop_word(struct hashmap * hm){
    for(int i=0;i< hm->num_buckets;i++){
        struct llnode * ll = hm->map[i];
        struct llnode * next; 
        while(ll!=NULL){
            if(findIDF(hm,ll->word)==0){
                next=ll->next;
                if(next!=NULL){
                    for(int i=0;i<3;i++){
                        if(next->word!=NULL){
                            if(strcmp(next->word,ll->word)==0)
                                next=next->next;
                        }
                    }
                }
                hm_remove(hm,ll->word,"D1");
                hm_remove(hm,ll->word,"D2");
                hm_remove(hm,ll->word,"D3");
                ll=next;
            }
            else {
                ll=ll->next;
            }

        }
    }
}
double getWeight(struct hashmap * hm, char * word, char * docId){
   //  printf("%s : %d",docId,hm_get(hm,word,docId));
    int occurences=0;
    occurences=hm_get(hm,word,docId);
    if(occurences==-1){
        occurences=0;
    }
 // printf("word: %s document : %s occurences: %d\n ",word,docId,occurences);
    return (occurences * findIDF(hm,word));
}
void getRank(struct hashmap * hm, char * word){
      // create integer array to store weight results
    for(int b=0; b<3;b++){
        if(b==0)
        totals[b] += getWeight(hm,word,"D1");
        if (b==1)
        totals[b] += getWeight(hm,word,"D2");
        if (b==2)
        totals[b] += getWeight(hm,word,"D3");
    }
}
int read_query(struct hashmap * hm){
    char word[21];  // create char array of size 21
    int i = 0; // index for word array
    char c='\0';  // single character input

    printf("Please enter a string: ");
    scanf("\n");
    while(i < 21){  // loop until array is full
        scanf("%c",&c); 
        if(c=='X' || c=='#'){
            hm_destroy(hm);
            return -1;
        }
        if(c != ' ' && c!= '\n'){  // if character is not a space, and not a new line store it in the word array
            word[i] = c;
            i++;
        } else {  // if character is a space, send word array to getRank and store result in totals array
            word[i]='\0';
            char * word2 = malloc(strlen(word)+1);
            strcpy(word2,word);
            getRank(hm,word2);
            free(word2);
            i=0;
            if (c=='\n'){
                break;
            }
        }
    }
    FILE *fptr;

    // open the file for writing
    fptr = fopen("search_scores.txt", "a");



    if(totals[0]>=totals[1] && totals[0]>=totals[2]){
        if(totals[1]>=totals[2]){
            if(totals[0]==0){
                 fprintf(fptr,"no results\n");
            }else{
             fprintf(fptr,"D1.txt : %f\n",totals[0]);
             if(totals[1]!=0)
                fprintf(fptr,"D2.txt : %f\n",totals[1]);
             if(totals[2]!=0)
                fprintf(fptr,"D3.txt : %f\n",totals[2]);
            }
        }
        else if(totals[2]>=totals[1]){
            if(totals[0]==0){
                 fprintf(fptr,"no results\n");
            }else{
             fprintf(fptr,"D1.txt : %f\n",totals[0]);
             if(totals[2]!=0)
                fprintf(fptr,"D3.txt : %f\n",totals[2]);
             if(totals[1]!=0)
                fprintf(fptr,"D2.txt : %f\n",totals[1]);
            }
        }
    }
    else if(totals[1]>=totals[0] && totals[1]>=totals[2]){
        if(totals[0]>=totals[2]){
            if(totals[1]==0){
                 fprintf(fptr,"no results\n");
            }else{
             fprintf(fptr,"D2.txt : %f\n",totals[1]);
             if(totals[0]!=0)
                fprintf(fptr,"D1.txt : %f\n",totals[0]);
             if(totals[2]!=0)
                fprintf(fptr,"D3.txt : %f\n",totals[2]);
            }
        }
        else if(totals[2]>=totals[0]){
             if(totals[1]==0){
                 fprintf(fptr,"no results\n");
            }else{
             fprintf(fptr,"D2.txt : %f\n",totals[1]);
             if(totals[2]!=0)
                fprintf(fptr,"D3.txt : %f\n",totals[2]);
             if(totals[0]!=0)
                fprintf(fptr,"D1.txt : %f\n",totals[0]);
            }
        }
    }
    else if(totals[2]>=totals[1] && totals[2]>=totals[0]){
        if(totals[1]>=totals[0]){
           if(totals[2]==0){
                 fprintf(fptr,"no results\n");
            }else{
             fprintf(fptr,"D3.txt : %f\n",totals[2]);
             if(totals[1]!=0)
                fprintf(fptr,"D2.txt : %f\n",totals[1]);
             if(totals[0]!=0)
                fprintf(fptr,"D1.txt : %f\n",totals[0]);
            }
        }
        else if(totals[0]>=totals[1]){
             if(totals[2]==0){
                 fprintf(fptr,"no results\n");
            }else{
             fprintf(fptr,"D3.txt : %f\n",totals[2]);
             if(totals[0]!=0)
                fprintf(fptr,"D1.txt : %f\n",totals[0]);
             if(totals[1]!=0)
                fprintf(fptr,"D3.txt : %f\n",totals[1]);
            }
        }
    }
    fclose(fptr);
    return 0;
} 
