package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import redhat.org.ipark.R;
import redhat.org.ipark.models.BookItem;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    public interface ClickListener {
        void onItemClick(int position);
    }

    private Context mContext;
    private ClickListener mListener;
    private List<BookItem> data;

    public BookAdapter(Context context, List<BookItem> items, ClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.data = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Integrate the item data by position

        BookItem item = data.get(position);
        holder.textDay.setText(item.getStrDay());
        holder.textDate.setText(item.getStrDate());
        holder.textPrice.setText(item.getPrice());
        holder.textPrice.setEnabled(item.isValid());
        if (item.isValid()) {
            holder.textPrice.setSelected(item.isSelected());
        } else {
            holder.textPrice.setSelected(false);
        }

        holder.textPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setList(List<BookItem> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textDay;
        private TextView textDate;
        private TextView textPrice;

        public ViewHolder(View view) {
            super(view);

            textDay = view.findViewById(R.id.item_book_text_day);
            textDate = view.findViewById(R.id.item_book_text_date);
            textPrice = view.findViewById(R.id.item_book_text_price);
        }
    }
}
