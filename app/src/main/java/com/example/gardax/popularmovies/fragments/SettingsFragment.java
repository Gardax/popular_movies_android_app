package com.example.gardax.popularmovies.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.gardax.popularmovies.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
