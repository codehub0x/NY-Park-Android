package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import redhat.org.ipark.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;

    public HomeAdapter(Context context) {
        this.mContext = context;
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
        

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    int dpToPx(@Dimension(unit = 0) int dps) {
        return Math.round(mContext.getResources().getDisplayMetrics().density * (float)dps);
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
