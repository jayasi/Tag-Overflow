package services;

import helpers.HTTPHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */

@Service
public class TagService {

    private List<String> getTagsList(String title, String body) throws IOException{
        String URL = "http://localhost:8081/tags";
        ArrayList<NameValuePair> tagsRequest = new ArrayList<NameValuePair>();
        tagsRequest.add(new BasicNameValuePair("title", title));
        tagsRequest.add(new BasicNameValuePair("body", body));
        String response = HTTPHelper.post(URL, tagsRequest);
        JSONArray responseJSON = new JSONArray(response);
        ArrayList<String> output = new ArrayList<String>();
        for(int i = 0; i < responseJSON.length(); i++) {
            output.add(responseJSON.get(i).toString());
        }
        return output;
    }

    public List<String> getTags(String title, String body) {
        List<String> tagList = null;
        try {
            tagList = getTagsList(title, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tagList;
    }
}
