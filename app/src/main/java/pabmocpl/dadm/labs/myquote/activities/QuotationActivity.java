package pabmocpl.dadm.labs.myquote.activities;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.databases.QuotationOpenHelper;
import pabmocpl.dadm.labs.myquote.databases.QuotationRoomDatabase;
import pabmocpl.dadm.labs.myquote.objects.Quotation;
import pabmocpl.dadm.labs.myquote.threads.QuotationActivityThread;


public class QuotationActivity extends AppCompatActivity {

    private Menu menu;
    private int databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseAccess = Integer.parseInt(preferences.getString(getString(R.string.pref_database_key), "0"));
        TextView tvQuotation = findViewById(R.id.tvQuotation);
        String data = tvQuotation.getText().toString();
        String name = preferences.getString(getString(R.string.pref_name_key), getString(R.string.sample_name));
        if (name.trim().equals("")) {
            name = getString(R.string.sample_name);
        }
        tvQuotation.setText(data.replaceAll("%1s", name));

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
                quotation = new Quotation(
                        tvQuotation.getText().toString(),
                        tvAuthor.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (databaseAccess == 0) {
                            QuotationOpenHelper
                                    .getInstance(QuotationActivity.this)
                                    .addQuotation(quotation);
                        } else {
                            QuotationRoomDatabase
                                    .getInstance(QuotationActivity.this)
                                    .quotationDAO()
                                    .addQuotation(quotation);
                        }
                    }
                }).start();

                item.setVisible(false);
                return true;
            case R.id.miRefresh:
                if (networkAvailable()) {
                    showProgressBar(true, false);
                    new QuotationActivityThread(this).start();
                } else {
                    Toast.makeText(this, R.string.toast_no_network, Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionBarButtonsVisibility(boolean showAddButton, boolean showRefreshButton) {
        menu.findItem(R.id.miAdd).setVisible(showAddButton);
        menu.findItem(R.id.miRefresh).setVisible(showRefreshButton);
    }

    public void showProgressBar(boolean showProgressBar, boolean showAddButton) {
        ProgressBar progressBar = findViewById(R.id.pbQuotation);
        if (showProgressBar) {
            setActionBarButtonsVisibility(false, false);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            setActionBarButtonsVisibility(showAddButton, true);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public boolean networkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < 23) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        } else {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;

            NetworkCapabilities networkCapabilities = connectivityManager
                    .getNetworkCapabilities(network);
            return networkCapabilities != null && (
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
        }

    }

    public void onQuotationReceived(Quotation quotation) {
        TextView tvQuotation = findViewById(R.id.tvQuotation);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        tvQuotation.setText(quotation.getQuoteText());
        tvAuthor.setText(quotation.getQuoteAuthor());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                boolean bool;
                if (databaseAccess == 0) {
                    bool = !QuotationOpenHelper
                            .getInstance(QuotationActivity.this)
                            .hasQuotation(quotation);
                } else {
                    bool = QuotationRoomDatabase
                            .getInstance(QuotationActivity.this)
                            .quotationDAO()
                            .getQuotation(quotation.getQuoteText()) == null;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showProgressBar(false, bool);
                    }
                });
            }
        }).start();

    }

}