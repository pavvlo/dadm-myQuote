package pabmocpl.dadm.labs.myquote.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.adapters.FavouriteRecyclerAdapter;
import pabmocpl.dadm.labs.myquote.objects.Quotation;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);



        RecyclerView r = findViewById(R.id.rvFavourite);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        r.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        r.addItemDecoration(itemDecoration);

        FavouriteRecyclerAdapter adapter = new FavouriteRecyclerAdapter(getMockQuotations(),
                position -> onAuthorInfoClick(this, FavouriteRecyclerAdapter.getQuotationAt(position)));
        r.setAdapter(adapter);
    }

    public void onAuthorInfoClick(Context context, Quotation quotation) {
        String authorName = URLEncoder.encode(quotation.getQuoteAuthor());
        if(authorName == ""){
            Toast.makeText(context,R.string.toast_unknown_author, Toast.LENGTH_SHORT).show();
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