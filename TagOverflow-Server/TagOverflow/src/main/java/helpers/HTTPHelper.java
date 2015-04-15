package helpers;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Apoorv Singh on 3/30/2015.
 */
public class HTTPHelper {

    public static String post(String URL, ArrayList<NameValuePair> data) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(URL);
        postRequest.setEntity(new UrlEncodedFormEntity(data));
        HttpResponse postResponse = httpClient.execute(postRequest);
        return IOUtils.toString(postResponse.getEntity().getContent());
    }
}
