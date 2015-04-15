package com.example.tagoverflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SinglePost  extends ActionBarActivity{

	TextView discussionTitle ;
	TextView discussionBody ;
	Intent intent  ;
	ImageView userDP ;
	SharedPreferences pref ; 
	ProgressBar progress ; 
	TagListView tagListView ; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_discussion);
        tagListView = (TagListView) findViewById(R.id.discussiontagview);
        discussionTitle = (TextView)findViewById(R.id.discussiontitle);
        discussionBody = new TextView(this.getApplicationContext()) ;
        pref = getSharedPreferences("AppPref", MODE_PRIVATE); 
        userDP = (ImageView)findViewById(R.id.userdp) ;
        intent = getIntent();
        progress = (ProgressBar)findViewById(R.id.progressbar) ;
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
		
		JSONArray tags = obj.getJSONArray("tags") ;
		discussionTitle.setText(Html.fromHtml(obj.getString("title")));
		
		LinearLayout linear = new LinearLayout(this.getApplicationContext()) ;
		LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT) ;
		
		for(int i = 0 ; i < tags.length() ; i++)
		{
			tagListView.addTag(tags.getString(i));
			TextView text = new TextView(this.getApplicationContext()) ;
			//Used a hack right now to show the tags separately. Will figure out a way to make these separate later.
			text.setText("   " + tags.getString(i)) ; 
			text.setTextColor(Color.BLACK);
			text.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// This can be used to show the stats or whatsoever is requried when a tag is clicked! 
					
				}
				
			});
			
			//linear.addView(text);
			
		}
		
		
		/*RelativeLayout relative = (RelativeLayout)findViewById(R.id.titleView) ;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.BELOW, R.id.discussiontitle);
		params.addRule(RelativeLayout.RIGHT_OF, R.id.userdp);
		params.leftMargin = 110 ; 
		relative.addView(linear, params); */
		String body = "<body>" + obj.getString("body")  + "</body>";
		body = body.replace("<code>", "<font color=grey><tt>") ; 
		body = body.replace("</code>", "</font></tt>") ;
		discussionBody.setText(Html.fromHtml(body));
		discussionBody.setTypeface(Typeface.SANS_SERIF);
		discussionBody.setTextColor(Color.BLACK);
		JSONArray arr  ;
		
		try
		{
			arr = obj.getJSONArray("replies") ;
		}
		catch (Exception e )
		{
			arr = new JSONArray() ;
		}
		progress.setVisibility(View.GONE);
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


