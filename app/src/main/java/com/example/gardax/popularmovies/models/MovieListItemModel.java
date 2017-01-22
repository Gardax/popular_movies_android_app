package com.example.gardax.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieListItemModel implements Parcelable {
    private long id;
    private String posterPath;
    private String title;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private String releaseData;

    public MovieListItemModel(long id, String posterUrl, String title) {
        setId(id);
        setPosterPath(posterUrl);
        setTitle(title);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
    }

    protected MovieListItemModel(Parcel in) {
        id = in.readLong();
        posterPath = in.readString();
        title = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        releaseData = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseData);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieListItemModel> CREATOR = new Parcelable.Creator<MovieListItemModel>() {
        @Override
        public MovieListItemModel createFromParcel(Parcel in) {
            return new MovieListItemModel(in);
        }

        @Override
        public MovieListItemModel[] newArray(int size) {
            return new MovieListItemModel[size];
        }
    };
}
