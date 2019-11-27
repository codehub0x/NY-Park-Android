package redhat.org.ipark;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.account_image_avatar)
    RoundedImageView imageAvatar;
    @BindView(R.id.account_edit_fullname)
    TextInputEditText editFullName;
    @BindView(R.id.account_edit_email)
    TextInputEditText editEmail;
    @BindView(R.id.account_edit_phone)
    TextInputEditText editPhone;
    @BindView(R.id.account_edit_password)
    TextInputEditText editPassword;
    @BindView(R.id.account_edit_repeat_password)
    TextInputEditText editRepeatPassword;
    @BindView(R.id.account_btn_save)
    MaterialButton btnSave;
    @BindView(R.id.account_switch_notification)
    Switch switchNotification;

    @OnClick(R.id.account_btn_save)
    public void onClickSave(View view) {

    }

    @OnClick(R.id.account_layout_payment)
    public void onClickPayment(View view) {

    }

    @OnClick(R.id.account_image_avatar)
    public void onClickAvatar(View view) {

    }

    @OnCheckedChanged(R.id.account_switch_notification)
    public void onChangeNotification(Switch switchNotif, boolean state) {

    }

    @OnTouch(R.id.account_scrollView)
    public boolean onTouchScrollView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            hideKeyboard(view);
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void initialize() {
        btnSave.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        boolean isKeyboardUp = imm.isAcceptingText();

        if (isKeyboardUp) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
