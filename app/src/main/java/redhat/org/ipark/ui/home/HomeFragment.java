package redhat.org.ipark.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
    ScrollView filterView;
    @BindView(R.id.home_bottom_recyclerView)
    RecyclerView bottomRecyclerView;

    @BindView(R.id.home_btn_filters)
    MaterialButton btnFilters;
    @BindView(R.id.home_btn_apply_filters)
    MaterialButton btnFilterApply;
    @BindView(R.id.home_btn_bottom_arrow)
    LinearLayout btnBottomArrow;
    @BindView(R.id.home_image_arrow)
    ImageView imageArrow;

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

    @OnClick(R.id.home_btn_toggle)
    public void onClickToggle(MaterialButton button) {
        if (isShownList) {
            getChildFragmentManager().popBackStack();
            isShownList = false;
            return;
        }

        isShownList = true;

        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.flip_right_in, R.animator.flip_right_out,
                        R.animator.flip_left_in, R.animator.flip_left_out)
                .replace(R.id.home_fragment_container, new HomeListFragment())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.home_btn_search)
    public void onClickSearch(MaterialButton button) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivityForResult(intent, SEARCH_REQUEST);
        getActivity().overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @OnClick(R.id.home_layout_timer)
    public void onClickTimer(RelativeLayout layout) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivityForResult(intent, SEARCH_REQUEST);
        getActivity().overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @OnClick(R.id.home_btn_favorite)
    public void onClickFavorite(MaterialButton button) {
        Intent intent = new Intent(getActivity(), SavedActivity.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @OnClick(R.id.home_btn_bottom_arrow)
    public void onClickDownArrow(LinearLayout button) {
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

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
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
        bottomAdapter = new HomeBottomAdapter();
        bottomRecyclerView.setAdapter(bottomAdapter);
    }

    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), up ? R.anim.rotate_up : R.anim.rotate_down);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }

}