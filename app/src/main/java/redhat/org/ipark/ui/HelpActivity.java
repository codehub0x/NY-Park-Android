package redhat.org.ipark.ui;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import redhat.org.ipark.R;

public class HelpActivity extends AppCompatActivity {

    @BindView(R.id.help_btn_phone)
    MaterialButton btnPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);
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
        btnPhone.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));
    }

    @OnClick(R.id.help_layout_website)
    public void onClickWebSite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ipark.com/"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.help_layout_email)
    public void onClickEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:info@ipark.com"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Email failure")
                    .setMessage("There isn't an Email app in your phone. Please install the email app and try again!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    @OnClick(R.id.help_layout_rate)
    public void onClickRate(View view) {

    }

    @OnClick(R.id.help_layout_faq)
    public void onClickFAQ(View view) {
        Intent intent = new Intent(HelpActivity.this, FAQActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
    }

    @OnClick(R.id.help_btn_phone)
    public void onClickPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8554727569"));
        startActivity(intent);
    }

    @OnClick(R.id.help_layout_terms)
    public void onClickTerms(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ipark.com/terms-and-conditions/"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.help_layout_privacy)
    public void onClickPrivacy(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ipark.com/privacy-policy/"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.help_image_facebook)
    public void onClickFacebook(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pages/IPark/606293799431302"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.help_image_twitter)
    public void onClickTwitter(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/imperialparking"));
        startActivity(browserIntent);
    }
}
