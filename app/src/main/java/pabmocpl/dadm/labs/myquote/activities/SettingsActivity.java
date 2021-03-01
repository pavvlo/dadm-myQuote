package pabmocpl.dadm.labs.myquote.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

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

    @Override
    protected void onPause() {
        super.onPause();
        /*
        SharedPreferences.Editor editor = preferences.edit();
        EditText etName = findViewById(R.id.etName);

        if ((etName.getText().length() > 0)) {
            editor.putString("pref_name", etName.getText().toString());
        } else {
            editor.remove("pref_name");
        }

        editor.apply();*/
    }
}