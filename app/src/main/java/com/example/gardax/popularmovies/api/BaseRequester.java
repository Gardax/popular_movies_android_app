package com.example.gardax.popularmovies.api;

import android.content.Context;

import com.example.gardax.popularmovies.R;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseRequester {
    private Context context;
    protected static String apiBaseUrl;
    protected static String apiImagesUrl;
    private String apiKey;
    private OkHttpClient client = new OkHttpClient();

    public BaseRequester(Context context) {
        this.context = context;

        apiKey = this.context.getResources().getString(R.string.API_KEY);
        apiBaseUrl = this.context.getResources().getString(R.string.API_URL);
        apiImagesUrl = this.context.getResources().getString(R.string.API_IMAGES_URL);
    }

    protected void run(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(apiBaseUrl + url + "?api_key=" + apiKey)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
