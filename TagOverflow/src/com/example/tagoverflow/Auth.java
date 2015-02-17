package com.example.tagoverflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Auth extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
	
	public void authenticate(View view) {
		
		//Do the oauth part here, get the user id.
		//Send the userid to the Home Activity
		
		Intent intent = new Intent(Auth.this, Home.class);
        intent.putExtra("id", "2313"); // Sending random id for now
        startActivity(intent);
	 }
}