package com.example.tagoverflow;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class AsyncHTTP extends AsyncTask<URLContext, Integer, String> {

    ArrayList<String> output = new ArrayList<String>();
    Callback callback;

    public AsyncHTTP(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(URLContext... urls) {
        int count = urls.length;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < count; i++) {
            try {
                output.append(JSON.request(urls[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }

    protected void onPostExecute(String output) {
    	Log.d("output", output) ;
       callback.onRequestComplete(output, 1);
    }
}
