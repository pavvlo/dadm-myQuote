package pabmocpl.dadm.labs.myquote.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.databases.QuotationRoomDatabase;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final int SQLITE = 0;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey);
        findPreference(getString(R.string.pref_database_key))
                .setOnPreferenceChangeListener((preference, newValue) -> {
                    if (newValue.equals(SQLITE)) {
                        QuotationRoomDatabase.destroyInstance();
                    }
                    return true;
                });
    }

}
