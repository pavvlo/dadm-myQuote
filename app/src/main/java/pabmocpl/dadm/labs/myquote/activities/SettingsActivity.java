package pabmocpl.dadm.labs.myquote.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fcvSettings, SettingsFragment.class, null)
                .commit();
    }
}