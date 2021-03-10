package pabmocpl.dadm.labs.myquote.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.adapters.FavouriteRecyclerAdapter;
import pabmocpl.dadm.labs.myquote.databases.QuotationOpenHelper;
import pabmocpl.dadm.labs.myquote.databases.QuotationRoomDatabase;
import pabmocpl.dadm.labs.myquote.objects.Quotation;
import pabmocpl.dadm.labs.myquote.threads.FavouriteActivityThread;

public class FavouriteActivity extends AppCompatActivity {

    private FavouriteRecyclerAdapter favouriteRecyclerAdapter;
    private int databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseAccess = Integer.parseInt(preferences.getString(getString(R.string.pref_database_key), "0"));

        favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(new ArrayList<>(),
                (adapter, position) -> onAuthorInfoClick(adapter.getQuotationAt(position)),
                this::showDialogAndDelete);

        RecyclerView recyclerView = findViewById(R.id.rvFavourite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(favouriteRecyclerAdapter);

        new FavouriteActivityThread(this, databaseAccess).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        RecyclerView recyclerView = findViewById(R.id.rvFavourite);
        if (favouriteRecyclerAdapter.getItemCount() == 0) {
            menu.findItem(R.id.miDeleteAll).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miDeleteAll) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage(R.string.dialog_delete_all_quotation_message);
            alertBuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
                favouriteRecyclerAdapter.removeAllQuotations();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (databaseAccess == 0) {
                            QuotationOpenHelper
                                    .getInstance(FavouriteActivity.this)
                                    .deleteAllQuotations();
                        } else {
                            QuotationRoomDatabase
                                    .getInstance(FavouriteActivity.this)
                                    .quotationDAO()
                                    .deleteAllQuotations();
                        }
                    }
                }).start();
                item.setVisible(false);
            });
            alertBuilder.setNegativeButton(R.string.no, (dialog, which) -> {
            });
            alertBuilder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addQuotationList(List<Quotation> quotationList) {
        if (quotationList.size() > 0) {
            favouriteRecyclerAdapter.addData(quotationList);
        }
    }

    private boolean showDialogAndDelete(FavouriteRecyclerAdapter adapter, int position) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.dialog_delete_quotation_message);
        alertBuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Handler handler = new Handler(Looper.getMainLooper());
                    if (databaseAccess == 0) {
                        QuotationOpenHelper
                                .getInstance(FavouriteActivity.this)
                                .deleteQuotation(adapter.getQuotationAt(position));
                    } else {
                        QuotationRoomDatabase
                                .getInstance(FavouriteActivity.this)
                                .quotationDAO()
                                .deleteQuotation(adapter.getQuotationAt(position));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.removeQuotationAt(position);
                        }
                    });
                }
            }).start();
        });
        alertBuilder.setNegativeButton(R.string.no, (dialog, which) -> {
        });
        alertBuilder.create().show();
        return true;
    }

    public void onAuthorInfoClick(Quotation quotation) {
        String authorName = URLEncoder.encode(quotation.getQuoteAuthor());
        if (authorName.trim().equals("")) {
            Toast.makeText(this, R.string.toast_unknown_author, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorName));
            startActivity(intent);
        }
    }

}