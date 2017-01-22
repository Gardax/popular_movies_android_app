package com.example.gardax.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gardax.popularmovies.R;
import com.example.gardax.popularmovies.api.MoviesRequester;
import com.example.gardax.popularmovies.models.MovieListItemModel;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView posterImageView;
    private TextView releaseDataTextView;
    private TextView ratingTextView;
    private TextView overviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        MovieListItemModel movie =
                getIntent().getParcelableExtra(MovieListItemModel.class.getSimpleName());

        titleTextView = (TextView) findViewById(R.id.tv_movie_title);
        posterImageView = (ImageView) findViewById(R.id.iv_movie_poster);
        releaseDataTextView = (TextView) findViewById(R.id.tv_release_date);
        ratingTextView = (TextView) findViewById(R.id.tv_rating);
        overviewTextView = (TextView) findViewById(R.id.tv_overview);

        titleTextView.setText(movie.getOriginalTitle());
        releaseDataTextView.setText(movie.getReleaseData());
        ratingTextView.setText(Double.toString(movie.getVoteAverage()));
        overviewTextView.setText(movie.getOverview());

        Glide.with(this)
                .load(MoviesRequester.buildImageUrl(movie.getPosterPath()))
                .into(posterImageView);
    }
}
