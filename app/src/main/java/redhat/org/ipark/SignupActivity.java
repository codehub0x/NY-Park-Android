package redhat.org.ipark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class SignupActivity extends AppCompatActivity {

    private boolean isKeyboardOpened;

    @BindView(R.id.signup_edit_fullname)
    TextInputEditText editFullName;
    @BindView(R.id.signup_edit_email)
    TextInputEditText editEmail;
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

    @OnClick(R.id.signup_btn_close)
    public void onClickClose(View view) {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.fade_out);
    }

    @OnClick(R.id.signup_btn_create)
    public void onClickCreate(View view) {
        ((MyApplication) this.getApplication()).setLoggedIn(true);
        Intent intent = new Intent(SignupActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
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
            hideKeyboard(view);
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        initialize();
        setListenerToRootView();
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
                    isKeyboardOpened = true;
                } else if (isKeyboardOpened == true) {
                    isKeyboardOpened = false;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bottomLayout.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }
            }
        });
    }
}
