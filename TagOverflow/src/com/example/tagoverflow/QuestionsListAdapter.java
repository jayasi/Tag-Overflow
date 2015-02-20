package com.example.tagoverflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject ;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionsListAdapter extends BaseAdapter {

	JSONArray questions ;
	LayoutInflater inflater ; 
	
	public QuestionsListAdapter(Activity myActivity, String jsonOutput, Resources resources) throws JSONException {
		Log.d("adapter", "construct");
		questions = new JSONArray("[{'id':2},{'id':3},{'id':3},{'id':3},{'id':3}]"); //Random stuff for now. Will comment this and uncomment the following
		//questions = new JSONArray(jsonOutput);
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
            return Integer.parseInt(obj.getString("question_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		 final QuestionHolder holder;
		 Log.d("adapter", "getview");
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
	        	JSONObject obj = (JSONObject)questions.get(position) ;
	        	//Will uncomment this and comment the further two lines after we get proper JSON.
	        	//	holder.tags.setText(obj.getJSONObject("owner").getString("display_name"));
			    //	holder.question.setText(obj.getString("title"));
	        	holder.tags.setText("C++   Java   Programming-Languages   Integration   Sample-tags");
	        	holder.question.setText("This is a sample question which was asked.");
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
		        }, "https://www.gravatar.com/avatar/303e66fe201050e384ab3fad08c1ed6d?s=128&d=identicon&r=PG&f=1");
	        	
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