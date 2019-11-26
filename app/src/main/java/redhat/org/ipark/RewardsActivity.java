package redhat.org.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RewardsActivity extends AppCompatActivity {

    private boolean closeLeft;

    @BindView(R.id.rewards_layout_top)
    LinearLayout topLayout;

    @OnClick(R.id.rewards_btn_create_account)
    public void onClickCreateAccount(MaterialButton button) {
        Intent intent = new Intent(RewardsActivity.this, SignupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @OnClick(R.id.rewards_btn_login)
    public void onClickLogin(MaterialButton button) {
        Intent intent = new Intent(RewardsActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rewards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        closeLeft = getIntent().getBooleanExtra("closeLeft", false);

        initialize();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        if (closeLeft) {
            overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        } else {
            overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (closeLeft) {
            overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        } else {
            overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
        }
    }

    private void initialize() {
        if (((MyApplication) this.getApplication()).isLoggedIn()) {
            topLayout.setVisibility(View.GONE);
        } else {
            topLayout.setVisibility(View.VISIBLE);
        }
    }
}
