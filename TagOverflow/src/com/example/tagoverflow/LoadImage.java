package com.example.tagoverflow;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadImage extends AsyncTask<String, String, Bitmap> {

	Bitmap bitmap;
	Callback callback ; 
	
	public LoadImage(Callback callback) {
        this.callback = callback;
    }
	
	@Override
	protected Bitmap doInBackground(String... params) {
		try {
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
           } 
		catch (Exception e) {
           e.printStackTrace();
     }
   return bitmap;
	}
	
	protected void onPostExecute(Bitmap image) {
        if(image != null)
        {
        	callback.onRequestComplete(image, 0);
        }
      }
}