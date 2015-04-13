package services;

import retrofit.RestAdapter;

/**
 * Created by Apoorv Singh on 4/12/2015.
 */

public class SOService {
    private static String API_URL = "https://api.stackexchange.com/2.2";

    public static SO getService() {
        return new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build()
                .create(SO.class);
    }
}
