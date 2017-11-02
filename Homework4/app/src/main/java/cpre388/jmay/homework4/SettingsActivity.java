package cpre388.jmay.homework4;

import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_INITIAL_POOL_SIZE = "pref_init_pool_size";
    public static final String KEY_MAX_POOL_SIZE = "pref_max_pool_size";
    public static final String KEY_KEEPALIVE = "pref_keepalive";
    public static final String KEY_KEEPALIVE_TIMEUNIT = "pref_timeunit";
    public static final String KEY_NUM_TASKS = "pref_num_tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preference from an XML resource.
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
