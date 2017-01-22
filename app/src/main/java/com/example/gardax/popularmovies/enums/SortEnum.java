package com.example.gardax.popularmovies.enums;

import com.example.gardax.popularmovies.R;

public enum SortEnum {
    POPULAR ("popular_movies"),
    TOP_RATED ("top_rated_movies");

    public String value;

    SortEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
