package PDFnlp.sections;
import java.util.ArrayList;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.json.JSONObject;

//This class contains all information about policy for a class. It holds an array list of all policies.
public class policyList extends section{
    //instance variables
    ArrayList<policy> policies = new ArrayList<>();

    public void addPolicy(policy policy){
        policies.add(policy);
    }

    public void extract(String document){
        String prompt = "Parse this syllabus into json. Give me only valid json with a single field called class_name, which holds the name of the class";
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
    public policyList(){

    }


    //Getter and setters
    public ArrayList<policy> getpolicies() {
        return this.policies;
      }
      
    public void setpolicies(ArrayList<policy> Policies) {
        this.policies = Policies;
    }
}





//Policy Class: This class contains the information about a policy. Examples: Late work, attendance, etc..
class policy{
    //instance variables
    String pname;
    String ptext;

    //constructor
    public policy(){

    }







    //Getter and setters
    public String getpname() {
        return this.pname;
    }
      
    public void setpname(String name) {
      this.pname = name;
    }
    public String gettext() {
        return this.pname;
    }
      
    public void setptext(String pText) {
        this.ptext = pText;
    }
}
