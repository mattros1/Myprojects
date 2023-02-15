package PDFnlp;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.TypesafeMap;
import retrofit2.HttpException;
import edu.stanford.nlp.pipeline.*;
import PDFnlp.Pipeline;


import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import PDFnlp.sections.policyList;
import PDFnlp.sections.officeHours;
import PDFnlp.sections.miscellaneous;
import PDFnlp.sections.courseInfo;
import PDFnlp.sections.OpenAIRequest;
import PDFnlp.sections.URLlist;
import PDFnlp.sections.contactList;
import PDFnlp.sections.officeHours;
import java.lang.Iterable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.ArrayList;


public class Extract
{
    public static void main( String[] args ) throws Exception
    {


        String text="";
        try {
            text = getText(new File("/Users/mattrosica/Downloads/untitled folder/syllabus.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }


      
        contactList contacts =new contactList();
        courseInfo info=new courseInfo();
        miscellaneous misc=new miscellaneous();
        officeHours officeHours=new officeHours();
        policyList policy=new policyList();
        URLlist urls=new URLlist();
       

        contacts.extract(text);
     //    info.extract(text);
        // officeHours.extract(text);
        // policy.extract(text);
        // urls.extract(text);



    //     StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeLine();
    //     CoreDocument coreDocument=new CoreDocument(text);
    //     stanfordCoreNLP.annotate(coreDocument);


    //     List<CoreSentence> sentences =coreDocument.sentences();
    //     List<CoreLabel> coreLabelList=coreDocument.tokens();

    //     for(CoreSentence coreSentence: sentences){
    //         System.out.println(coreSentence.toString());
    //     }
    //     for(CoreLabel coreLabel: coreLabelList){
    //         String lemma=coreLabel.lemma();
    //         String ner =coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
    //        System.out.println(coreLabel.originalText() + " = " + ner);
    //     }


    }
    static String getText(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);
        return new PDFTextStripper().getText(doc);
    }

}






