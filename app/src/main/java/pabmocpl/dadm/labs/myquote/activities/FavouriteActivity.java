package pabmocpl.dadm.labs.myquote.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.adapters.FavouriteRecyclerAdapter;
import pabmocpl.dadm.labs.myquote.databases.QuotationOpenHelper;
import pabmocpl.dadm.labs.myquote.objects.Quotation;

public class FavouriteActivity extends AppCompatActivity {

    private FavouriteRecyclerAdapter favouriteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        RecyclerView r = findViewById(R.id.rvFavourite);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        r.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        r.addItemDecoration(itemDecoration);

        List<Quotation> quotationList = QuotationOpenHelper.getInstance(this).getAllQuotations();

        FavouriteRecyclerAdapter recyclerAdapter = new FavouriteRecyclerAdapter(quotationList,
                (adapter, position) -> onAuthorInfoClick(adapter.getQuotationAt(position)),
                (adapter, position) -> showDialogAndDelete(adapter,position));
        r.setAdapter(recyclerAdapter);
        favouriteRecyclerAdapter = recyclerAdapter;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        RecyclerView recyclerView = findViewById(R.id.rvFavourite);
        if(favouriteRecyclerAdapter.getItemCount()==0){menu.findItem(R.id.miDeleteAll).setVisible(false);}
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.miDeleteAll:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage(R.string.dialog_delete_all_quotation_message);
                alertBuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    favouriteRecyclerAdapter.removeAllQuotations();
                    QuotationOpenHelper.getInstance(this).deleteAllQuotations();
                    item.setVisible(false);
                });
                alertBuilder.setNegativeButton(R.string.no, (dialog, which) -> {} );
                alertBuilder.create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean showDialogAndDelete(FavouriteRecyclerAdapter adapter, int position) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.dialog_delete_quotation_message);
        alertBuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
            QuotationOpenHelper.getInstance(this).deleteQuotation(adapter.getQuotationAt(position));
            adapter.removeQuotationAt(position);
        });
        alertBuilder.setNegativeButton(R.string.no, (dialog, which) -> {} );
        alertBuilder.create().show();
        return true;
    }

    public void onAuthorInfoClick(Quotation quotation) {
        String authorName = URLEncoder.encode(quotation.getQuoteAuthor());
        if(authorName == ""){
            Toast.makeText(this,R.string.toast_unknown_author, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorName));
            startActivity(intent);
        }
    }

    public ArrayList<Quotation> getMockQuotations() {
        ArrayList<Quotation> list = new ArrayList<>();
        list.add(new Quotation("You have to write the book that wants to be written. " +
                "And if the book will be too difficult for grown-ups, then you write it for children.",
                "Madeleine L'Engle"));
        list.add(new Quotation("If you don't have time to read, you don't have the time " +
                "(or the tools) to write. Simple as that.", "Stephen King"));
        list.add(new Quotation("We write to taste life twice, in the moment and in retrospect",
                "Anaïs Nin"));
        list.add(new Quotation("You can make anything by writing.", "C.S. Lewis"));
        list.add(new Quotation("Tears are words that need to be written.", "Paulo Coelho"));
        list.add(new Quotation("To survive, you must tell stories.", "Umberto Eco"));
        list.add(new Quotation("If my doctor told me I had only six minutes to live, " +
                "I wouldn't brood. I'd type a little faster.", "Isaac Asimov"));
        list.add(new Quotation("I write to discover what I know.", "Flannery O'Connor"));
        list.add(new Quotation("Writers live twice.", "Natalie Goldberg"));
        list.add(new Quotation("Writing is its own reward.", "Henry Miller"));

        Quotation qNull = new Quotation();
        qNull.setQuoteText("This is a null author");
        list.add(qNull);

        list.add(new Quotation("This is an empty author", ""));

        return list;
    }

}