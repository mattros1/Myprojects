package PDFnlp.sections;
import java.util.ArrayList;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.json.JSONObject;
//This class has all the basic course information for a class/syllabus. 
public class courseInfo extends section{

    //instance variables
    String courseTitle;
    int semester;
    String startTime;
    String endTime;
    String location;
    String website;
    String dotw;




    public void extract(String document){
        
        String prompt = "Parse this syllabus into json. Give me only valid json with variables called; class_name (which holds the name of the class), semester (which contains the current semester as an integer(1 or 2), start_time(which contains the time at which class starts, it may be multiple times depending on the day), end_time(which contains the time at which class ends, it may be multiple times depending on the day), location(which contains the location of the class), website(which contains the url for the website for the class)and dotw (which contains the days of the week in which the class is held)) ";
        prompt +=  "\n";
        prompt += "\n json: ";

        OpenAIRequest request = new OpenAIRequest("hey");

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
    public courseInfo(){
            
    }

    






    // Getters and Setters
    public String getdotw(){
        return dotw;
    }

    public void setdotw(String Dotw){
        this.dotw = Dotw;
    }
    public String getCourseTitle(){
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle){
        this.courseTitle = courseTitle;
    }

    public int getSemester(){
        return semester;
    }

    public void setSemester(int semester){
        this.semester = semester;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getWebsite(){
        return website;
    }

    public void setWebsite(String website){
        this.website = website;
    }

}
