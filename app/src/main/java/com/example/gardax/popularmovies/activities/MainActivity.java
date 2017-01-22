package com.example.gardax.popularmovies.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gardax.popularmovies.R;
import com.example.gardax.popularmovies.adapters.MoviesAdapter;
import com.example.gardax.popularmovies.api.MoviesRequester;
import com.example.gardax.popularmovies.enums.SortEnum;
import com.example.gardax.popularmovies.models.MovieListItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieClickLister {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MoviesAdapter moviesAdapter;
    private RecyclerView moviesList;
    private TextView chosenSortTextView;
    private String chosenOrder;

    private ArrayList<MovieListItemModel> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = (RecyclerView) findViewById(R.id.rv_movies);

        int numberOfColumns = 2;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            numberOfColumns = 4;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        moviesList.setLayoutManager(layoutManager);
        moviesList.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(this);
        moviesList.setAdapter(moviesAdapter);

        chosenOrder = getChosenOrderSetting();

        setChosenSortText();
        if(savedInstanceState == null) {
            loadMovies();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //If the sorting setting is edited we are reloading the movies
        if(!chosenOrder.equals(getChosenOrderSetting())) {
            chosenOrder = getChosenOrderSetting();
            setChosenSortText();
            loadMovies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intentMain = new Intent(MainActivity.this,
                    SettingsActivity.class);
            MainActivity.this.startActivity(intentMain);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getChosenOrderSetting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(getString(R.string.setting_sort_chosen_key), SortEnum.POPULAR.getValue());
    }

    private void setChosenSortText() {
        chosenSortTextView = (TextView) findViewById(R.id.tv_chosen_sort);
        String sortText = chosenOrder.equals(SortEnum.POPULAR.getValue()) ?
                getString(R.string.popular_sort_chosen) : getString(R.string.top_rated_sort_chosen);
        chosenSortTextView.setText(sortText);
    }

    private void loadMovies() {
        MoviesRequester moviesRequester = new MoviesRequester(this);

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.movies_api_error),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            JSONObject result = new JSONObject(response.body().string());
                            movies = extractMoviesList(result.getJSONArray(getString(R.string.results_key)));
                            moviesAdapter.setMovies(movies);
                        }
                        catch (JSONException |IOException ex) {
                            Log.e(TAG, getString(R.string.could_not_parse_response_data) + ex.toString());
                        }
                    }
                });
            }
        };

        if(chosenOrder.equals(SortEnum.POPULAR.getValue())) {
            moviesRequester.getPopularMovies(callback);
        }
        else {
            moviesRequester.getTopRatedMovies(callback);
        }
    }

    private ArrayList<MovieListItemModel> extractMoviesList(JSONArray moviesData) throws JSONException {
        ArrayList<MovieListItemModel> movies = new ArrayList<>();
        for (int i = 0; i < moviesData.length(); i++) {
            JSONObject movieData = moviesData.getJSONObject(i);
            MovieListItemModel movie = new MovieListItemModel(
                    movieData.getLong(getString(R.string.id_key)),
                    movieData.getString(getString(R.string.poster_key)),
                    movieData.getString(getString(R.string.title_key))
            );
            movie.setOriginalTitle(movieData.getString(getString(R.string.original_title_key)));
            movie.setOverview(movieData.getString(getString(R.string.overview_key)));
            movie.setReleaseData(movieData.getString(getString(R.string.release_date_key)));
            movie.setVoteAverage(movieData.getDouble(getString(R.string.vote_average_key)));
            movies.add(movie);
        }

        return movies;
    }

    @Override
    public void onMovieClick(MovieListItemModel movie) {
        Intent intentMain = new Intent(MainActivity.this,
                MovieDetailsActivity.class);

        intentMain.putExtra(MovieListItemModel.class.getSimpleName(), movie);
        MainActivity.this.startActivity(intentMain);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", movies);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        movies = savedInstanceState.getParcelableArrayList("movies");
        moviesAdapter.setMovies(movies);
    }
}
