package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import redhat.org.ipark.R;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    public interface ClickListener {
        void onClickEdit(int position);
    }

    private Context mContext;
    private ClickListener mListener;

    public VehicleAdapter(Context context, ClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Integrate real data

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickEdit(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textMake;
        private TextView textModel;
        private TextView textInfo;
        private MaterialButton btnEdit;

        public ViewHolder(View view) {
            super(view);

            textMake = view.findViewById(R.id.item_vehicle_text_make);
            textModel = view.findViewById(R.id.item_vehicle_text_model);
            textInfo = view.findViewById(R.id.item_vehicle_text_info);
            btnEdit = view.findViewById(R.id.item_vehicle_btn_edit);
        }
    }
}
