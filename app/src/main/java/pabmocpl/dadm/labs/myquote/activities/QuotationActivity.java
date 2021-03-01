package pabmocpl.dadm.labs.myquote.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ClipData;
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
import pabmocpl.dadm.labs.myquote.databases.QuotationOpenHelper;
import pabmocpl.dadm.labs.myquote.databases.QuotationRoomDatabase;
import pabmocpl.dadm.labs.myquote.objects.Quotation;
import androidx.preference.PreferenceFragmentCompat;


public class QuotationActivity extends AppCompatActivity {

    private int quotationNumber = 0;
    private Menu menu;
    private int databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        quotationNumber = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseAccess = Integer.parseInt(preferences.getString(getString(R.string.pref_database_key), "0"));
        TextView tvQuotation = findViewById(R.id.tvQuotation);
        String data = tvQuotation.getText().toString();
        String name = preferences.getString(getString(R.string.pref_name_key), getString(R.string.sample_name));
        if(name.trim().equals("")) {name = getString(R.string.sample_name);}
        tvQuotation.setText(data.replaceAll("%1s", name));

        TextView tvAuthor = findViewById(R.id.tvAuthor);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quotation, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView tvQuotation = findViewById(R.id.tvQuotation);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        Quotation quotation;
        switch (item.getItemId()) {
            case R.id.miAdd:
                quotation = new Quotation(tvQuotation.getText().toString(), tvAuthor.getText().toString());
                if(databaseAccess == 0) {
                    QuotationOpenHelper.getInstance(this).addQuotation(quotation);
                }else {
                    QuotationRoomDatabase.getInstance(this).quotationDAO().addQuotation(quotation);
                }
                item.setVisible(false);
                return true;
            case R.id.miRefresh:

                quotation = new Quotation(getString(R.string.sample_quote)+" "+quotationNumber, getString(R.string.sample_author));
                quotationNumber++;
                tvQuotation.setText(quotation.getQuoteText());
                tvAuthor.setText(quotation.getQuoteAuthor());
                if(databaseAccess == 0) {
                    menu.findItem(R.id.miAdd).setVisible(!QuotationOpenHelper.getInstance(this).hasQuotation(quotation));
                }else {
                    menu.findItem(R.id.miAdd).setVisible(
                            QuotationRoomDatabase
                                    .getInstance(this)
                                    .quotationDAO()
                                    .getQuotation(quotation.getQuoteText()) == null);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}