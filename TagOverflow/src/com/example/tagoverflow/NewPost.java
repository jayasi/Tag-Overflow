package com.example.tagoverflow;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class NewPost extends ActionBarActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
