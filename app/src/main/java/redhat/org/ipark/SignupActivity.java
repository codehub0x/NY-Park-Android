package redhat.org.ipark;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import redhat.org.ipark.extras.KeyboardVisibilityListener;
import redhat.org.ipark.extras.Utils;

public class SignupActivity extends AppCompatActivity implements KeyboardVisibilityListener {

    @BindView(R.id.signup_inputLayout_fullname)
    TextInputLayout inputLayoutFullName;
    @BindView(R.id.signup_edit_fullname)
    TextInputEditText editFullName;
    @BindView(R.id.signup_inputLayout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.signup_edit_email)
    TextInputEditText editEmail;
    @BindView(R.id.signup_inputLayout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.signup_edit_password)
    TextInputEditText editPassword;
    @BindView(R.id.signup_btn_create)
    MaterialButton btnCreate;
    @BindView(R.id.signup_btn_login)
    MaterialButton btnLogin;
    @BindView(R.id.signup_text_terms)
    TextView textTerms;
    @BindView(R.id.signup_layout_bottom)
    LinearLayout bottomLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        initialize();
        Utils.setKeyboardVisibilityListener(this, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.fade_out);
    }

    private void initialize() {
        btnLogin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        btnCreate.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

        textTerms.setPaintFlags(textTerms.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        editFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String fullName = editFullName.getText().toString().trim();
                    if (fullName.isEmpty()) {
                        inputLayoutFullName.setError(getString(R.string.error_empty_full_name));
                    } else {
                        inputLayoutFullName.setError(null);
                    }
                }
            }
        });

        editEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = editEmail.getText().toString().trim();
                    if (email.isEmpty()) {
                        inputLayoutEmail.setError(getString(R.string.error_empty_email));
                    } else if (!Utils.isValidEmail(email)) {
                        inputLayoutEmail.setError(getString(R.string.error_invalid_email));
                    } else {
                        inputLayoutEmail.setError(null);
                    }
                }
            }
        });

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputLayoutPassword.setError(null);
                } else {
                    String password = editPassword.getText().toString().trim();
                    if (password.isEmpty()) {
                        inputLayoutPassword.setError(getString(R.string.error_empty_password));
                    } else {
                        inputLayoutPassword.setError(null);
                    }
                }
            }
        });
    }

    @Override
    public void onKeyboardVisibilityChanged(boolean keyboardVisible) {
        if (keyboardVisible) {
            bottomLayout.setVisibility(View.GONE);
        } else {
            bottomLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.signup_btn_close)
    public void onClickClose(View view) {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.fade_out);
    }

    @OnClick(R.id.signup_btn_create)
    public void onClickCreate(View view) {
        boolean valid = true;
        String fullName = editFullName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            valid = false;
            editFullName.requestFocus();
            Utils.showKeyboard(this, editFullName);
            inputLayoutFullName.setError(getString(R.string.error_empty_full_name));
        } else {
            inputLayoutFullName.setError(null);
        }

        if (email.isEmpty()) {
            if (valid) {
                editEmail.requestFocus();
                Utils.showKeyboard(this, editEmail);
            }
            valid = false;
            inputLayoutEmail.setError(getString(R.string.error_empty_email));
        } else if (!Utils.isValidEmail(email)) {
            if (valid) {
                editEmail.requestFocus();
                Utils.showKeyboard(this, editEmail);
            }
            valid = false;
            inputLayoutEmail.setError(getString(R.string.error_invalid_email));
        } else {
            inputLayoutEmail.setError(null);
        }

        if (password.isEmpty()) {
            if (valid) {
                editPassword.requestFocus();
                Utils.showKeyboard(this, editPassword);
            }
            valid = false;
            inputLayoutPassword.setError(getString(R.string.error_empty_password));
        } else {
            inputLayoutPassword.setError(null);
        }

        if (valid) {
            ((MyApplication) this.getApplication()).setLoggedIn(true);
            Intent intent = new Intent(SignupActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            finish();
        }

    }

    @OnClick(R.id.signup_btn_login)
    public void onClickLogin(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.nothing);
        finish();
    }

    @OnClick(R.id.signup_text_terms)
    public void onClickTerms(View view) {

    }

    @OnTouch(R.id.signup_scrollview)
    public boolean onTouchScrollView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            Utils.hideKeyboard(this, view);
        }
        return false;
    }
}
