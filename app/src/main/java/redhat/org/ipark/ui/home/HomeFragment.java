package redhat.org.ipark.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.MapView;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import redhat.org.ipark.DetailsActivity;
import redhat.org.ipark.R;
import redhat.org.ipark.SavedActivity;
import redhat.org.ipark.SearchActivity;
import redhat.org.ipark.adapters.HomeBottomAdapter;

public class HomeFragment extends Fragment {

    static final int SEARCH_REQUEST = 10001;

    private boolean isShownList;
    private boolean isShownFilter;
    private boolean isShownBottom;

    private HomeBottomAdapter bottomAdapter;

    @BindView(R.id.home_mapView)
    MapView mapView;
    @BindView(R.id.home_layout_bottom)
    LinearLayout bottomLayout;
    @BindView(R.id.home_layout_bottom_booked)
    ConstraintLayout bottomBookedLayout;
    @BindView(R.id.home_layout_filter)
    LinearLayout filterView;
    @BindView(R.id.home_checkbox_24hours)
    CheckBox checkBox24Hours;
    @BindView(R.id.home_checkbox_7days)
    CheckBox checkBox7Days;
    @BindView(R.id.home_checkbox_covered)
    CheckBox checkBoxCovered;
    @BindView(R.id.home_checkbox_paved)
    CheckBox checkBoxPaved;
    @BindView(R.id.home_checkbox_valet)
    CheckBox checkBoxValet;
    @BindView(R.id.home_checkbox_oversized)
    CheckBox checkBoxOversized;
    @BindView(R.id.home_checkbox_green)
    CheckBox checkBoxGreen;
    @BindView(R.id.home_checkbox_tesla)
    CheckBox checkBoxTesla;
    @BindView(R.id.home_checkbox_outdoors)
    CheckBox checkBoxOutdoors;
    @BindView(R.id.home_checkbox_onsite_staff)
    CheckBox checkBoxOnsite;

    @BindView(R.id.home_bottom_recyclerView)
    RecyclerView bottomRecyclerView;

    @BindView(R.id.home_btn_toggle)
    MaterialButton btnToggle;
    @BindView(R.id.home_btn_filters)
    MaterialButton btnFilters;
    @BindView(R.id.home_btn_apply_filters)
    MaterialButton btnFilterApply;
    @BindView(R.id.home_btn_bottom_arrow)
    LinearLayout btnBottomArrow;
    @BindView(R.id.home_image_arrow)
    ImageView imageArrow;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        initialize();
        return root;
    }

    private void initialize() {
        filterView.setVisibility(View.INVISIBLE);
        bottomBookedLayout.setVisibility(View.GONE);
        btnFilters.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorYellow));
        btnFilterApply.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorYellow));

        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bottomAdapter = new HomeBottomAdapter(getContext(), new HomeBottomAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            }
        });
        bottomRecyclerView.setAdapter(bottomAdapter);
    }

    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), up ? R.anim.rotate_up : R.anim.rotate_down);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }

    @OnClick(R.id.home_btn_filters)
    public void onClickFilters(MaterialButton button) {
        if (isShownFilter) {
            filterView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right));
            button.setText(R.string.filters);
            filterView.setVisibility(View.INVISIBLE);
        } else {
            filterView.setVisibility(View.VISIBLE);
            filterView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left));
            button.setText(R.string.close);
        }
        isShownFilter = !isShownFilter;
    }

    @OnClick(R.id.home_btn_apply_filters)
    public void onClickApplyFilters(View view) {
        filterView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right));
        btnFilters.setText(R.string.filters);
        filterView.setVisibility(View.INVISIBLE);
        isShownFilter = false;
    }

    @OnClick(R.id.home_btn_filter_clear)
    public void onClickClearFilters(View view) {
        checkBox24Hours.setChecked(false);
        checkBox7Days.setChecked(false);
        checkBoxCovered.setChecked(false);
        checkBoxPaved.setChecked(false);
        checkBoxValet.setChecked(false);
        checkBoxOversized.setChecked(false);
        checkBoxGreen.setChecked(false);
        checkBoxTesla.setChecked(false);
        checkBoxOutdoors.setChecked(false);
        checkBoxOnsite.setChecked(false);
    }

    @OnClick(R.id.home_btn_toggle)
    public void onClickToggle(View view) {
        if (isShownList) {
            getChildFragmentManager().popBackStack();
            isShownList = false;
            btnToggle.setText(R.string.list);
            return;
        }

        isShownList = true;
        btnToggle.setText(R.string.map);
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.flip_right_in, R.animator.flip_right_out,
                        R.animator.flip_left_in, R.animator.flip_left_out)
                .replace(R.id.home_fragment_container, new HomeListFragment())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.home_btn_search)
    public void onClickSearch(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivityForResult(intent, SEARCH_REQUEST);
        getActivity().overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @OnClick(R.id.home_layout_timer)
    public void onClickTimer(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivityForResult(intent, SEARCH_REQUEST);
        getActivity().overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @OnClick(R.id.home_btn_favorite)
    public void onClickFavorite(View view) {
        Intent intent = new Intent(getActivity(), SavedActivity.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.home_btn_bottom_arrow)
    public void onClickDownArrow(View view) {
        if (isShownBottom) {
            imageArrow.startAnimation(animate(true));
            bottomBookedLayout.setVisibility(View.GONE);
        } else {
            bottomLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up));
            imageArrow.startAnimation(animate(false));
            bottomBookedLayout.setVisibility(View.VISIBLE);
        }
        isShownBottom = !isShownBottom;
    }
}