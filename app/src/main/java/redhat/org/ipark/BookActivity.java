package redhat.org.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import redhat.org.ipark.adapters.BookAdapter;
import redhat.org.ipark.models.BookItem;

public class BookActivity extends AppCompatActivity {

    static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    private BookAdapter mAdapter;
    private List<BookItem> data = new ArrayList<>();

    @BindView(R.id.book_text_time)
    TextView textTime;
    @BindView(R.id.book_text_price)
    TextView textPrice;
    @BindView(R.id.book_text_vehicle)
    TextView textVehicle;
    @BindView(R.id.book_text_payment)
    TextView textPayment;
    @BindView(R.id.book_switch_book_multiple)
    Switch switchMultiple;
    @BindView(R.id.book_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.book_text_total)
    TextView textTotal;
    @BindView(R.id.book_text_total_price)
    TextView textTotalPrice;
    @BindView(R.id.book_btn_add_payment)
    MaterialButton btnAddPayment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book);
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
        btnAddPayment.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

        Date date = Calendar.getInstance().getTime();
        for (int i = 0; i < 7; i ++) {
            boolean valid = true;
            if ( i == 3) {
                valid = false;
            }
            data.add(new BookItem(offsetDate(date, i), "$11.95", valid));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new BookAdapter(this, data, new BookAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!switchMultiple.isChecked()) {
                    for (int k = 0; k < data.size(); k ++) {
                        if (k == position) {
                            data.get(k).setSelected(!data.get(k).isSelected());
                        } else {
                            data.get(k).setSelected(false);
                        }
                    }

                    mAdapter.setList(data);
                } else {
                    data.get(position).setSelected(!data.get(position).isSelected());
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }

    private Date offsetDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long t = cal.getTimeInMillis();
        Date afterAddingMinutes = new Date(t + (days * ONE_DAY_IN_MILLIS));
        return afterAddingMinutes;
    }

    @OnClick(R.id.book_layout_vehicle)
    public void onClickVehicle(View view) {
        Intent intent = new Intent(BookActivity.this, VehiclesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.book_layout_payment)
    public void onClickPayment(View view) {
        Intent intent = new Intent(BookActivity.this, BillingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.book_layout_promo_code)
    public void onClickPromoCode(View view) {

    }

    @OnClick(R.id.book_btn_add_payment)
    public void onClickAddPayment(View view) {

    }
}
