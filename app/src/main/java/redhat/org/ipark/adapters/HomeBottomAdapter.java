package redhat.org.ipark.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import redhat.org.ipark.R;

public class HomeBottomAdapter extends RecyclerView.Adapter<HomeBottomAdapter.ViewHolder> implements View.OnClickListener {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_bottom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View view) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView textAddress;
        private TextView textBook;

        public ViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.item_home_bottom_imageView);
            textAddress = view.findViewById(R.id.item_home_bottom_text_address);
            textBook = view.findViewById(R.id.item_home_bottom_text_book);
        }
    }
}
