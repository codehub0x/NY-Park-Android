package redhat.org.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.details_text_enter_time)
    TextView textStartTime;
    @BindView(R.id.details_text_exit_time)
    TextView textEndTime;
    @BindView(R.id.details_text_price)
    TextView textPrice;
    @BindView(R.id.details_text_stay_period)
    TextView textStayPeriod;
    @BindView(R.id.details_text_distance)
    TextView textDistance;
    @BindView(R.id.details_segmented_cartype)
    SegmentedButtonGroup segmentedCarType;
    @BindView(R.id.details_segmented_view_type)
    SegmentedButtonGroup segmentedViewType;
    @BindView(R.id.details_text_suv)
    TextView textSUV;
    @BindView(R.id.details_text_tax)
    TextView textTax;
    @BindView(R.id.details_btn_book)
    MaterialButton btnBook;
    @BindView(R.id.details_btn_help)
    MaterialButton btnHelp;

    // Info view
    @BindView(R.id.details_layout_info)
    LinearLayout layoutInfo;
    @BindView(R.id.details_text_hours_of_operation)
    TextView textHoursOfOperation;
    @BindView(R.id.details_text_address)
    TextView textAddress;
    @BindView(R.id.details_text_phonenumber)
    TextView textPhoneNumber;
    @BindView(R.id.details_btn_directions)
    MaterialButton btnDirections;

    // Rates View
    @BindView(R.id.details_layout_rates)
    LinearLayout layoutRates;

    // Specials View
    @BindView(R.id.details_layout_specials)
    LinearLayout layoutSpecials;

    // Actions
    @OnClick(R.id.details_btn_book)
    public void onClickBook(MaterialButton button) {
        Intent intent = new Intent(DetailsActivity.this, BookActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.details_btn_help)
    public void onClickHelp(MaterialButton button) {

    }

    @OnClick(R.id.details_btn_directions)
    public void onClickDirections(MaterialButton button) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
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
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView textHeader = view.findViewById(R.id.header_text_header);
        TextView textSubHeader = view.findViewById(R.id.header_text_subheader);
        textHeader.setText(header);
        textSubHeader.setText(subHeader);
    }

    private void initialize() {
        btnBook.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

        segmentedCarType.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {

            }
        });

        segmentedViewType.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                switch (position) {
                    case 0:
                        layoutInfo.setVisibility(View.VISIBLE);
                        layoutRates.setVisibility(View.GONE);
                        layoutSpecials.setVisibility(View.GONE);
                        break;
                    case 1:
                        layoutInfo.setVisibility(View.GONE);
                        layoutRates.setVisibility(View.VISIBLE);
                        layoutSpecials.setVisibility(View.GONE);
                        break;
                    case 2:
                        layoutInfo.setVisibility(View.GONE);
                        layoutRates.setVisibility(View.GONE);
                        layoutSpecials.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }
}
