package com.example.tagoverflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Home extends ActionBarActivity {
	TextView displayName ; 
	SharedPreferences pref ;
	ProgressBar bar ;
	
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pref = getSharedPreferences("AppPref", MODE_PRIVATE); 
        
        //SharedPreferences.Editor edit = pref.edit();
        //edit.putString("AccessToken", "3fzYofjVJLIM*ETdKljPXw))"); //Storing the accessToken in the shared preferences for easy access
        //edit.commit();
        this.setTitle("Home");
        
        final ActionBar actionBar = getSupportActionBar() ;
        if(actionBar == null )
        	Log.d("NULLL", "aDASDAS") ; 

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
           /* public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            } */

			@Override
			public void onTabReselected(
					android.support.v7.app.ActionBar.Tab arg0,
					android.support.v4.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabSelected(
					android.support.v7.app.ActionBar.Tab arg0,
					android.support.v4.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabUnselected(
					android.support.v7.app.ActionBar.Tab arg0,
					android.support.v4.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 2; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }
        
        
        /*Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
          String query = intent.getStringExtra(SearchManager.QUERY);
          Log.d("Search for", query) ;
          ArrayList<NameValuePair> data = new ArrayList<NameValuePair>() ;
          data.add(new BasicNameValuePair("title", query)) ; 
          Controller.search(data, new Callback() {

			@Override
			public void onRequestComplete(Object output, int x) {
				updateQuestions((String)output);
			}
        	  
          });
        }
        else
        {
        	Controller.getQuestions(new Callback() {
                @Override
                public void onRequestComplete(Object output, int x) {
                	updateQuestions((String)output);
                }
            });   
        }
        
        this.setTitle("Home"); */
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
        bar = (ProgressBar)findViewById(R.id.progress) ; 
        bar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_action_new) {
        	Intent intent = new Intent(Home.this, NewPost.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
