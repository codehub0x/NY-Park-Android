package redhat.org.ipark;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import redhat.org.ipark.extras.CreditCardUtils;
import redhat.org.ipark.extras.Utils;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

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
    @BindView(R.id.billing_ccp_country)
    CountryCodePicker ccpCountry;
    @BindView(R.id.billing_edit_phone)
    EditText editPhone;
    @BindView(R.id.billing_ccp_phone)
    CountryCodePicker ccpPhone;
    @BindView(R.id.billing_btn_save)
    MaterialButton btnSave;

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

    @OnTouch(R.id.billing_scrollView)
    boolean onTouchScrollView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            Utils.hideKeyboard(this, view);
        }
        return false;
    }

    @OnClick(R.id.billing_btn_save)
    public void onClickSave(View view) {
        boolean valid = true;

        String fullName = editFullName.getText().toString().trim();
        if (TextUtils.isEmpty(fullName)) {
            editFullName.requestFocus();
            Utils.showKeyboard(this, editFullName);
            editFullName.setError(getString(R.string.error_empty_full_name));
            valid = false;
        } else {
            editFullName.setError(null);
        }

        String cardNumber = editCardNumber.getText().toString();
        cardNumber = cardNumber.replaceAll("\\D+", "");
        if (!CreditCardUtils.isValidCard(cardNumber)) {
            if (valid) {
                editCardNumber.requestFocus();
                Utils.showKeyboard(this, editCardNumber);
            }
            valid = false;
            editCardNumber.setError(getString(R.string.error_invalid_card_number));
            editCardNumber.setTextColor(errorTextColor);
        } else {
            editCardNumber.setError(null);
            editCardNumber.setTextColor(defaultTextColor);
        }

        String expDate = editExpDate.getText().toString().trim();
        if (!CreditCardUtils.isValidExpDate(expDate)) {
            if (valid) {
                editExpDate.requestFocus();
                Utils.showKeyboard(this, editExpDate);
            }
            valid = false;
            editExpDate.setTextColor(errorTextColor);
            editExpDate.setError(getString(R.string.error_invalid_exp_date));
        } else {
            editExpDate.setError(null);
            editExpDate.setTextColor(defaultTextColor);
        }

        String cvv = editCVV.getText().toString().trim();
        if (cvv.length() != 3) {
            if (valid) {
                editCVV.requestFocus();
                Utils.showKeyboard(this, editCVV);
            }
            valid = false;
            editCVV.setTextColor(errorTextColor);
            editCVV.setError(getString(R.string.error_invalid_cvv));
        } else {
            editCVV.setTextColor(defaultTextColor);
            editCVV.setError(null);
        }

        String country = ccpCountry.getSelectedCountryName();
        if (TextUtils.isEmpty(country)) {
            valid = false;
        }

        String phone = ccpPhone.getFullNumberWithPlus();
        if (!Utils.isValidPhoneNumber(phone)) {
            if (valid) {
                editPhone.requestFocus();
                Utils.showKeyboard(this, editPhone);
            }
            valid = false;
            editPhone.setTextColor(errorTextColor);
            editPhone.setError(getString(R.string.error_invalid_phone));
        } else {
            editPhone.setTextColor(defaultTextColor);
            editPhone.setError(null);
        }

        if (valid) {
            // TODO: save billing informations

            finish();
            overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        }
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
    @OnTextChanged(R.id.billing_edit_cvv)
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
                if (!hasFocus) {
                    String fullName = editFullName.getText().toString().trim();
                    if (fullName.isEmpty()) {
                        editFullName.setError(getString(R.string.error_empty_full_name));
                    } else {
                        editFullName.setError(null);
                    }
                }
            }
        });

        editCardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String cardNumber = editCardNumber.getText().toString().trim();
                    if (!CreditCardUtils.isValidCard(cardNumber)) {
                        editCardNumber.setTextColor(errorTextColor);
                        editCardNumber.setError(getString(R.string.error_invalid_card_number));
                    } else {
                        editCardNumber.setTextColor(defaultTextColor);
                        editCardNumber.setError(null);
                    }
                }
            }
        });

        editExpDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String expDate = editExpDate.getText().toString().trim();
                    if (!CreditCardUtils.isValidExpDate(expDate)) {
                        editExpDate.setTextColor(errorTextColor);
                        editExpDate.setError(getString(R.string.error_invalid_exp_date));
                    } else {
                        editExpDate.setTextColor(defaultTextColor);
                        editExpDate.setError(null);
                    }
                }
            }
        });

        editCVV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String cvv = editCVV.getText().toString().trim();
                    if (cvv.length() == 3) {
                        editCVV.setTextColor(defaultTextColor);
                        editCVV.setError(null);
                    } else {
                        editCVV.setTextColor(errorTextColor);
                        editCVV.setError(getString(R.string.error_invalid_cvv));
                    }
                }
            }
        });

        editCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String city = editCity.getText().toString().trim();
                    if (city.isEmpty()) {
                        editCity.setError(getString(R.string.error_empty_city));
                    } else {
                        editCity.setError(null);
                    }
                }
            }
        });

        editState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String state = editState.getText().toString().trim();
                    if (state.isEmpty()) {
                        editState.setError(getString(R.string.error_empty_state));
                    } else {
                        editState.setError(null);
                    }
                }
            }
        });

        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String zipCode = editZipCode.getText().toString().trim();
                    if (zipCode.length() == 5) {
                        editZipCode.setTextColor(defaultTextColor);
                        editZipCode.setError(null);
                    } else {
                        editZipCode.setTextColor(errorTextColor);
                        editZipCode.setError(getString(R.string.error_invalid_zipcode));
                    }
                }
            }
        });

        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String phone = ccpPhone.getFullNumberWithPlus();
                    if (!Utils.isValidPhoneNumber(phone)) {
                        editPhone.setTextColor(errorTextColor);
                        editPhone.setError(getString(R.string.error_invalid_phone));
                    } else {
                        editPhone.setTextColor(defaultTextColor);
                        editPhone.setError(null);
                    }
                }
            }
        });

        ccpPhone.registerCarrierNumberEditText(editPhone);
        ccpPhone.setNumberAutoFormattingEnabled(true);
        ccpPhone.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if (isValidNumber) {
                    editPhone.setTextColor(defaultTextColor);
                } else {
                    editPhone.setTextColor(errorTextColor);
                }
            }
        });

        btnSave.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

    }
}
