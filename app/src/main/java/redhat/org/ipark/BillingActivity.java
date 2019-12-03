package redhat.org.ipark;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import redhat.org.ipark.extras.CreditCardUtils;
import redhat.org.ipark.extras.MyTextFormatter;
import redhat.org.ipark.extras.Utils;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;
import static butterknife.OnTextChanged.Callback.BEFORE_TEXT_CHANGED;

public class BillingActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.billing_edit_full_name)
    EditText editFullName;
    @BindView(R.id.billing_edit_card_number)
    EditText editCardNumber;
    @BindView(R.id.billing_edit_exp_date)
    EditText editExpDate;
    @BindView(R.id.billing_edit_cvv)
    EditText editCVV;
    @BindView(R.id.billing_edit_city)
    EditText editCity;
    @BindView(R.id.billing_edit_state)
    EditText editState;
    @BindView(R.id.billing_edit_zipcode)
    EditText editZipCode;
    @BindView(R.id.billing_edit_country)
    EditText editCountry;
    @BindView(R.id.billing_edit_phone)
    EditText editPhone;

    private ColorStateList defaultTextColor;
    private ColorStateList errorTextColor;
    private boolean isDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_billing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        defaultTextColor = ContextCompat.getColorStateList(this, R.color.colorBlack90);
        errorTextColor = ContextCompat.getColorStateList(this, android.R.color.holo_red_light);

        ButterKnife.bind(this);

        initialize();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }

    @OnClick(R.id.billing_btn_save)
    public void onClickSave(View view) {

    }

    // Card Number text change listener
    @OnTextChanged(R.id.billing_edit_card_number)
    void cardNumberTextChange(CharSequence s, int start, int before, int count) {
        if (before == 0)
            isDelete = false;
        else
            isDelete = true;
    }

    @OnTextChanged(value = R.id.billing_edit_card_number, callback = AFTER_TEXT_CHANGED)
    void afterCardNumberChanged(Editable s) {
        StringBuilder str = new StringBuilder(s.toString());
        String formattedStr = CreditCardUtils.formattedCardNumber(str.toString());
        if (str.length() == 5 || str.length() == 12) {
            if (isDelete) {
                str.deleteCharAt(str.length() - 1);
                editCardNumber.setText(str.toString());
            } else {
                editCardNumber.setText(formattedStr);
            }
            editCardNumber.setSelection(editCardNumber.getText().length());
        }

        if (CreditCardUtils.isValidCard(editCardNumber.getText().toString())) {
            editCardNumber.setTextColor(defaultTextColor);
            editExpDate.requestFocus();
        } else {
            editCardNumber.setTextColor(errorTextColor);
        }
    }

    // Exp. Date text change listener
    @OnTextChanged(R.id.billing_edit_exp_date)
    void expDateChange(CharSequence s, int start, int before, int count) {
        if (before == 0)
            isDelete = false;
        else
            isDelete = true;
    }

    @OnTextChanged(value = R.id.billing_edit_exp_date, callback = AFTER_TEXT_CHANGED)
    void afterExpDateChanged(Editable s) {
        StringBuilder str = new StringBuilder(s.toString());
        String formattedStr = CreditCardUtils.formattedExpDate(s.toString());
        if ((str.length() == 1 && Integer.parseInt(str.toString()) >= 2) ||
            (str.length() == 2)) {
            if (isDelete) {
                str = str.deleteCharAt(str.length() - 1);
                editExpDate.setText(str.toString());
            } else {
                editExpDate.setText(formattedStr);
            }
            editExpDate.setSelection(editExpDate.getText().length());
        }

        if (CreditCardUtils.isValidExpDate(editExpDate.getText().toString())) {
            editExpDate.setTextColor(defaultTextColor);
            editCVV.requestFocus(); // auto move to next edittext
        } else {
            editExpDate.setTextColor(errorTextColor);
        }
    }


    // CVV text change listener
    @OnTextChanged(value = R.id.billing_edit_cvv)
    void cvvTextChange() {
        String str = editCVV.getText().toString();
        if (str.length() == 3) {
            editCity.requestFocus();
        }
    }

    private void initialize() {
        editFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editFullName.setTextColor(defaultTextColor);
                } else {
                    String fullName = editFullName.getText().toString().trim();
                    if (fullName.isEmpty()) {
                        editFullName.setTextColor(errorTextColor);
                    } else {
                        editFullName.setTextColor(defaultTextColor);
                    }
                }
            }
        });

        editCardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editCardNumber.setTextColor(defaultTextColor);
                } else {
                    String cardNumber = editCardNumber.getText().toString().trim();
                    if (cardNumber.isEmpty()) {
                        editCardNumber.setTextColor(errorTextColor);
                    } else if (!CreditCardUtils.isValidCard(cardNumber)) {
                        editCardNumber.setTextColor(errorTextColor);
                    } else {
                        editCardNumber.setTextColor(defaultTextColor);
                    }
                }
            }
        });

        editExpDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editExpDate.setTextColor(defaultTextColor);
                } else {
                    String expDate = editExpDate.getText().toString().trim();
                    if (expDate.isEmpty()) {
                        editExpDate.setTextColor(errorTextColor);
                    } else {
                        editExpDate.setTextColor(defaultTextColor);
                    }
                }
            }
        });

        editCVV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editCVV.setTextColor(defaultTextColor);
                } else {
                    String cvv = editCVV.getText().toString().trim();
                    if (cvv.isEmpty()) {
                        editCVV.setTextColor(errorTextColor);
                    } else {
                        editCVV.setTextColor(defaultTextColor);
                    }
                }
            }
        });

        editCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editCity.setTextColor(defaultTextColor);
                } else {
                    String city = editCity.getText().toString().trim();
                    if (city.isEmpty()) {
                        editCity.setTextColor(errorTextColor);
                    } else {
                        editCity.setTextColor(defaultTextColor);
                    }
                }
            }
        });

        editState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editState.setTextColor(defaultTextColor);
                } else {
                    String state = editState.getText().toString().trim();
                    if (state.isEmpty()) {
                        editState.setTextColor(errorTextColor);
                    } else {
                        editState.setTextColor(defaultTextColor);
                    }
                }
            }
        });

        editZipCode.addTextChangedListener(new MyTextFormatter(editCVV, "#####"));
        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editZipCode.setTextColor(defaultTextColor);
                } else {
                    String zipCode = editZipCode.getText().toString().trim();
                    if (zipCode.isEmpty()) {
                        editZipCode.setTextColor(errorTextColor);
                    } else {
                        editZipCode.setTextColor(defaultTextColor);
                    }
                }
            }
        });

        editPhone.addTextChangedListener(new MyTextFormatter(editPhone, "###-###-####"));
        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editPhone.setTextColor(defaultTextColor);
                } else {
                    String phone = editPhone.getText().toString().trim();
                    if (phone.isEmpty()) {
                        editPhone.setTextColor(errorTextColor);
                    } else if (!Utils.isValidPhoneNumber(phone)) {
                        editPhone.setTextColor(errorTextColor);
                    } else {
                        editPhone.setTextColor(defaultTextColor);
                    }
                }
            }
        });
    }
}
