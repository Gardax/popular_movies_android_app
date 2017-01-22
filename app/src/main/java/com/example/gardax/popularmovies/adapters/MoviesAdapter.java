package com.example.gardax.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gardax.popularmovies.R;
import com.example.gardax.popularmovies.api.MoviesRequester;
import com.example.gardax.popularmovies.models.MovieListItemModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{
    private List<MovieListItemModel> movies = new ArrayList<MovieListItemModel>();
    private MovieClickLister movieClickLister;

    public interface MovieClickLister {
        void onMovieClick(MovieListItemModel movie);
    }

    public MoviesAdapter(MovieClickLister lister) {
        this.movieClickLister = lister;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.movie_item, parent, shouldAttachToParentImmediately);
        MoviesViewHolder viewHolder = new MoviesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<MovieListItemModel> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView moviePosterView;
        private TextView movieTitleView;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            moviePosterView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            movieTitleView = (TextView) itemView.findViewById(R.id.tv_movie_title);

            itemView.setOnClickListener(this);
        }

        void bind(MovieListItemModel movie) {
            Glide.with(itemView.getContext())
                    .load(MoviesRequester.buildImageUrl(movie.getPosterPath()))
                    .into(moviePosterView);
            movieTitleView.setText(movie.getTitle());
        }

        @Override
        public void onClick(View view) {
            int clickedMoviePosition = getAdapterPosition();
            movieClickLister.onMovieClick(movies.get(clickedMoviePosition));
        }
    }
}
