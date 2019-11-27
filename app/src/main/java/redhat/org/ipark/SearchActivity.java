package redhat.org.ipark;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import redhat.org.ipark.extras.KeyboardVisibilityListener;
import redhat.org.ipark.extras.Utils;

public class SearchActivity extends AppCompatActivity implements KeyboardVisibilityListener {

    public enum DateType {
        StartTime, EndTime, StartParkingOn
    }

    static final String DATETIME_FORMAT = "EEE, MMM dd h:mm a";
    static final String DATE_FORMAT = "EEE, MMM dd";
    static final long ONE_MINUTE_IN_MILLIS = 60000;

    private DateType selectedDateType;

    private Date dateStartTime;
    private Date dateEndTime;
    private Date dateStartParkingOn;

    @BindView(R.id.search_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.search_scrollView)
    ScrollView scrollView;
    @BindView(R.id.search_btn_search)
    MaterialButton btnSearch;

    // Daily tab
    @BindView(R.id.search_layout_daily)
    LinearLayout layoutDaily;
    @BindView(R.id.search_inputLayout_address)
    TextInputLayout inputLayoutAddress;
    @BindView(R.id.search_inputLayout_start)
    TextInputLayout inputLayoutStartTime;
    @BindView(R.id.search_inputLayout_end)
    TextInputLayout inputLayoutEndTime;
    @BindView(R.id.search_edit_address)
    TextInputEditText editAddress;
    @BindView(R.id.search_edit_start_time)
    TextInputEditText editStartTime;
    @BindView(R.id.search_edit_end_time)
    TextInputEditText editEndTime;

    // Monthly tab
    @BindView(R.id.search_layout_monthly)
    LinearLayout layoutMonthly;
    @BindView(R.id.search_inputLayout_address_monthly)
    TextInputLayout inputLayoutAddressMonthly;
    @BindView(R.id.search_inputLayout_start_parking)
    TextInputLayout inputLayoutStartParking;
    @BindView(R.id.search_edit_address_monthly)
    TextInputEditText editAddressMonthly;
    @BindView(R.id.search_edit_start_parking)
    TextInputEditText editStartParking;

    @OnClick(R.id.search_edit_start_time)
    public void onClickStartTime(TextInputEditText editText) {
        showDateTimePicker(DateType.StartTime, editText);
    }

    @OnClick(R.id.search_edit_end_time)
    public void onClickEndTime(TextInputEditText editText) {
        showDateTimePicker(DateType.EndTime, editText);
    }

    @OnClick(R.id.search_edit_start_parking)
    public void onClickStartParkingOn(TextInputEditText editText) {
        showDateTimePicker(DateType.StartParkingOn, editText);
    }

    @OnClick(R.id.search_btn_search)
    public void onClickSearch(MaterialButton button) {

    }

    @OnTouch(R.id.search_scrollView)
    public boolean onTouchScrollView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            Utils.hideKeyboard(this, view);
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initialize();
        Utils.setKeyboardVisibilityListener(this, this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
    }

    private void initialize() {
        btnSearch.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    layoutDaily.setVisibility(View.VISIBLE);
                    layoutMonthly.setVisibility(View.GONE);
                } else {
                    layoutMonthly.setVisibility(View.VISIBLE);
                    layoutDaily.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onKeyboardVisibilityChanged(boolean keyboardVisible) {
        if (keyboardVisible) {
            btnSearch.setVisibility(View.GONE);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnSearch.setVisibility(View.VISIBLE);
                }
            }, 300);
        }
    }

    private void showDateTimePicker(DateType dateType, TextInputEditText editText) {
        Utils.hideKeyboard(this, editText);
        selectedDateType = dateType;

        Date minDate = Calendar.getInstance().getTime();
        Date selectedDate = Calendar.getInstance().getTime();

        switch (dateType) {
            case StartTime:
                if (dateStartTime != null) {
                    selectedDate = dateStartTime;
                }
                break;
            case EndTime:
                if (dateEndTime != null) {
                    selectedDate = dateEndTime;
                } else if (dateStartTime != null) {
                    selectedDate = offsetDate(dateStartTime, 30);
                } else {
                    selectedDate = offsetDate(selectedDate, 30);
                }

                if (dateStartTime != null) {
                    minDate = offsetDate(dateStartTime, 30);
                } else {
                    minDate = offsetDate(minDate, 30);
                }
                break;
            case StartParkingOn:
                if (dateStartParkingOn != null) {
                    selectedDate = dateStartParkingOn;
                }
        }

        Date finalSelectedDate = selectedDate;

        if (dateType == DateType.StartParkingOn) {
            new SingleDateAndTimePickerDialog.Builder(this)
                    .bottomSheet()
                    .curved()
                    .minDateRange(minDate)
                    .displayHours(false)
                    .displayMinutes(false)
                    .displayDays(false)
                    .displayMonth(true)
                    .displayYears(true)
                    .displayDaysOfMonth(true)
                    .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                        @Override
                        public void onDisplayed(SingleDateAndTimePicker picker) {
                            picker.selectDate(toCalendar(finalSelectedDate));
                        }
                    })
                    .listener(new SingleDateAndTimePickerDialog.Listener() {
                        @Override
                        public void onDateSelected(Date date) {
                            dateStartParkingOn = date;
                            editText.setText(getStringFromDate(date, DATE_FORMAT));

                        }
                    }).display();
        } else {
            new SingleDateAndTimePickerDialog.Builder(this)
                    .bottomSheet()
                    .curved()
                    .minutesStep(5)
                    .displayAmPm(true)
                    .minDateRange(minDate)
                    .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                        @Override
                        public void onDisplayed(SingleDateAndTimePicker picker) {
                            picker.selectDate(toCalendar(finalSelectedDate));
                        }
                    })
                    .listener(new SingleDateAndTimePickerDialog.Listener() {
                        @Override
                        public void onDateSelected(Date date) {
                            switch (selectedDateType) {
                                case StartTime:
                                    dateStartTime = date;
                                    break;
                                case EndTime:
                                    dateEndTime = date;
                                    break;
                            }
                            editText.setText(getStringFromDate(date, DATETIME_FORMAT));

                        }
                    }).display();
        }
    }

    private Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private Date offsetDate(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long t = cal.getTimeInMillis();
        Date afterAddingMinutes = new Date(t + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMinutes;
    }

    private String getStringFromDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
