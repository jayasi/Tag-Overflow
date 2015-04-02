package com.example.tagoverflow;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class Home extends ActionBarActivity {
	TextView displayName ; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        displayName = (TextView)findViewById(R.id.username) ;
        Controller.getUserInfo(new Callback() {
        	@Override
        	public void onRequestComplete(Object output, int x) { 
        		try {
        			JSONObject obj = new JSONObject((String)output) ;
        			displayName.setText("Hi, " + obj.getJSONArray("items").getJSONObject(0).getString("display_name") );
					//Log.d("displayname", obj.getJSONArray("items").getJSONObject(0).getString("display_name"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		Log.d("user data", (String)output) ;
        	}
        }, getIntent().getExtras().getString("accessToken")); 
        Controller.getQuestions(new Callback() {
            @Override
            public void onRequestComplete(Object output, int x) {
            	updateQuestions((String)output);
            }
        });   
        this.setTitle("Home");
    }

    public void updateQuestions(String output)
    {			            
    	ListView questionsList = (ListView) findViewById(R.id.listView1);
        QuestionsListAdapter adapter = null;
        try {
            adapter = new QuestionsListAdapter(this, output, getResources());
        } catch (Exception e) {
            e.printStackTrace();
        }
        questionsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.add_new) {
        	Intent intent = new Intent(Home.this, NewPost.class);
            intent.putExtra("id", "2313"); // Sending random (user)id for now
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
