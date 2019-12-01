package redhat.org.ipark.ui.home;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import redhat.org.ipark.BookActivity;
import redhat.org.ipark.DetailsActivity;
import redhat.org.ipark.R;
import redhat.org.ipark.SavedActivity;
import redhat.org.ipark.SearchActivity;
import redhat.org.ipark.adapters.HomeBottomAdapter;
import redhat.org.ipark.models.SearchResult;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = HomeFragment.class.getSimpleName();

    static final int SEARCH_REQUEST_CODE = 10001;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int LOCATION_REQUEST_CODE = 1101;
    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private boolean isShownList;
    private boolean isShownFilter;
    private boolean isShownBottom;
    private boolean isShownMarkerInfo;

    private HomeBottomAdapter bottomAdapter;
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private PlacesClient mPlacesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    @BindView(R.id.home_map_view)
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
    @BindView(R.id.home_marker_info_layout)
    LinearLayout markerInfoLayout;

    @BindView(R.id.home_bottom_recyclerView)
    RecyclerView bottomRecyclerView;

    @BindView(R.id.home_btn_toggle)
    MaterialButton btnToggle;
    @BindView(R.id.home_btn_filters)
    MaterialButton btnFilters;
    @BindView(R.id.home_btn_apply_filters)
    MaterialButton btnFilterApply;
    @BindView(R.id.home_image_arrow)
    ImageView imageArrow;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        // Retrieve location and camera position from saved instance state.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        if (!Places.isInitialized()) {
            Places.initialize(getContext(), getString(R.string.google_maps_key));
        }
        mPlacesClient = Places.createClient(getContext());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        initialize();
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);

            Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
            if (mapViewBundle == null) {
                mapViewBundle = new Bundle();
                outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
            }

            mapView.onSaveInstanceState(mapViewBundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        mapView.onPause();
        updateMarkerInfoLayout(false, false);
        updateBottomLayout(false, false);
        updateFiltersLayout(false, false);
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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

        initMarkerInfoLayout();
    }

    private void initMarkerInfoLayout() {
        View infoView = getActivity().getLayoutInflater().inflate(R.layout.item_home, null);
        MaterialButton btnBook = infoView.findViewById(R.id.item_home_btn_book);
        MaterialButton btnDetails = infoView.findViewById(R.id.item_home_btn_details);
        btnBook.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorYellow));

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.nothing);

                updateMarkerInfoLayout(false, false);
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.nothing);

                updateMarkerInfoLayout(false, false);
            }
        });

        markerInfoLayout.addView(infoView);
        markerInfoLayout.setVisibility(View.GONE);
    }

    private void updateMarkerInfoLayout(boolean isShown, boolean animate) {
        isShownMarkerInfo = isShown;
        if (isShownMarkerInfo) {
            if (animate) {
                markerInfoLayout.setVisibility(View.VISIBLE);
                markerInfoLayout.setAlpha(0.0f);

                markerInfoLayout.animate()
                        .alpha(1.0f)
                        .setDuration(300)
                        .setListener(null);
            } else {
                markerInfoLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (animate) {
                markerInfoLayout.setVisibility(View.VISIBLE);
                markerInfoLayout.setAlpha(1.0f);

                markerInfoLayout.animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                markerInfoLayout.setVisibility(View.GONE);
                            }
                        });
            } else {
                markerInfoLayout.setVisibility(View.GONE);
            }
        }
    }

    private void updateBottomLayout(boolean isShwon, boolean animate) {
        isShownBottom = isShwon;
        if (isShownBottom) {
            bottomLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up));
            imageArrow.startAnimation(animate(false));
            bottomBookedLayout.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                imageArrow.startAnimation(animate(true));
                bottomBookedLayout.setVisibility(View.GONE);
            } else {
                imageArrow.setImageResource(R.drawable.ic_arrow_up_black_24dp);
                bottomBookedLayout.setVisibility(View.GONE);
            }
        }
    }

    private void updateFiltersLayout(boolean isShown, boolean animate) {
        isShownFilter = isShown;
        if (isShownFilter) {
            filterView.setVisibility(View.VISIBLE);
            filterView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left));
            btnFilters.setText(R.string.close);
        } else {
            if (animate) {
                filterView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right));
                btnFilters.setText(R.string.filters);
                filterView.setVisibility(View.INVISIBLE);
            } else {
                btnFilters.setText(R.string.filters);
                filterView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private Animation animate(boolean up) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), up ? R.anim.rotate_up : R.anim.rotate_down);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }

    @OnClick(R.id.home_btn_filters)
    public void onClickFilters(View view) {
        isShownFilter = !isShownFilter;
        updateFiltersLayout(isShownFilter, true);
        updateBottomLayout(false, false);
        updateMarkerInfoLayout(false, false);
    }

    @OnClick(R.id.home_btn_apply_filters)
    public void onClickApplyFilters(View view) {
        updateFiltersLayout(false, true);
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
        updateFiltersLayout(false, false);
        updateBottomLayout(false, false);
        updateMarkerInfoLayout(false, false);
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
        startActivityForResult(intent, SEARCH_REQUEST_CODE);
        getActivity().overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @OnClick(R.id.home_layout_timer)
    public void onClickTimer(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivityForResult(intent, SEARCH_REQUEST_CODE);
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
        isShownBottom = !isShownBottom;
        updateBottomLayout(isShownBottom, true);
        updateMarkerInfoLayout(false, false);
        updateFiltersLayout(false, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Prompt the user for permission.
        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        addMarker(mDefaultLocation, "$35");
        addMarker(new LatLng(41.8057, 123.4315), "$14");
        addMarker(new LatLng(41.8156, 123.4014), "$25");
        addMarker(new LatLng(41.8355, 123.4213), "$30");
        addMarker(new LatLng(41.8254, 123.4412), "$15");

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                updateMarkerInfoLayout(true, true);
                updateBottomLayout(false, false);
                updateFiltersLayout(false, false);
                return true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                updateMarkerInfoLayout(false, false);
                updateBottomLayout(false, false);
                updateFiltersLayout(false, false);
            }
        });
    }

    private void getLocationPermission() {
        if (Build.VERSION.SDK_INT < 23 || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, LOCATION_REQUEST_CODE);
            mLocationPermissionGranted = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    //Permission Granted
                    mLocationPermissionGranted = true;
                } else {
                    Toast.makeText(getContext(), "Location Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        updateLocationUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                SearchResult searchResult = data.getParcelableExtra("searchResult");

                int type = searchResult.getType(); // 0: daily search, 1: monthly search
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(searchResult.getLatitude(), searchResult.getLongitude()),
                        DEFAULT_ZOOM));
            }
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.setIndoorEnabled(true);
                mMap.setMinZoomPreference(10);

                UiSettings uiSettings = mMap.getUiSettings();
                uiSettings.setIndoorLevelPickerEnabled(true);
                uiSettings.setMyLocationButtonEnabled(true);
                uiSettings.setMapToolbarEnabled(true);
                uiSettings.setCompassEnabled(true);
                uiSettings.setZoomControlsEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void addMarker(LatLng ly, String text) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ly);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createMapMarker(text)));
        mMap.addMarker(markerOptions);
    }

    private Bitmap createMapMarker(String text) {
        View markerLayout = getActivity().getLayoutInflater().inflate(R.layout.marker_layout, null);
        TextView markerText = markerLayout.findViewById(R.id.marker_image_text);
        markerText.setText(text);

        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        markerLayout.layout(0, 0, markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight());

        final Bitmap bitmap = Bitmap.createBitmap(markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        markerLayout.draw(canvas);

        return bitmap;
    }
}