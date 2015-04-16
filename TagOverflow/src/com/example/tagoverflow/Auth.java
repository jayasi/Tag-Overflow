package com.example.tagoverflow;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;

public class Auth extends Activity {
	WebView web;
	Button auth;
	SharedPreferences pref;
	TextView Access;
	static String OAUTH_URL = "https://stackexchange.com/oauth/dialog" ;
	static String REDIRECT_URI = "https://stackexchange.com/oauth/login_success" ; 
	static String CLIENT_ID = "4548" ;
	static String SENDER_ID = "836191956228" ;
	static String OAUTH_SCOPE = "write_access private_info no_expiry read_inbox" ;
	Dialog auth_dialog;
	
	GoogleCloudMessaging gcm;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        //setContentView(R.layout.auth_dialog) ;
        pref = getSharedPreferences("AppPref", MODE_PRIVATE); 
        authenticate(new View(this.getApplicationContext()));
        registerInBackground() ;
    }
	
	public void authenticate(View view) {
		//Do the oauth part here, get the user id.
		//Send the userid to the Home Activity
		
		auth_dialog = new Dialog(Auth.this);
        auth_dialog.setContentView(R.layout.auth_dialog);
        web = (WebView)auth_dialog.findViewById(R.id.webv);
        //web = (WebView)findViewById(R.id.webv) ;
		web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(OAUTH_URL+"?redirect_uri="+REDIRECT_URI+"&client_id="+CLIENT_ID+"&scope="+OAUTH_SCOPE+"&key=HtY8bMld7du)7cX*VkZ5yg((");
        web.setWebViewClient(new WebViewClient() {
        	boolean authComplete = false;
            Intent resultIntent = new Intent();
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
             super.onPageStarted(view, url, favicon); 
            }
            String authCode;
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("#access_token=") && authComplete != true) {
                    Uri uri = Uri.parse(url);
                    authCode = uri.getEncodedFragment().split("&")[0].split("=")[1] ; 
                    Log.d("Code", authCode);
                    authComplete = true;
                    resultIntent.putExtra("code", authCode);
                    Auth.this.setResult(Activity.RESULT_OK, resultIntent);
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("AccessToken", authCode); //Storing the accessToken in the shared preferences for easy access
                    edit.commit();
                    auth_dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Authorization Code is: " +authCode, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Auth.this, Home.class);
                    startActivity(intent);
                }else if(url.contains("error=access_denied")){
                    Log.i("", "ACCESS_DENIED_HERE");
                    resultIntent.putExtra("code", authCode);
                    authComplete = true;
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    auth_dialog.dismiss();
                }
            }
        });
        auth_dialog.show();
        auth_dialog.setTitle("Authorize TagOverflow");
        auth_dialog.setCancelable(true);
	 }


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void registerInBackground() {
        Log.d("loginprocess","in register in background");
		new AsyncTask() {
			protected String doInBackground(Void... params) {
				// //Log.d(TAG, "starting");
				String msg;
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					}
					String regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;
					sendRegistrationIdToBackend();
					//storeRegistrationId(getApplicationContext(), regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			protected void onPostExecute(String msg) {
			}

			@Override
			protected Object doInBackground(Object... arg0) {
				try{
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					}
					String regid = gcm.register(SENDER_ID);
					Log.d("here", regid) ;
					ArrayList<NameValuePair> data = new ArrayList<NameValuePair>() ; 
					data.add(new BasicNameValuePair("access_token", pref.getString("AccessToken", "empty"))) ; 
					data.add(new BasicNameValuePair("registration_id", regid)) ;
					Controller.sendRegistrationID(data, new Callback() {

						@Override
						public void onRequestComplete(Object output, int x) {
							// TODO Auto-generated method stub
							Log.d("sent regid", (String)output) ;
						}
						
					});
					sendRegistrationIdToBackend();
				}
				catch (IOException ex) {
				}
				return "";
				// TODO Auto-generated method stub
			}
		}.execute(null, null, null);

	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */

	private void sendRegistrationIdToBackend() {
        Log.d("loginprocess","in send registration id");

        // Your implementation here.
		// //Log.d(TAG, "Sending regid to backpack");
        //TODO: What does this do?
       // new GetDataInAsyncTask(this).execute(ServerURL+"/api/gcm", "regid="
         //       + regid);
//		getDataInAsyncTask(ServerURL+"/api/gcm", "regid="
//				+ regid, "");
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public String getRegistrationId(Context context) {
        Log.d("loginprocess","in get registration id");
		String registrationId = pref.getString("regid", "");
		if (registrationId.isEmpty()) {
			Log.i("jkjh", "Registration not found.");
			return "";
		}
		return registrationId;
	}


}
