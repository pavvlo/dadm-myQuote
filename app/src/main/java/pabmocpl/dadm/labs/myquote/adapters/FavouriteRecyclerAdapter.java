package pabmocpl.dadm.labs.myquote.adapters;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;


    public FavouriteRecyclerAdapter(List<Quotation> data, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.data = data;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_list_row, parent, false);
        FavouriteRecyclerAdapter.ViewHolder holder = new ViewHolder(view, clickListener, longClickListener);
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

    public Quotation getQuotationAt(int position){return data.get(position);}

    public void removeQuotationAt(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAllQuotations(){
        data.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuote;
        public TextView tvAuthorName;

        public ViewHolder(View view, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            super(view);

            tvQuote = view.findViewById(R.id.tvRowQuote);
            tvAuthorName = view.findViewById(R.id.tvRowAuthor);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClickListener(FavouriteRecyclerAdapter.this, getAdapterPosition());
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return longClickListener.onItemLongClickListener(FavouriteRecyclerAdapter.this, getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(FavouriteRecyclerAdapter adapter, int position);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClickListener(FavouriteRecyclerAdapter adapter, int position);
    }

}
