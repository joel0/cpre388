package com.example.mediaplayerpreferences;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MediaPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MediaPreferencesFragment())
                .commit();
    }

    public static class MediaPreferencesFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Make sure the default values are applied.
            PreferenceManager.setDefaultValues(getActivity(), R.xml.media_preferences, false);

            // Load the preferences from the XML resource
            addPreferencesFromResource(R.xml.media_preferences);
        }
    }
}
