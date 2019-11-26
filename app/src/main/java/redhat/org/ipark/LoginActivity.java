package redhat.org.ipark;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LoginActivity extends AppCompatActivity {

    private boolean isOpened;

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

    @OnClick(R.id.login_btn_close)
    public void onClickClose(View view) {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
    }

    @OnClick(R.id.login_btn_signin)
    public void onClickSignin(View view) {
        ((MyApplication) this.getApplication()).setLoggedIn(true);
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.login_text_forgot_password)
    public void onClickForgotPassword(View view) {

    }

    @OnClick(R.id.login_btn_register)
    public void onClickRegister(View view) {

    }

    @OnTouch(R.id.login_scrollview)
    public boolean onTouchScrollView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            hideKeyboard(view);
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initialize();
        setListenerToRootView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
    }

    private void initialize() {
        btnSignin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        btnRegister.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        boolean isKeyboardUp = imm.isAcceptingText();

        if (isKeyboardUp) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setListenerToRootView() {
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.
                    bottomLayout.setVisibility(View.GONE);
                    isOpened = true;
                } else if (isOpened == true) {
                    bottomLayout.setVisibility(View.VISIBLE);
                    isOpened = false;
                }
            }
        });
    }
}
