Voice to text Project
This was a personal project for the purpose of me learning how to use web based API.
It creates an obeject(example) of a "transcript" it then uses example.seturl("(url here)") to
link an audio file that will be transcribed. It then creates a post of the transcript to assemblyAI
using an Http request. It then creates a get of the transcript with the text and saves into a transcript  
object using gson. Finally, it prints the text of the transcript. I am unsure if you will be able to run 
this as it requires some dependencies. I have an example url in here now so all you have to do is run App.java