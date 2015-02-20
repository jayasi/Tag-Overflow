package com.example.tagoverflow;

public class Controller {
	
	static String url = "http://www.google.com" ; //Server,(have just put a random URL for now to make sure callback works.)
	
	public static void getQuestions(Callback callback) {
          String endpoint = ""; //Route
          URLContext questionsContext = new URLContext(url+endpoint, "GET", callback);
          new AsyncHTTP(callback).execute(questionsContext);
    }
	
	public static void getUserDP(Callback callback, String dpURL) {
		new LoadImage(callback).execute(dpURL) ;
	}

}
