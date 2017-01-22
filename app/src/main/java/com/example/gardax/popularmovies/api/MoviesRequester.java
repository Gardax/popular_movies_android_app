package com.example.gardax.popularmovies.api;

import android.content.Context;

import okhttp3.Callback;

public class MoviesRequester extends BaseRequester {
    private static final String DEFAULT_IMAGES_SIZE = "w185";

    private final String POPULAR_MOVIES_URL = "movie/popular";
    private final String TOP_RATED_MOVIES_URL = "movie/top_rated";

    public MoviesRequester(Context context) {
        super(context);
    }

    public void getPopularMovies(Callback callback) {
        run(POPULAR_MOVIES_URL, callback);
    }

    public void getTopRatedMovies(Callback callback) {
        run(TOP_RATED_MOVIES_URL, callback);
    }

    public static String buildImageUrl(String posterPath) {
        return apiImagesUrl + DEFAULT_IMAGES_SIZE + posterPath;
    }


}
