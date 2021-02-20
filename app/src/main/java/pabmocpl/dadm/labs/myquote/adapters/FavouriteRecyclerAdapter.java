package pabmocpl.dadm.labs.myquote.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pabmocpl.dadm.labs.myquote.R;
import pabmocpl.dadm.labs.myquote.objects.Quotation;

public class FavouriteRecyclerAdapter
        extends RecyclerView.Adapter<FavouriteRecyclerAdapter.ViewHolder> {

    private List<Quotation> data;

    public FavouriteRecyclerAdapter(List<Quotation> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_list_row, parent, false);
        FavouriteRecyclerAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvQuote.setText(data.get(position).getQuoteText());
        holder.tvAuthorName.setText(data.get(position).getQuoteAuthor());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuote;
        public TextView tvAuthorName;

        public ViewHolder(View view) {
            super(view);
            tvQuote = (TextView) view.findViewById(R.id.tvRowQuote);
            tvAuthorName = (TextView) view.findViewById(R.id.tvRowAuthor);
        }
    }

}
