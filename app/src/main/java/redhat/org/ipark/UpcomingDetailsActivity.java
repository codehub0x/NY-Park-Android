package redhat.org.ipark;

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

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class UpcomingDetailsActivity extends AppCompatActivity {
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

    // Actions
    @OnClick(R.id.upcoming_btn_add_to_calendar)
    public void onClickAddToCalendar(MaterialButton button) {

    }

    @OnClick(R.id.upcoming_btn_directions)
    public void onClickDirections(MaterialButton button) {

    }

    @OnClick(R.id.upcoming_btn_cancel_reservation)
    public void onClickCancelReservation(MaterialButton button) {

    }

    @OnClick(R.id.upcoming_btn_paid)
    public void onClickPaid(MaterialButton button) {

    }

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }
}
