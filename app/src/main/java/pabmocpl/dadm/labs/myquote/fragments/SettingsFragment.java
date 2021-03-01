package pabmocpl.dadm.labs.myquote.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import pabmocpl.dadm.labs.myquote.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey);
    }

}
