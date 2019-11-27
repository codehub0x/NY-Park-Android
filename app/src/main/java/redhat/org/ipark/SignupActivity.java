package redhat.org.ipark;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import redhat.org.ipark.extras.KeyboardVisibilityListener;
import redhat.org.ipark.extras.Utils;

public class SignupActivity extends AppCompatActivity implements KeyboardVisibilityListener {

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
    }

    @Override
    public void onKeyboardVisibilityChanged(boolean keyboardVisible) {
        if (keyboardVisible) {
            bottomLayout.setVisibility(View.GONE);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bottomLayout.setVisibility(View.VISIBLE);
                }
            }, 300);
        }
    }

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
        finish();
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
