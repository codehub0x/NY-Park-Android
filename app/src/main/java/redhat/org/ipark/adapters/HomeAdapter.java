package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import redhat.org.ipark.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements View.OnClickListener {

    public interface ClickListener {
        void onBookClicked(int position);
        void onDetailsClicked(int position);
    }

    private Context mContext;
    private final ClickListener mListener;

    public HomeAdapter(Context context, ClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Integrate the reservations data
        holder.btnBook.setOnClickListener(this);
        holder.btnBook.setTag(position);

        holder.btnDetails.setOnClickListener(this);
        holder.btnDetails.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_home_btn_book:
                mListener.onBookClicked((Integer) view.getTag());
                break;
            case R.id.item_home_btn_details:
                mListener.onDetailsClicked((Integer) view.getTag());
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView itemImageView;
        private TextView textAddress;
        private TextView textTitle;
        private TextView textPrice;
        private MaterialButton btnBook;
        private MaterialButton btnDetails;
        private TextView textDistance;

        public ViewHolder(View view) {
            super(view);

            itemImageView = view.findViewById(R.id.item_home_imageView);
            textAddress = view.findViewById(R.id.item_home_text_address);
            textTitle = view.findViewById(R.id.item_home_text_title);
            textPrice = view.findViewById(R.id.item_home_text_price);
            btnBook = view.findViewById(R.id.item_home_btn_book);
            btnDetails = view.findViewById(R.id.item_home_btn_details);
            textDistance = view.findViewById(R.id.item_home_text_distance);
            btnBook.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorYellow));
        }
    }
}
