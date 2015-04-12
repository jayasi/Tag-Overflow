package com.example.tagoverflow;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewPost extends ActionBarActivity {

	SharedPreferences pref ; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);
        pref = getSharedPreferences("AppPref", MODE_PRIVATE); 
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	
	public void createDiscussion(View view)
	{
		EditText title = (EditText)findViewById(R.id.title) ;
		String titleString = title.getText().toString() ;
		
		EditText body = (EditText)findViewById(R.id.body) ;
		String bodyString = body.getText().toString() ;
		JSONObject data = new JSONObject() ; 
		JSONObject params = new JSONObject() ; 
		try {
			data.put("title", titleString) ;
			data.put("body", bodyString) ; 
			JSONArray arr = new JSONArray() ; 
			arr.put("c#") ; //Putting hardcoded tags for now. 
			arr.put("java") ;
			data.put("tags", arr) ;
			params.put("data", data) ;
			params.put("access_token", pref.getString("AccessToken", "doesNotExist")) ; 
			
			Controller.postDiscussion(params, new Callback() {

				@Override
				public void onRequestComplete(Object output, int x) {
					// TODO Auto-generated method stub
					Log.d("posted", (String) output) ;
				}
				
			});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void predict(View view) throws JSONException
	{
		EditText title = (EditText)findViewById(R.id.title) ;
		String titleString = title.getText().toString() ;
		
		EditText body = (EditText)findViewById(R.id.body) ; 
		
		ArrayList<NameValuePair> arr = new ArrayList<NameValuePair>() ; 
		arr.add(new BasicNameValuePair("title", titleString)) ; 
		arr.add(new BasicNameValuePair("body", body.getText().toString())) ;
		
		Controller.predictTags(arr, new Callback() {
        	@Override
        	public void onRequestComplete(Object output, int x) { 
        		Log.d("predicted!", (String)output) ;
        		//JSONArray json = (JSONArray)output ; 
        	}
        });
	}
}
