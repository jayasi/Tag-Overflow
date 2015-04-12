package com.example.tagoverflow;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class JSON {

    public static String request(URLContext URL) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        String type = URL.getType();
        String url = URL.getURL();
        HttpResponse response = null;
        InputStream responseStream = null;
        
        // Need to use this way to support HTTPS connections. Have just used this in the GET part for now
        URL theUrl = new URL(url);
        URLConnection urlConnection = theUrl.openConnection();

        if(type.equals("GET")) {
            responseStream = urlConnection.getInputStream() ; 
        } else if(type.equals("POST")) {
            HttpPost httppost = new HttpPost(url);
            if (null!=URL.getJSON())
            {
            	StringEntity params =new StringEntity(URL.getJSON().toString());
            	httppost.setEntity(params);
            	httppost.setHeader(HTTP.CONTENT_TYPE, "application/json");
            }
            else
            {
            	 httppost.setEntity(new UrlEncodedFormEntity(URL.getData()));
            }
            response = httpclient.execute(httppost);
            /* The following code is to see what parameters have been sent. 
             * InputStream in = httppost.getEntity().getContent() ; 
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close(); */
            responseStream = response.getEntity().getContent();
        } else if(type.equals("PUT")) {
            HttpPut httpput = new HttpPut(url);
            httpput.setEntity(new UrlEncodedFormEntity(URL.getData()));
            response = httpclient.execute(httpput);
        } else if (type.equals("DELETE")) {
            HttpDelete httpdelete = new HttpDelete(url);
            response = httpclient.execute(httpdelete);
        }
        //responseStream = response.getEntity().getContent();
        if(responseStream != null) {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(responseStream));
            StringBuilder output = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null) {
                output.append(line);
            }
            return output.toString();
        } else {
            return "[]";
        }
    }
}