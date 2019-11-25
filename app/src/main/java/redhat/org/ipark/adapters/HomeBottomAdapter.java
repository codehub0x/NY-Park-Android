package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import redhat.org.ipark.R;

public class HomeBottomAdapter extends RecyclerView.Adapter<HomeBottomAdapter.ViewHolder> implements View.OnClickListener {

    public interface ClickListener {
        void onItemClick(int position);
    }

    private Context mContext;
    private ClickListener mListener;

    public HomeBottomAdapter(Context context, ClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_bottom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.layout.setTag(position);
        holder.layout.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_home_bottom_layout) {
            mListener.onItemClick((Integer) view.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private RoundedImageView imageView;
        private TextView textAddress;
        private TextView textBook;

        public ViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.item_home_bottom_layout);
            imageView = view.findViewById(R.id.item_home_bottom_imageView);
            textAddress = view.findViewById(R.id.item_home_bottom_text_address);
            textBook = view.findViewById(R.id.item_home_bottom_text_book);
        }
    }
}
