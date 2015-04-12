package com.example.tagoverflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SinglePost  extends ActionBarActivity{

	TextView discussionTitle ;
	TextView discussionBody ;
	ImageView userDP ;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_discussion);
        discussionTitle = (TextView)findViewById(R.id.discussiontitle);
       // discussionBody = (TextView)findViewById(R.id.discussionbody) ;
        discussionBody = new TextView(this.getApplicationContext()) ;
        userDP = (ImageView)findViewById(R.id.userdp) ;
        Intent intent = getIntent();
        Controller.getDiscussionDetails(new Callback() {
            @Override
            public void onRequestComplete(Object output, int x) {
            	try {
					updateDiscussions(output);
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
       // }, intent.getStringExtra("id")); 
        }, "22557088");
    }
	
	void updateDiscussions(Object output) throws JSONException {
		JSONObject obj = new JSONObject((String)output) ;
		discussionTitle.setText(obj.getString("title") ); 
		//discussionBody.setText("wUHSOSHSOHSOA") ;
		discussionBody.setText(Html.fromHtml(obj.getString("body")));
		JSONArray arr = obj.getJSONArray("replies") ;
		System.out.println("Here!");
		
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
        //setListViewHeightBasedOnChildren(repliesList);
		
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) 
	{
	    RepliesAdapter listAdapter = (RepliesAdapter) listView.getAdapter();
	    if (listAdapter == null)
	        return;

	    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
	    int totalHeight=0;
	    View view = null;

	    for (int i = 0; i < listAdapter.getCount(); i++) 
	    {
	        view = listAdapter.getView(i, view, listView);

	        if (i == 0)
	            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,  
	                                      LayoutParams.MATCH_PARENT));

	        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	        totalHeight += view.getMeasuredHeight();

	    }

	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));
	    listView.setLayoutParams(params);
	    listView.requestLayout();

	}
	
}


