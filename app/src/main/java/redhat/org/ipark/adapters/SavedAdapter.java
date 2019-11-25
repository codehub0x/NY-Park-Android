package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import redhat.org.ipark.R;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    private Context mContext;

    public SavedAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Integrate the reservations data


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView textName;
        private ImageView imageStar;
        private TextView textAddress;
        private TextView textTitle;
        private TextView textPrice;
        private MaterialButton btnBook;
        private MaterialButton btnDetails;
        private TextView textDistance;

        public ViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.item_saved_imageView);
            textName = view.findViewById(R.id.item_saved_text_name);
            imageStar = view.findViewById(R.id.item_saved_image_star);
            textAddress = view.findViewById(R.id.item_saved_text_address);
            textTitle = view.findViewById(R.id.item_saved_text_title);
            textPrice = view.findViewById(R.id.item_saved_text_price);
            btnBook = view.findViewById(R.id.item_saved_btn_book);
            btnDetails = view.findViewById(R.id.item_saved_btn_details);
            textDistance = view.findViewById(R.id.item_saved_text_distance);
            btnBook.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorYellow));
        }
    }
}
