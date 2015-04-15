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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPost extends ActionBarActivity {

	SharedPreferences pref ; 
	TagListView tagListView ;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);
        pref = getSharedPreferences("AppPref", MODE_PRIVATE); 
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tagListView = (TagListView) findViewById(R.id.tagview);
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
			for(int i = 0 ; i < tagListView.getSize() ; i++)
			{
				arr.put(tagListView.getTag(i)) ;
				Log.d("tags", tagListView.getTag(i) ) ;
			}
			data.put("tags", arr) ;
			params.put("data", data) ;
			params.put("access_token", pref.getString("AccessToken", "doesNotExist")) ; 
			
			Controller.postDiscussion(params, new Callback() {

				@Override
				public void onRequestComplete(Object output, int x) {
					// TODO Auto-generated method stub
					try{
						
						JSONObject json = new JSONObject((String)output) ; 
						if(json.getString("error") != null)
							Toast.makeText(getApplicationContext(), json.getString("error"), Toast.LENGTH_SHORT).show() ;
						else 
							Log.d("here", "Start new activity and go to my discussions") ;  // Start a new activity here. 
					}
					catch(Exception e)
					{
						Log.d("e", e.getLocalizedMessage()) ;
						
					}
					Log.d("posted here", (String) output) ;
					
				}
				
			});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void moreTags(View view)
	{
		String tags = ((EditText)findViewById(R.id.moretags)).getText().toString() ;
		String[] listTags = tags.split(",") ;
		for(int i = 0 ; i < listTags.length ; i++) 
		{
			tagListView.addTag(listTags[i]);
		}
		EditText t = (EditText)findViewById(R.id.moretags) ; 
		t.setText("");
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
        		JSONArray o ;
        		try{
        			o = new JSONArray((String)output) ;
        			for (int i = 0 ; i < o.length(); i++)
            			tagListView.addTag(o.getString(i));
        		}
        		catch(Exception e)
        		{
        			Log.d("exception", e.getMessage()) ;
        		}
        		EditText t = (EditText)findViewById(R.id.moretags) ; 
        		t.setVisibility(View.VISIBLE);
        		Button b = (Button)findViewById(R.id.button1) ; 
        		b.setVisibility(View.VISIBLE);
        		//Log.d("psasasd", o) ; 
        		/*String xx = "[";
        		String clean = o.split(xx)[0].split("]")[0];
        		Log.d("cleaned!", clean) ; 
        		String list[] = clean.split(",") ;
        		for (int i = 0 ; i < list.length ; i++)
        			tagListView.addTag(list[i]); */
        		
        	}
        });
		
	}
}
