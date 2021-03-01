package pabmocpl.dadm.labs.myquote.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import pabmocpl.dadm.labs.myquote.R;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView tvQuotation = findViewById(R.id.tvQuotation);
        String data = tvQuotation.getText().toString();
        String name = preferences.getString("pref_name", getString(R.string.sample_name));
        if(name.trim().equals("")) {name = getString(R.string.sample_name);}
        tvQuotation.setText(data.replaceAll("%1s", name));

        TextView tvAuthor = findViewById(R.id.tvAuthor);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quotation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView tvQuotation = findViewById(R.id.tvQuotation);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        switch (item.getItemId()) {
            case R.id.miAdd:
                return true;
            case R.id.miRefresh:
                tvQuotation.setText(R.string.sample_quote);
                tvAuthor.setText(R.string.sample_author);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}