package com.example.tagoverflow;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

public class Controller {
	
	static String url = "http://192.168.53.188:8080/" ; //Server
	static String key = "HtY8bMld7du)7cX*VkZ5yg((" ;
	
	public static void getMyDiscussions(List<NameValuePair> data, Callback callback) {
		String endpoint = "/users/mydiscussions" ; 
		URLContext context = new URLContext(url+endpoint, "POST", callback) ; 
		context.setData(data);
		new AsyncHTTP(callback).execute(context) ;
	}
	
	public static void search(List<NameValuePair> data, Callback callback) {
		String endpoint = "discussions/search/" ;
		URLContext context = new URLContext(url+endpoint, "POST", callback);
		context.setData(data);
        new AsyncHTTP(callback).execute(context);
	}
	
	public static void postReply(JSONObject json, Callback callback) {
        String endpoint = "discussions/add/reply";
        URLContext context = new URLContext(url+endpoint, "POST", callback);
        context.setJSON(json);
        new AsyncHTTP(callback).execute(context);
    }
	
	public static void predictTags(List<NameValuePair> data, Callback callback) {
        String endpoint = "tags";
        URLContext context = new URLContext(url+endpoint, "POST", callback);
        context.setData(data);
        new AsyncHTTP(callback).execute(context);
    }
	
	public static void postDiscussion(JSONObject json, Callback callback) {
		String endpoint = "discussions/add" ; 
		URLContext context = new URLContext(url+endpoint, "POST", callback) ; 
		context.setJSON(json);
		new AsyncHTTP(callback).execute(context) ;
	}
	
	public static void getQuestions(Callback callback) {
          String endpoint = "/discussions"; //Route
          URLContext questionsContext = new URLContext(url+endpoint, "GET", callback);
          new AsyncHTTP(callback).execute(questionsContext);
    }
	
	public static void getUserInfo(Callback callback, String accessToken) {
		String endpoint = ""; 
		URLContext userContext = new URLContext("https://api.stackexchange.com/me?site=stackoverflow&access_token="+accessToken+"&key="+key, "GET", callback) ;
		new AsyncHTTP(callback).execute(userContext) ;
	}
	
	public static void getDiscussionDetails(Callback callback, String discussionID)
	{
		String endpoint = "discussions/" + discussionID ;
		URLContext questionsContext = new URLContext(url+endpoint, "GET", callback);
        new AsyncHTTP(callback).execute(questionsContext);
	}
	
	public static void getUserDP(Callback callback, String dpURL) {
		new LoadImage(callback).execute(dpURL) ;
	}

}
