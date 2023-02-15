package PDFnlp.sections;
import java.util.ArrayList;
import java.util.List;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.io.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import java.lang.Iterable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

//This class contains all contact info for a class/syllabus. It contains an array list of 'contacts' that hold contact information for one person. 
public class contactList extends section{


    //instance variables
    ArrayList<contact> contactsList = new ArrayList<>();

    //methods
    public void addContact(contact contact){
        contactsList.add(contact);
    }

    /* BIG BOY METHOD*/
    public void extract(String document){
     //   List<String> listOfStrings = ;
  
     
         //   String prompt = "Parse this syllabus into json. Give me only valid json with a single field called class_name, which holds the name of the class";
         String prompt ="give me a json only, that holds a variable class_name that contains the String 'math'";
          //  prompt += + "\n";
            prompt += " json: ";

            OpenAIRequest request = new OpenAIRequest(prompt);
           
            try {
            //    String text=request.getResponse();
                String answer=request.getResponse();
                System.out.println(answer);

           //     JSONObject json = new JSONObject(answer);
          //      String className = json.getString("class_name");
           //     System.out.println(className);

            } catch (Exception e) {
                System.out.println("Request failed.");
                System.out.println(e);
            }
    
    }



    //constructor
    public contactList(){
        
    }




    //Getters and Setters
    public ArrayList<contact> getcontactsList() {
        return this.contactsList;
      }
      
    public void setcontactsList(ArrayList<contact> ContactsList) {
        this.contactsList = ContactsList;
    }

}



//Contact class: This class contains the contact information for one person. 
class contact{
    //instance variables
    String name;
    String phone[];
    String email[];

    //constructor
    public contact(){

    }




    //Getters and Setters
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String[] getPhone(){
        return phone;
    }
    
    public void setPhone(String[] phone){
        this.phone = phone;
    }
    
    public String[] getEmail(){
        return email;
    }
    
    public void setEmail(String[] email){
        this.email = email;
    }
}
