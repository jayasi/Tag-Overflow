package tests;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Apoorv Singh on 4/2/2015.
 */
public class TestController {

    protected JSONObject extractResponseJSONObject(HttpResponse response)
            throws IOException, IllegalStateException, JSONException {
        return new JSONObject(IOUtils.toString(response.getEntity().getContent()));
    }

    protected JSONArray extractResponseJSONArray(HttpResponse response)
            throws IOException, IllegalStateException, JSONException {
        return new JSONArray(IOUtils.toString(response.getEntity().getContent()));
    }
}
