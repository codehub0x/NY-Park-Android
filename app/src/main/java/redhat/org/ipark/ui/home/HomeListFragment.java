package redhat.org.ipark.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import redhat.org.ipark.R;
import redhat.org.ipark.adapters.HomeAdapter;

public class HomeListFragment extends Fragment {

    private HomeAdapter homeAdapter;
    private boolean isSortByPrice;

    @BindView(R.id.home_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.home_list_btn_sort_distance)
    MaterialButton btnSortByDistance;
    @BindView(R.id.home_list_btn_sort_price)
    MaterialButton btnSortByPrice;

    @OnClick(R.id.home_list_btn_sort_distance)
    public void onClickDistance(MaterialButton btn) {
        if (!isSortByPrice)
            return;
        isSortByPrice = false;
        btnSortByDistance.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
        btnSortByPrice.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.colorBlack60));

        // TODO: Update the recycler view
    }

    @OnClick(R.id.home_list_btn_sort_price)
    public void onClickPrice(MaterialButton btn) {
        if (isSortByPrice)
            return;
        isSortByPrice = true;
        btnSortByDistance.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.colorBlack60));
        btnSortByPrice.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));

        // TODO: Update the recycler view
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_list, container, false);
        ButterKnife.bind(this, root);

        initialize();
        return root;
    }

    private void initialize() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);
    }
}
