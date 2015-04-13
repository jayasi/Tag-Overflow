package tests;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */
public class TagsTest extends TestController {
    private static final String TEST_URL = "http://localhost:8080/tags";

    @Test
    public void testGetTags() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost getTags = new HttpPost(TEST_URL);
        String title = "c#";
        String body = "c#";
        /* Test should return 200 OK response with c# (DUH!) */
        ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("title", title));
        data.add(new BasicNameValuePair("body", body));
        getTags.setEntity(new UrlEncodedFormEntity(data));
        HttpResponse tagsResponse = httpClient.execute(getTags);
        assertEquals(200, tagsResponse.getStatusLine().getStatusCode());
        JSONArray receivedTags = extractResponseJSONArray(tagsResponse);
        assertTrue(receivedTags.toString().contains("c#"));
    }
}