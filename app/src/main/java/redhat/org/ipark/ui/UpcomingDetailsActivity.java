package redhat.org.ipark.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import redhat.org.ipark.R;
import redhat.org.ipark.adapters.DotsIndicatorPagerAdapter;
import redhat.org.ipark.components.ImageViewer.ImageViewer;

public class UpcomingDetailsActivity extends AppCompatActivity {

    private List<String> images = new ArrayList<>();
    private DotsIndicatorPagerAdapter pagerAdapter;

    @BindView(R.id.upcoming_text_enter_time)
    TextView textStartTime;
    @BindView(R.id.upcoming_text_exit_time)
    TextView textEndTime;
    @BindView(R.id.upcoming_text_price)
    TextView textPrice;
    @BindView(R.id.upcoming_text_stay_period)
    TextView textStayPeriod;
    @BindView(R.id.upcoming_text_distance)
    TextView textDistance;
    @BindView(R.id.upcoming_btn_add_to_calendar)
    MaterialButton btnAddToCalendar;
    @BindView(R.id.upcoming_btn_directions)
    MaterialButton btnDirections;
    @BindView(R.id.upcoming_btn_cancel_reservation)
    MaterialButton btnCancelReservation;
    @BindView(R.id.upcoming_btn_paid)
    MaterialButton btnPaid;

    // ViewPager & DotsIndicator
    @BindView(R.id.upcoming_viewPager)
    ViewPager viewPager;
    @BindView(R.id.upcoming_dotsIndicator)
    SpringDotsIndicator dotsIndicator;

    // Info view
    @BindView(R.id.upcoming_layout_info)
    LinearLayout layoutInfo;
    @BindView(R.id.upcoming_text_vehicle)
    TextView textVehicle;
    @BindView(R.id.upcoming_text_hours_of_operation)
    TextView textHoursOfOperation;
    @BindView(R.id.upcoming_text_address)
    TextView textAddress;
    @BindView(R.id.upcoming_text_phonenumber)
    TextView textPhoneNumber;
    @BindView(R.id.upcoming_text_price1)
    TextView textPrice1;
    @BindView(R.id.upcoming_text_tax)
    TextView textTax;
    @BindView(R.id.upcoming_text_total)
    TextView textTotal;
    @BindView(R.id.upcoming_image_qrcode)
    ImageView imageQRCode;
    @BindView(R.id.upcoming_image_barcode)
    ImageView imageBarCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upcoming_details);
        ButterKnife.bind(this);

        initializeCustomActionBar("West 90TH Garage Corp.", "7 East 14th Street, New York, NY 10003 Between 2nd Ave. and 3rd Ave.");
        initialize();

    }

    private void initializeCustomActionBar(String header, String subHeader) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.header_two_lines);

        View view = actionBar.getCustomView();
        Toolbar toolbar = (Toolbar) view.getParent();
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);

        ImageButton btnBack = view.findViewById(R.id.header_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
            }
        });

        ImageButton btnFavorite = view.findViewById(R.id.header_btn_favorite);
        btnFavorite.setVisibility(View.GONE);

        TextView textHeader = view.findViewById(R.id.header_text_header);
        TextView textSubHeader = view.findViewById(R.id.header_text_subheader);
        textHeader.setText(header);
        textSubHeader.setText(subHeader);
    }

    private void initialize() {
        btnAddToCalendar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        btnDirections.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

        // Define ViewPager & DotsIndicator
        images.add("https://ipark.com/wp-content/uploads/2018/02/276-2-e1537392870275.jpg");
        images.add("https://ipark.com/wp-content/uploads/2017/01/1-700x525.jpg");
        images.add("https://ipark.com/wp-content/uploads/2017/01/48-North-web-700x525.jpg");
        images.add("https://ipark.com/wp-content/uploads/2017/01/IMG_0128-web.jpg");
        images.add("https://ipark.com/wp-content/uploads/2017/01/31-700x525.jpg");

        pagerAdapter = new DotsIndicatorPagerAdapter(this, images);
        pagerAdapter.setOnClickListener(new DotsIndicatorPagerAdapter.ClickListener() {
            @Override
            public void onClickItem(int position) {
                new ImageViewer.Builder<>(UpcomingDetailsActivity.this, images)
                        .setStartPosition(position)
                        .show();
            }
        });

        viewPager.setAdapter(pagerAdapter);
        dotsIndicator.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }

    // Actions
    @OnClick(R.id.upcoming_btn_add_to_calendar)
    public void onClickAddToCalendar(View view) {

    }

    @OnClick(R.id.upcoming_btn_directions)
    public void onClickDirections(View view) {
        LatLng destLatlng = new LatLng(41.8057, 123.4315);
        String urlStr = "http://maps.google.com/maps?daddr=" + destLatlng.latitude + "," + destLatlng.longitude;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
        startActivity(browserIntent);
    }

    @OnClick(R.id.upcoming_btn_cancel_reservation)
    public void onClickCancelReservation(View view) {

    }

    @OnClick(R.id.upcoming_btn_paid)
    public void onClickPaid(View view) {

    }
}
