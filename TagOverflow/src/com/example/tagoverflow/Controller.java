package com.example.tagoverflow;

public class Controller {
	
	static String url = "http://192.168.53.188:8080/" ; //Server
	static String key = "HtY8bMld7du)7cX*VkZ5yg((" ;
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
	
	public static void getUserDP(Callback callback, String dpURL) {
		new LoadImage(callback).execute(dpURL) ;
	}

}
