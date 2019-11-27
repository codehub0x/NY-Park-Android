package redhat.org.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.warkiz.widget.IndicatorSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.menu_layout_info_login)
    ConstraintLayout layoutInfoLogin;
    @BindView(R.id.menu_layout_info_account)
    ConstraintLayout layoutInfoAccount;
    @BindView(R.id.menu_image_avatar)
    RoundedImageView imageAvatar;
    @BindView(R.id.menu_text_user_name)
    TextView textUserName;
    @BindView(R.id.menu_seekbar)
    IndicatorSeekBar seekBar;
    @BindView(R.id.menu_text_reward_points)
    TextView textRewardPoints;
    @BindView(R.id.menu_text_reward_points_total)
    TextView textRewardTotal;
    @BindView(R.id.menu_text_upcoming)
    TextView textUpcoming;
    @BindView(R.id.menu_text_saved)
    TextView textSaved;
    @BindView(R.id.menu_text_saved_total)
    TextView textSavedTotal;
    @BindView(R.id.menu_text_vehicles)
    TextView textVehicles;
    @BindView(R.id.menu_text_vehicles_total)
    TextView textVehiclesTotal;
    @BindView(R.id.menu_btn1)
    MaterialButton btn1;
    @BindView(R.id.menu_btn2)
    MaterialButton btn2;
    @BindView(R.id.menu_text_version)
    TextView textVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        initialize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }

    private void initialize() {
        // Check login status
        if (isLoggedIn()) {
            layoutInfoAccount.setVisibility(View.VISIBLE);
            layoutInfoLogin.setVisibility(View.GONE);
            btn1.setText(R.string.my_account);
            btn2.setText(R.string.logout);
        } else {
            layoutInfoLogin.setVisibility(View.VISIBLE);
            layoutInfoAccount.setVisibility(View.GONE);
            btn1.setText(R.string.create_account);
            btn2.setText(R.string.login);
        }
        textVersion.setText(getVersionName());
    }

    @OnClick(R.id.menu_layout)
    public void onClickOutside(View view) {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }

    @OnClick(R.id.menu_btn_close)
    public void onClickClose(View view) {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }

    @OnClick(R.id.menu_layout_iPark_rewards)
    public void onClickiParkRewards(View view) {
        Intent intent = new Intent(MenuActivity.this, RewardsActivity.class);
        intent.putExtra("closeLeft", true);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.menu_layout_rewards)
    public void onClickRewards(View view) {
        Intent intent = new Intent(MenuActivity.this, RewardsActivity.class);
        intent.putExtra("closeLeft", true);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.menu_layout_upcoming)
    public void onClickUpcoming(View view) {
        Intent intent = new Intent(MenuActivity.this, ReservationsActivity.class);
        intent.putExtra("closeLeft", true);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.menu_layout_saved)
    public void onClickSaved(View view) {
        Intent intent = new Intent(MenuActivity.this, SavedActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.menu_layout_my_vehicles)
    public void onClickMyVehicles(View view) {
        Intent intent = new Intent(MenuActivity.this, VehiclesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.menu_text_monthly_parking)
    public void onClickMonthlyParking(View view) {

    }

    @OnClick(R.id.menu_text_faq)
    public void onClickFAQ(View view) {
        Intent intent = new Intent(MenuActivity.this, FAQActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.menu_text_promo_codes)
    public void onClickPromoCodes(View view) {

    }

    @OnClick(R.id.menu_text_help)
    public void onClickHelp(View view) {
        Intent intent = new Intent(MenuActivity.this, HelpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.menu_btn1)
    public void onClickBtn1(View view) {
        if (isLoggedIn()) {
            // Go to my account page
            Intent intent = new Intent(MenuActivity.this, AccountActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
        } else {
            // Go to Sign up page
            Intent intent = new Intent(MenuActivity.this, SignupActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.nothing);
        }
    }

    @OnClick(R.id.menu_btn2)
    public void onClickBtn2(View view) {
        if (isLoggedIn()) {
            ((MyApplication) this.getApplication()).setLoggedIn(false);
            finish();
            overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        } else {
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.nothing);
        }
    }

    private boolean isLoggedIn() {
        return ((MyApplication) this.getApplication()).isLoggedIn();
    }

    private String getVersionName() {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        String result = "App Version: v" + versionName + "." + versionCode;
        return result;
    }
}
