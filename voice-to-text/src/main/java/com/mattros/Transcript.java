package com.mattros;
public class Transcript {
    private String audio_url;
    private String id;
    private String status;
    private String text;

    public void setUrl(String url){
        this.audio_url=url;
    }
    public String getUrl(){
       return this.audio_url;
    }

    public void setStatus(String Status){
        this.status=Status;
    }
    public String getStatus(){
        return this.status;
    }
      
    public void setid(String Id){
        this.id=Id;
    }
    public String getid(){
       return this.id;
    }


    public void setText(String Text){
        this.text=Text;
    }
    public String getText(){
       return this.text;
    }
}
