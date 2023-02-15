Syllabus Reader
This is my biggest project ive done. I recently started so it is by no means finished or operational. 
Currently, I am able to take a Syllabus pdf and convert it into text using pdfBox. I have then also 
imported Standoford NLP and set up a pipeline to use natural language processing on the syllabus. I have also created a class(openAiRequest)
that can be used to access OpenAi through web API. So, this project has the capability of querying openAI, converting pdf
into text, split the text by word/sentence, identify labels for each word using NER and finding the lemma of each word.  
It also utilizes inheritance and encapsulation in the "sections" folder which holds the sections of the syllabus that I will 
store.