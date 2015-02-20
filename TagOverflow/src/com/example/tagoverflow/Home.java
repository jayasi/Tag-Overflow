package com.example.tagoverflow;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


public class Home extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //updateQuestions("");
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
       Log.d("callback", "updatelist");
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
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.add_new) {
        	Intent intent = new Intent(Home.this, NewPost.class);
            intent.putExtra("id", "2313"); // Sending random id for now
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
