package pabmocpl.dadm.labs.myquote.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.databases.QuotationRoomDatabase;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey);
        findPreference(getString(R.string.pref_database_key))
                .setOnPreferenceChangeListener((preference, newValue) -> {
                    if(newValue.equals(0)){
                        QuotationRoomDatabase.destroyInstance();
                    }
                    return true;
        });
    }

}
