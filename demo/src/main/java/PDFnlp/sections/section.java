package PDFnlp.sections;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import PDFnlp.sections.policyList;
import PDFnlp.sections.officeHours;
import PDFnlp.sections.miscellaneous;
import PDFnlp.sections.courseInfo;
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
public class section {
    public section(){

    }
    public List<String> splitDocument(String document, int textLimit) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeLine();
        CoreDocument coreDocument=new CoreDocument(document);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreMap> sections = coreDocument.annotation().get(CoreAnnotations.SectionsAnnotation.class);
        ArrayList<String> chunks=new ArrayList<>();
        int chunk=0;
        for (int i=0;i <sections.size();i++ ) {
            String chunkText= chunks.get(chunk);
            CoreMap section=sections.get(i);
            String sectionText = section.get(CoreAnnotations.TextAnnotation.class);
            if ((sectionText.length() +chunkText.length())<= textLimit) {
                chunks.set(chunk,(sectionText+chunkText));
            } else {
                chunk++;
            }
        }
        return chunks;
    }
}
