package com.example.tagoverflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject ;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionsListAdapter extends BaseAdapter {

	JSONArray questions ;
	LayoutInflater inflater ; 
	Activity parentActivity ; 
	
	public QuestionsListAdapter(Activity myActivity, String jsonOutput, Resources resources) throws JSONException {
		questions = new JSONArray(jsonOutput);
		parentActivity = myActivity ; 
        inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	
	@Override
	public int getCount() {
		return questions.length();
	}

	@Override
	public Object getItem(int position) {
		 try {
	            return questions.get(position);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return null;
	}

	@Override
	public long getItemId(int position) {
		try {
			JSONObject obj = (JSONObject) questions.get(position) ;
            return Integer.parseInt(obj.getString("ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		 final QuestionHolder holder;
	        if(view == null) {
	            view = inflater.inflate(R.layout.questions, null);
	            holder = new QuestionHolder();
	            holder.question = (TextView) view.findViewById(R.id.question);
	            holder.tags = (TextView) view.findViewById(R.id.tags);
	            holder.dp = (ImageView) view.findViewById(R.id.dp);
	            view.setTag(holder);
	        } 
	        else {
	            holder = (QuestionHolder) view.getTag();
	        }
	        try {
	        	final JSONObject obj = (JSONObject)questions.get(position) ;
	        	holder.tags.setText("");
	        	for(int i = 0 ; i < obj.getJSONArray("tags").length() ; i++)
	        	{
	        		holder.tags.append(obj.getJSONArray("tags").getString(i) + "    ") ;
	        	}
	        	holder.question.setText(obj.getString("title"));
	        	//The avatar URL is hardcoded for now. 
	        	Controller.getUserDP(new Callback() {
					@Override
					public void onRequestComplete(Object output, int i) {
						if(i==0)
						{
							Bitmap img = (Bitmap)output ;
							holder.dp.setImageBitmap(img);
						}
					}
		        }, obj.getString("avatar"));
	        	view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.d("CLICK!", "works?") ;
						Intent intent = new Intent(parentActivity, SinglePost.class);
						try {
							intent.putExtra("id", obj.getString("ID")) ;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
	                    parentActivity.startActivity(intent);
					}
		        	
		        });
	        	
			} catch (JSONException e) {
				e.printStackTrace();
			}
	        
		   return view;
	}

}


class QuestionHolder {
    public TextView question;
    public TextView tags; 
    public ImageView dp; 
}