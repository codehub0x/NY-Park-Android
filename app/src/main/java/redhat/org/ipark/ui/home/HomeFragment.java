package redhat.org.ipark.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.MapView;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import redhat.org.ipark.R;

public class HomeFragment extends Fragment {

    private boolean isShownFilter;
    private boolean isShownBottom;

    @BindView(R.id.home_mapView)
    MapView mapView;
    @BindView(R.id.home_layout_bottom)
    LinearLayout bottomLayout;
    @BindView(R.id.home_layout_bottom_booked)
    ConstraintLayout bottomBookedLayout;
    @BindView(R.id.home_layout_filter)
    ScrollView filterView;

    @BindView(R.id.home_btn_filters)
    MaterialButton btnFilters;
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

    }

    @OnClick(R.id.home_btn_search)
    public void onClickSearch(MaterialButton button) {

    }

    @OnClick(R.id.home_btn_favorite)
    public void onClickFavorite(MaterialButton button) {

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
    }

    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), up ? R.anim.rotate_up : R.anim.rotate_down);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }

}