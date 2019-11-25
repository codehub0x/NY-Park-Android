package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import redhat.org.ipark.R;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder> implements View.OnClickListener {

    public interface ClickListener {
        void onDirectionsClicked(int position);
        void onReBookClicked(int position);
        void onDetailsClicked(int position);
        void onUpcomingDetailsClicked(int position);
    }

    public enum ReservationsType {
        UPCOMING, PAST, CANCELED
    }

    private ReservationsType mType = ReservationsType.UPCOMING;
    private Context mContext;
    private ClickListener mListener;

    public ReservationsAdapter(Context context, ClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Integrate the reservations data
        switch (mType) {
            case UPCOMING:
                holder.btnDirections.setVisibility(View.VISIBLE);
                holder.btnRebook.setVisibility(View.INVISIBLE);
                holder.layoutPrice.setBackgroundResource(R.drawable.corner_background_yellow);
                holder.textPrice.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                holder.textCompleted.setVisibility(View.GONE);
                break;
            case PAST:
                holder.btnDirections.setVisibility(View.INVISIBLE);
                holder.btnRebook.setVisibility(View.VISIBLE);
                holder.layoutPrice.setBackgroundResource(R.drawable.corner_background_blue);
                holder.textPrice.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                holder.textCompleted.setVisibility(View.VISIBLE);
                break;
            case CANCELED:
                holder.btnDirections.setVisibility(View.VISIBLE);
                holder.btnRebook.setVisibility(View.INVISIBLE);
                holder.layoutPrice.setBackgroundResource(R.drawable.corner_background_yellow);
                holder.textPrice.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                holder.textCompleted.setVisibility(View.GONE);
                break;
        }

        holder.btnDirections.setTag(position);
        holder.btnDirections.setOnClickListener(this);

        holder.btnRebook.setTag(position);
        holder.btnRebook.setOnClickListener(this);

        holder.btnDetails.setTag(position);
        holder.btnDetails.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setType(ReservationsType type) {
        this.mType = type;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_reservations_btn_directions:
                mListener.onDirectionsClicked((Integer) view.getTag());
                break;
            case R.id.item_reservations_btn_rebook:
                mListener.onReBookClicked((Integer) view.getTag());
                break;
            case R.id.item_reservations_btn_details:
                if (mType == ReservationsType.UPCOMING) {
                    mListener.onUpcomingDetailsClicked((Integer) view.getTag());
                } else {
                    mListener.onDetailsClicked((Integer) view.getTag());
                }
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView textLicense;
        private TextView textAddress;
        private TextView textTitle;
        private TextView textStartTime;
        private TextView textEndTime;
        private LinearLayout layoutPrice;
        private TextView textPrice;
        private TextView textCompleted;
        private MaterialButton btnDirections;
        private MaterialButton btnRebook;
        private MaterialButton btnDetails;
        private TextView textDistance;

        public ViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.item_reservations_imageView);
            textLicense = view.findViewById(R.id.item_reservations_text_license);
            textAddress = view.findViewById(R.id.item_reservations_text_address);
            textTitle = view.findViewById(R.id.item_reservations_text_title);
            textStartTime = view.findViewById(R.id.item_reservations_text_enter_time);
            textEndTime = view.findViewById(R.id.item_reservations_text_exit_time);
            layoutPrice = view.findViewById(R.id.item_reservations_layout_price);
            textPrice = view.findViewById(R.id.item_reservations_text_price);
            textCompleted = view.findViewById(R.id.item_reservations_text_completed);
            btnDirections = view.findViewById(R.id.item_reservations_btn_directions);
            btnRebook = view.findViewById(R.id.item_reservations_btn_rebook);
            btnDetails = view.findViewById(R.id.item_reservations_btn_details);
            textDistance = view.findViewById(R.id.item_reservations_text_distance);
            btnDirections.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
            btnRebook.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorYellow));
        }
    }
}
