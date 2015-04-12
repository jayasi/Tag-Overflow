package com.example.tagoverflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("NewApi")
public class RepliesAdapter extends BaseAdapter  {

	JSONArray replies ;
	LayoutInflater inflater ; 
	Activity parentActivity ; 
	
	public RepliesAdapter(Activity myActivity, JSONArray jsonOutput, Resources resources) throws JSONException {
		replies = jsonOutput;
		parentActivity = myActivity ; 
        inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

@Override
public int getCount() {
	// TODO Auto-generated method stub
	return replies.length() ; 
}

@Override
public Object getItem(int position) {
	try {
        return replies.get(position);
    } catch (JSONException e) {
        e.printStackTrace();
    }
	return null;
}

@Override
public long getItemId(int position) {
	try {
		JSONObject obj = (JSONObject) replies.get(position) ;
        return Integer.parseInt(obj.getString("ID"));
    } catch (JSONException e) {
        e.printStackTrace();
    }
	return 0;
}

@SuppressLint("InflateParams")
@Override
public View getView(int position, View view, ViewGroup parent) {
	final ReplyHolder holder;
    if(view == null) {
        view = inflater.inflate(R.layout.replies, null);
        holder = new ReplyHolder();
        holder.reply = (TextView) view.findViewById(R.id.reply);
        holder.name = (TextView) view.findViewById(R.id.username);
        view.setTag(holder);
    } 
    else {
        holder = (ReplyHolder) view.getTag();
    }
    try {
    	final JSONObject obj = (JSONObject)replies.get(position) ;
    	holder.reply.setText(Html.fromHtml(obj.getString("body")));
    	holder.name.setText(obj.getString("username"));
    }
    catch (JSONException e) {
		e.printStackTrace();
	}
    return view ;
}

}

class ReplyHolder {
    public TextView reply;
    public TextView name;  
}
