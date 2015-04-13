package tests;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */
public class DiscussionTest extends TestController {
    private static final String TEST_URL = "http://localhost:8080/discussions";

    @Test
    public void testGetDiscussion() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getDiscussion = new HttpGet(TEST_URL+"/1234");
        HttpResponse response = httpClient.execute(getDiscussion);
        assertEquals(200, response.getStatusLine().getStatusCode());
        JSONObject receivedTags = extractResponseJSONObject(response);
    }

    @Test
    public void testGetDiscussionList() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getDiscussionList = new HttpGet(TEST_URL);
        HttpResponse response = httpClient.execute(getDiscussionList);
        assertEquals(200, response.getStatusLine().getStatusCode());
        JSONArray receivedTags = extractResponseJSONArray(response);
    }
}
