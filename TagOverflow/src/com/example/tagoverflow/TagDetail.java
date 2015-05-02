package com.example.tagoverflow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TagDetail extends ActionBarActivity {

    SharedPreferences pref;
    Intent intent;

    TextView tagTitle;
    TextView tagFrequency;
    TextView tagDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_details_test);
        pref = getSharedPreferences("AppPref", MODE_PRIVATE);
        intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("Tag Details");
        tagTitle = (TextView) findViewById(R.id.tagtitle);
        tagFrequency = (TextView) findViewById(R.id.tagfrequency);
        tagDescription = (TextView) findViewById(R.id.tagdetail);

        Controller.getTagDetails(new Callback() {
            @Override
            public void onRequestComplete(Object output, int x) {
                try {
                    updateDetails((String) output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, intent.getStringExtra("tagName"));
    }

    public void updateDetails(String output) throws JSONException {
        JSONObject obj = new JSONObject(output);

        tagTitle.setText(obj.getString("name"));
        tagFrequency.setText(obj.getString("frequency"));
        tagDescription.setText(obj.getString("description"));

        JSONArray arrS;
        try {
            arrS = obj.getJSONArray("synonyms");
        } catch (Exception e) {
            arrS = new JSONArray();
        }

        ListView synonymsList = (ListView) findViewById(R.id.listViewSynonyms);
        tagListAdapter adapter = null;
        try {
            adapter = new tagListAdapter(this, arrS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        synonymsList.setAdapter(adapter);

        JSONArray arrR;
        try {
            arrR = obj.getJSONArray("related_tags");
        } catch (Exception e) {
            arrR = new JSONArray();
        }

        ListView relatedTagsList = (ListView) findViewById(R.id.listViewRelatedTags);
        tagListAdapter relatedTagAdapter = null;
        try {
            relatedTagAdapter = new tagListAdapter(this, arrR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        relatedTagsList.setAdapter(relatedTagAdapter);
    }
}

@SuppressLint("NewApi")
class tagListAdapter extends BaseAdapter {
    JSONArray elements;
    LayoutInflater inflater;
    Activity parentActivity;

    public tagListAdapter(Activity myActivity, JSONArray jsonOutput) throws JSONException {
        elements = jsonOutput;
        parentActivity = myActivity;
        inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return elements.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return elements.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        try {
            JSONObject obj = (JSONObject) elements.get(position);
            return Integer.parseInt(obj.getString("ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final TagElementHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.synonyms, null);
            holder = new TagElementHolder();
            holder.element = (TextView) view.findViewById(R.id.synonym);
            view.setTag(holder);
        } else {
            holder = (TagElementHolder) view.getTag();
        }
        try {
            final String obj = (String) elements.get(position);
            holder.element.setText(Html.fromHtml(obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}

class TagElementHolder {
    public TextView element;
}
