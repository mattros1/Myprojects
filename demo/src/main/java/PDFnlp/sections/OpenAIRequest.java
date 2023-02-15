package PDFnlp.sections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class OpenAIRequest {
    
    String prompt;

    public OpenAIRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getResponse() throws Exception {
        URL url = new URL("https://api.openai.com/v1/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer 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");
        String data = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + prompt + "\", \"temperature\": 0, \"max_tokens\": 7}";
        connection.setDoOutput(true);
        connection.getOutputStream().write(data.getBytes());

        int responseCode = connection.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //parse json response
        JSONObject json = new JSONObject(response.toString());
        String text = json.getJSONArray("choices").getJSONObject(0).getString("text");
        int tokens = json.getJSONObject("usage").getInt("total_tokens");
        String id = json.getString("id");
        String model = json.getString("model");

        //parse into custom class
        class Response {
            String Text;
            int Tokens;
            String Id;
            String Model;
        }
        Response res = new Response();
        res.Text = text;
        res.Tokens = tokens;
        res.Id = id;
        res.Model = model;
        return res.Text;


    }

    public static void main(String[] args) {
        


    }
}


