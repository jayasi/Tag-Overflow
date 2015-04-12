package com.example.tagoverflow;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

public class URLContext {
    private String url;
    private String type;
    private List<NameValuePair> data;
    private JSONObject json ; 

    Callback callback;

    public URLContext(String url, String type, Callback callback) {
        this.setURL(url);
        this.setType(type);
        this.setCallback(callback);
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NameValuePair> getData() {
        return data;
    }

    public void setData(List<NameValuePair> data) {
        this.data = data;
    }
    
   public void setJSON(JSONObject json)
   {
	   this.json = json ; 
   }
   
   public JSONObject getJSON()
   {
	   return json ; 
   }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}