package redhat.org.ipark;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;
import redhat.org.ipark.extras.MyTextFormatter;
import redhat.org.ipark.extras.Utils;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = AccountActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    @BindView(R.id.account_image_avatar)
    RoundedImageView imageAvatar;
    @BindView(R.id.account_inputLayout_fullname)
    TextInputLayout inputLayoutFullName;
    @BindView(R.id.account_edit_fullname)
    TextInputEditText editFullName;
    @BindView(R.id.account_inputLayout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.account_edit_email)
    TextInputEditText editEmail;
    @BindView(R.id.account_inputLayout_phone)
    TextInputLayout inputLayoutPhone;
    @BindView(R.id.account_edit_phone)
    TextInputEditText editPhone;
    @BindView(R.id.account_inputLayout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.account_edit_password)
    TextInputEditText editPassword;
    @BindView(R.id.account_inputLayout_repeat_password)
    TextInputLayout inputLayoutRepeatPassword;
    @BindView(R.id.account_edit_repeat_password)
    TextInputEditText editRepeatPassword;
    @BindView(R.id.account_btn_save)
    MaterialButton btnSave;
    @BindView(R.id.account_switch_notification)
    Switch switchNotification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        btnSave.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

        loadProfileDefault();
        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);

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
        editFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputLayoutFullName.setError(null);
                } else {
                    String fullName = editFullName.getText().toString().trim();
                    if (TextUtils.isEmpty(fullName)) {
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
                if (hasFocus) {
                    inputLayoutEmail.setError(null);
                } else {
                    String email = editEmail.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        inputLayoutEmail.setError(getString(R.string.error_empty_email));
                    } else if (!Utils.isValidEmail(email)) {
                        inputLayoutEmail.setError(getString(R.string.error_invalid_email));
                    } else {
                        inputLayoutEmail.setError(null);
                    }
                }
            }
        });

        editPhone.addTextChangedListener(new MyTextFormatter(editPhone, "###-###-####"));
        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputLayoutPhone.setError(null);
                } else {
                    String phone = editPhone.getText().toString().trim();
                    if (TextUtils.isEmpty(phone)) {
                        inputLayoutPhone.setError(getString(R.string.error_empty_phone));
                    } else if (!Utils.isValidPhoneNumber(phone)) {
                        inputLayoutPhone.setError(getString(R.string.error_invalid_phone));
                    } else {
                        inputLayoutPhone.setError(null);
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
                    if (TextUtils.isEmpty(password)) {
                        inputLayoutPassword.setError(getString(R.string.error_empty_password));
                    } else {
                        inputLayoutPassword.setError(null);
                    }
                }
            }
        });

        editRepeatPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputLayoutRepeatPassword.setError(null);
                } else {
                    String password = editPassword.getText().toString();
                    String repeatPassword = editRepeatPassword.getText().toString();
                    if (TextUtils.isEmpty(repeatPassword)) {
                        inputLayoutRepeatPassword.setError(getString(R.string.error_empty_repeat_password));
                    } else if (!password.equals(repeatPassword)) {
                        inputLayoutRepeatPassword.setError(getString(R.string.error_mismatch_password));
                    } else {
                        inputLayoutRepeatPassword.setError(null);
                    }
                }
            }
        });
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        Glide.with(this).load(url)
                .into(imageAvatar);
        imageAvatar.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Glide.with(this).load(R.drawable.avatar)
                .into(imageAvatar);
        imageAvatar.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    @OnClick(R.id.account_btn_save)
    void onClickSave(View view) {
        boolean valid = true;

        String fullName = editFullName.getText().toString().trim();
        if (fullName.isEmpty()) {
            editFullName.requestFocus();
            valid = false;
            inputLayoutFullName.setError(getString(R.string.error_empty_full_name));
        }

        String email = editEmail.getText().toString().trim();
        if (email.isEmpty()) {
            if (valid) {
                editEmail.requestFocus();
            }
            valid = false;
            inputLayoutEmail.setError(getString(R.string.error_empty_email));
        } else if (!Utils.isValidEmail(email)) {
            if (valid) {
                editEmail.requestFocus();
            }
            valid = false;
            inputLayoutEmail.setError(getString(R.string.error_invalid_email));
        } else {
            inputLayoutEmail.setError(null);
        }

        String phone = editPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            if (valid) {
                editPhone.requestFocus();
            }
            valid = false;
            inputLayoutPhone.setError(getString(R.string.error_empty_phone));
        } else if (!Utils.isValidPhoneNumber(phone)) {
            if (valid) {
                editPhone.requestFocus();
            }
            valid = false;
            inputLayoutPhone.setError(getString(R.string.error_invalid_phone));
        } else {
            inputLayoutPhone.setError(null);
        }

        String password = editPassword.getText().toString();
        if (password.isEmpty()) {
            if (valid) {
                editPassword.requestFocus();
            }
            valid = false;
            inputLayoutPassword.setError(getString(R.string.error_empty_password));
        } else {
            inputLayoutPassword.setError(null);
        }

        String repeatPassword = editRepeatPassword.getText().toString();
        if (repeatPassword.isEmpty()) {
            if (valid) {
                editRepeatPassword.requestFocus();
            }
            valid = false;
            inputLayoutRepeatPassword.setError(getString(R.string.error_empty_repeat_password));
        } else if (!password.equals(repeatPassword)) {
            if (valid) {
                editRepeatPassword.requestFocus();
            }
            valid = false;
            inputLayoutRepeatPassword.setError(getString(R.string.error_mismatch_password));
        } else {
            inputLayoutRepeatPassword.setError(null);
        }

        if (valid) {
            finish();
            overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        }
    }

    @OnClick(R.id.account_layout_payment)
    void onClickPayment(View view) {

    }

    @OnCheckedChanged(R.id.account_switch_notification)
    void onChangeNotification(Switch switchNotif, boolean state) {

    }

    @OnTouch(R.id.account_scrollView)
    boolean onTouchScrollView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            Utils.hideKeyboard(this, view);
        }
        return false;
    }

    @OnClick(R.id.account_image_avatar)
    void onClickAvatar(View view) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, getSupportFragmentManager(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(AccountActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 200);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 200);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(AccountActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 200);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 200);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    if (Build.VERSION.SDK_INT < 28) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } else {
                        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), uri);
                        Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                    }

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
