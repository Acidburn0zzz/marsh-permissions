package com.guavabot.marshpermissions.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.guavabot.marshpermissions.R;

/**
 * Screen for configuring the app settings.
 */
public class SettingsActivity extends AppCompatActivity {

    public static final String PREF_HIDDEN = "pref_display_hidden";
    public static final String PREF_GOOGLE = "pref_display_google";
    public static final String PREF_ANDROID = "pref_display_android";

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new SettingsFragment(), null)
                    .commit();
        }

    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
        }
    }
}
