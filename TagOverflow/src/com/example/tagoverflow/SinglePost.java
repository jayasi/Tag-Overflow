package com.example.tagoverflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SinglePost  extends ActionBarActivity{

	TextView discussionTitle ;
	TextView discussionBody ;
	Intent intent  ;
	ImageView userDP ;
	SharedPreferences pref ; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_discussion);
        discussionTitle = (TextView)findViewById(R.id.discussiontitle);
        discussionBody = new TextView(this.getApplicationContext()) ;
        pref = getSharedPreferences("AppPref", MODE_PRIVATE); 
        userDP = (ImageView)findViewById(R.id.userdp) ;
        intent = getIntent();
        Controller.getDiscussionDetails(new Callback() {
            @Override
            public void onRequestComplete(Object output, int x) {
            	try {
					updateDiscussions(output);
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        }, intent.getStringExtra("id")); 
       // }, "22557088");
    }
	
	void updateDiscussions(Object output) throws JSONException {
		JSONObject obj = new JSONObject((String)output) ;
		discussionTitle.setText(obj.getString("title") ); 
		discussionBody.setText(Html.fromHtml(obj.getString("body")));
		JSONArray arr = obj.getJSONArray("replies") ;
		
		Controller.getUserDP(new Callback() {
			@Override
			public void onRequestComplete(Object output, int i) {
				if(i==0)
				{
					Bitmap img = (Bitmap)output ;
					userDP.setImageBitmap(img);
				}
			}
        }, obj.getString("avatar"));
		
		ListView repliesList = (ListView) findViewById(R.id.replieslist);
		repliesList.addHeaderView(discussionBody);
        RepliesAdapter adapter = null;
        try {
            adapter = new RepliesAdapter(this, arr, getResources());
        } catch (Exception e) {
            e.printStackTrace();
        }
        repliesList.setAdapter(adapter);
	}
	
	public void submitReply(View view) throws JSONException {
		EditText reply = (EditText)findViewById(R.id.replyField) ;
		String replyString = reply.getText().toString() ;
		
		JSONObject data = new JSONObject() ; 
		data.put("body", replyString) ;
		JSONObject params = new JSONObject() ; 
		params.put("data", data) ; 
		params.put("access_token", pref.getString("AccessToken", "doesNotExist") ) ;
		params.put("id", intent.getStringExtra("id")) ;

        Controller.postReply(params, new Callback() {
        	@Override
        	public void onRequestComplete(Object output, int x) { 
        		Log.d("Reply posted!", (String)output) ;
        	}
        });
	}
	
	
	
	
}


