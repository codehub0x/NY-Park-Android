package redhat.org.ipark.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.button.MaterialButton;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import redhat.org.ipark.R;
import redhat.org.ipark.extras.CreditCardUtils;
import redhat.org.ipark.extras.Utils;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class BillingActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private final int SCAN_REQUEST_CODE = 101;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.billing_menu, menu);
        Drawable drawable = menu.findItem(R.id.scan).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.colorWhite));
        menu.findItem(R.id.scan).setIcon(drawable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.scan:
                Log.d(TAG, "Select scan");
                Intent intent = new Intent(this, CardIOActivity.class);
                intent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
                intent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true);
                intent.putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true);
                startActivityForResult(intent, SCAN_REQUEST_CODE);
                return true;
        }
        return(super.onOptionsItemSelected(item));
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
        boolean isValidField;

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
        isValidField = CreditCardUtils.isValidCard(cardNumber);
        updateLabel(isValidField, editCardNumber);
        if (!isValidField) {
            if (valid) {
                editCardNumber.requestFocus();
                Utils.showKeyboard(this, editCardNumber);
            }
            valid = false;
            editCardNumber.setError(getString(R.string.error_invalid_card_number));
        } else {
            editCardNumber.setError(null);
        }

        String expDate = editExpDate.getText().toString().trim();
        isValidField = CreditCardUtils.isValidExpDate(expDate);
        updateLabel(isValidField, editExpDate);
        if (!isValidField) {
            if (valid) {
                editExpDate.requestFocus();
                Utils.showKeyboard(this, editExpDate);
            }
            valid = false;
            editExpDate.setError(getString(R.string.error_invalid_exp_date));
        } else {
            editExpDate.setError(null);
        }

        String cvv = editCVV.getText().toString();
        isValidField = CreditCardUtils.isValidCVV(cvv, cardNumber);
        updateLabel(isValidField, editCVV);
        if (!isValidField) {
            if (valid) {
                editCVV.requestFocus();
                Utils.showKeyboard(this, editCVV);
            }
            valid = false;
            editCVV.setError(getString(R.string.error_invalid_cvv));
        } else {
            editCVV.setError(null);
        }

        String city = editCity.getText().toString();
        if (TextUtils.isEmpty(city)) {
            if (valid) {
                editCity.requestFocus();;
                Utils.showKeyboard(this, editCity);
            }
            valid = false;
            editCity.setError(getString(R.string.error_empty_city));
        } else {
            editCity.setError(null);
        }

        String state = editState.getText().toString();
        if (TextUtils.isEmpty(state)) {
            if (valid) {
                editState.requestFocus();;
                Utils.showKeyboard(this, editState);
            }
            valid = false;
            editState.setError(getString(R.string.error_empty_state));
        } else {
            editState.setError(null);
        }

        String zipCode = editZipCode.getText().toString().trim();
        if (zipCode.length() == 5) {
            editZipCode.setError(null);
        } else {
            if (valid) {
                editZipCode.requestFocus();
                Utils.showKeyboard(this, editZipCode);
            }
            valid = false;
            editZipCode.setError(getString(R.string.error_invalid_zipcode));
        }

        String country = ccpCountry.getSelectedCountryName();
        if (TextUtils.isEmpty(country)) {
            valid = false;
        }

        String phone = ccpPhone.getFullNumberWithPlus();
        isValidField = Utils.isValidPhoneNumber(phone);
        updateLabel(isValidField, editPhone);
        if (!isValidField) {
            if (valid) {
                editPhone.requestFocus();
                Utils.showKeyboard(this, editPhone);
            }
            valid = false;
            editPhone.setError(getString(R.string.error_invalid_phone));
        } else {
            editPhone.setError(null);
        }

        if (valid) {
            // TODO: save billing informations

            finish();
            overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        }
    }

    @OnTextChanged(value = R.id.billing_edit_card_number, callback = AFTER_TEXT_CHANGED)
    void afterCardNumberChanged(Editable s) {
        StringBuilder str = new StringBuilder(s.toString());
        String formattedStr = CreditCardUtils.formattedCardNumber(str.toString());
        if (str.length() != formattedStr.length()) {
            editCardNumber.setText(formattedStr);
            editCardNumber.setSelection(editCardNumber.getText().length());
        }

        boolean isValid = CreditCardUtils.isValidCard(editCardNumber.getText().toString());
        updateLabel(isValid, editCardNumber);
        if (isValid) {
            editExpDate.requestFocus();
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

        boolean isValid = CreditCardUtils.isValidExpDate(editExpDate.getText().toString());
        updateLabel(isValid, editExpDate);
        if (isValid) {
            editCVV.requestFocus(); // auto move to next edittext
        }
    }


    // CVV text change listener
    @OnTextChanged(R.id.billing_edit_cvv)
    void cvvTextChange() {
        String str = editCVV.getText().toString();
        String cardNumber = editCardNumber.getText().toString();
        boolean isValid = CreditCardUtils.isValidCVV(str, cardNumber);
        updateLabel(isValid, editCVV);
        if (isValid) {
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
                    String cardNumber = editCardNumber.getText().toString();
                    boolean isValid = CreditCardUtils.isValidCard(cardNumber);
                    updateLabel(isValid, editCardNumber);
                    if (isValid) {
                        editCardNumber.setError(null);
                    } else {
                        editCardNumber.setError(getString(R.string.error_invalid_card_number));
                    }
                }
            }
        });

        editExpDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String expDate = editExpDate.getText().toString().trim();
                    boolean isValid = CreditCardUtils.isValidExpDate(expDate);
                    updateLabel(isValid, editExpDate);
                    if (isValid) {
                        editExpDate.setError(null);
                    } else {
                        editExpDate.setError(getString(R.string.error_invalid_exp_date));
                    }
                }
            }
        });

        editCVV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String cvv = editCVV.getText().toString();
                    String cardNumber = editCardNumber.getText().toString();
                    boolean isValid = CreditCardUtils.isValidCVV(cvv, cardNumber);
                    updateLabel(isValid, editCVV);
                    if (isValid) {
                        editCVV.setError(null);
                    } else {
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
                    boolean isValid = zipCode.length() == 5;
                    if (isValid) {
                        editZipCode.setError(null);
                    } else {
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
                    boolean isValid = Utils.isValidPhoneNumber(phone);
                    updateLabel(isValid, editPhone);
                    if (isValid) {
                        editPhone.setError(null);
                    } else {
                        editPhone.setError(getString(R.string.error_invalid_phone));
                    }
                }
            }
        });

        ccpPhone.registerCarrierNumberEditText(editPhone);
        ccpPhone.setNumberAutoFormattingEnabled(true);
        ccpPhone.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                updateLabel(isValidNumber, editPhone);
            }
        });

        btnSave.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                String expMonth = scanResult.expiryMonth > 9 ? String.valueOf(scanResult.expiryMonth) : "0" + scanResult.expiryMonth;
                String expYear = String.valueOf(scanResult.expiryYear - 2000);
                String expDate = expMonth + "/" + expYear;
                editCardNumber.setText(CreditCardUtils.formattedCardNumber(scanResult.cardNumber));
                editExpDate.setText(CreditCardUtils.formattedExpDate(expDate));
                editCVV.setText(scanResult.cvv);
            } else {
                Toast.makeText(this, "Scan was canceled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateLabel(boolean isValid, EditText editText) {
        if (isValid) {
            editText.setTextColor(defaultTextColor);
        } else {
            editText.setTextColor(errorTextColor);
        }
    }
}
