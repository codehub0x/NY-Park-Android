package redhat.org.ipark;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity implements KeyboardVisibilityListener {

    @BindView(R.id.login_inputLayout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.login_inputLayout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.login_edit_email)
    TextInputEditText editEmail;
    @BindView(R.id.login_edit_password)
    TextInputEditText editPassword;
    @BindView(R.id.login_btn_signin)
    MaterialButton btnSignin;
    @BindView(R.id.login_btn_register)
    MaterialButton btnRegister;
    @BindView(R.id.login_layout_bottom)
    LinearLayout bottomLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
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
        btnSignin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        btnRegister.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

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

    @OnClick(R.id.login_btn_close)
    public void onClickClose(View view) {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.fade_out);
    }

    @OnClick(R.id.login_btn_signin)
    public void onClickSignin(View view) {
        boolean valid = true;
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.requestFocus();
            Utils.showKeyboard(this, editEmail);
            inputLayoutEmail.setError(getString(R.string.error_empty_email));
            valid = false;
        } else if (!Utils.isValidEmail(email)) {
            editEmail.requestFocus();
            Utils.showKeyboard(this, editEmail);
            inputLayoutEmail.setError(getString(R.string.error_invalid_email));
            valid = false;
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
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            finish();
        }

    }

    @OnClick(R.id.login_text_forgot_password)
    public void onClickForgotPassword(View view) {

    }

    @OnClick(R.id.login_btn_register)
    public void onClickRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.nothing);
        finish();
    }

    @OnTouch(R.id.login_scrollview)
    public boolean onTouchScrollView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            Utils.hideKeyboard(this, view);
        }
        return false;
    }
}
