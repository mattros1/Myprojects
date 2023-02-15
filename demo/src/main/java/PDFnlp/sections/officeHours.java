package PDFnlp.sections;
import java.util.ArrayList;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.json.JSONObject;

//This class contains all infromation about office hours for a class/syllabus. It holds an array list of 'hosts' which are all the TAs and professors.
public class officeHours extends section{

    //instance variables
    ArrayList<host> hostList = new ArrayList<>();

    public void addhost(host host){
        hostList.add(host);
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
    public officeHours(){

    }


    //Getter and setters
    public ArrayList<host> gethostList() {
        return this.hostList;
    }
      
    public void sethostList(ArrayList<host> HostList) {
        this.hostList = HostList;
    }
}




//Host class: Each host is either a TA or Professor. This class holds information about their offic hours.
class host{
    //instance variables
    private String name;
    private String email;
    private String meetingTime;
    private String meetingLocation;

    //constructor
    public host(){

    }






    //Getter and setters
    public String getName() {
        return this.name;
    }
      
    public String getEmail() {
        return this.email;
    }
      
      public String getMeetingTime() {
        return this.meetingTime;
      }
      
      public String getMeetingLocation() {
        return this.meetingLocation;
      }
      
      public void setName(String name) {
        this.name = name;
      }
      
      public void setEmail(String email) {
        this.email = email;
      }
      
      public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
      }
      
      public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
      }
      
    }
    

  
  
  
  