package PDFnlp.sections;
import java.util.ArrayList;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.json.JSONObject;

//URL class: contains all URLs picked up in the syllabus. It holds an array list of the URLS. We could find a URL library but for now they are strings.
public class URLlist extends section{
    //instance variables
    ArrayList<String> urls = new ArrayList<>();


    public void addurl(String url){
        urls.add(url);
    }
    public void extract(String document){

        String prompt = "Parse this syllabus into json. Give me only valid json with variables called; class_name (which holds the name of the class), ";
        prompt += document+ "\n";
        prompt += "\n json: ";
  
        OpenAIRequest request = new OpenAIRequest(prompt);
  
        try {
            String text=request.getResponse();
  
            JSONObject json = new JSONObject(text);
            String className = json.getString("class_name");
            System.out.println(className);
  
        } catch (Exception e) {
            System.out.println("Request failed.");
        }
      }

    //constructor
    public URLlist(){

    }



    //Getters and Setters
    public ArrayList<String> geturls() {
        return this.urls;
      }
      
    public void seturls(ArrayList<String> Urls) {
        this.urls = Urls;
    }
}
