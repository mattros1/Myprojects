package com.mattros;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.Gson;
/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Transcript example= new Transcript();
        example.setUrl("https://github.com/johnmarty3/JavaAPITutorial/blob/main/Thirsty.mp4?raw=true");
        Gson gson =new Gson();
        String jsonRequest = gson.toJson(example);
        System.out.println(jsonRequest);
        HttpRequest postRequest= HttpRequest.newBuilder()
            .uri(new URI("https://api.assemblyai.com/v2/transcript"))
            .header("Authorization","3041e39da808402f882d4792372c2da2")
            .POST(BodyPublishers.ofString(jsonRequest))
            .build();
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpResponse<String> postResponse=httpClient.send(postRequest,BodyHandlers.ofString());
        System.out.println(postResponse.body());
        example=gson.fromJson(postResponse.body(), Transcript.class);
        System.out.println(example.getid());
        HttpRequest getRequest= HttpRequest.newBuilder()
            .uri(new URI("https://api.assemblyai.com/v2/transcript/"+example.getid()))
            .header("Authorization","3041e39da808402f882d4792372c2da2")
            .GET()
            .build();
        while(true){
            HttpResponse<String> getResponse=httpClient.send(getRequest,BodyHandlers.ofString());
            example=gson.fromJson(getResponse.body(), Transcript.class);
            System.out.println(example.getStatus());
            if("completed".equals(example.getStatus()) || "error".equals(example.getStatus())){
                break;
            }
            Thread.sleep(1000);
        }
        System.out.println("completed!");
        System.out.println(example.getText());
       
    }
}
