package redhat.org.ipark.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTouch;
import redhat.org.ipark.R;
import redhat.org.ipark.adapters.VehicleAdapter;
import redhat.org.ipark.extras.Utils;

public class VehiclesActivity extends AppCompatActivity {

    private VehicleAdapter mAdapter;

    @BindView(R.id.vehicle_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.vehicle_inputLayout_make)
    TextInputLayout inputLayoutMake;
    @BindView(R.id.vehicle_edit_make)
    TextInputEditText editMake;
    @BindView(R.id.vehicle_inputLayout_model)
    TextInputLayout inputLayoutModel;
    @BindView(R.id.vehicle_edit_model)
    TextInputEditText editModel;
    @BindView(R.id.vehicle_inputLayout_color)
    TextInputLayout inputLayoutColor;
    @BindView(R.id.vehicle_edit_color)
    TextInputEditText editColor;
    @BindView(R.id.vehicle_inputLayout_plate)
    TextInputLayout inputLayoutPlate;
    @BindView(R.id.vehicle_edit_plate)
    TextInputEditText editPlate;
    @BindView(R.id.vehicle_btn_add)
    MaterialButton btnAddVehicle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicles);
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
        btnAddVehicle.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorYellow));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VehicleAdapter(this, new VehicleAdapter.ClickListener() {
            @Override
            public void onClickEdit(int position) {

            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.vehicle_btn_add)
    public void onClickAddVehicle(View view) {
        boolean valid = true;

        String make = editMake.getText().toString().trim();
        if (TextUtils.isEmpty(make)) {
            editMake.requestFocus();
            Utils.showKeyboard(this, editMake);
            valid = false;
            inputLayoutMake.setError(getString(R.string.error_empty_vehicle_make));
        } else {
            inputLayoutMake.setError(null);
        }

        String model = editModel.getText().toString().trim();
        if (TextUtils.isEmpty(model)) {
            if (valid) {
                editModel.requestFocus();
                Utils.showKeyboard(this, editModel);
            }
            valid = false;
            inputLayoutModel.setError(getString(R.string.error_empty_vehicle_model));
        } else {
            inputLayoutModel.setError(null);
        }

        String color = editColor.getText().toString().trim();
        if (TextUtils.isEmpty(color)) {
            if (valid) {
                editColor.requestFocus();
                Utils.showKeyboard(this, editColor);
            }
            valid = false;
            inputLayoutColor.setError(getString(R.string.error_empty_color));
        } else {
            inputLayoutColor.setError(null);
        }

        String plate = editPlate.getText().toString().trim();
        if (TextUtils.isEmpty(plate)) {
            if (valid) {
                editPlate.requestFocus();
                Utils.showKeyboard(this, editPlate);
            }
            valid = false;
            inputLayoutPlate.setError(getString(R.string.error_empty_plate));
        } else {
            inputLayoutPlate.setError(null);
        }

        if (valid) {
            // TODO: Save vehicles
            Utils.hideKeyboard(this, view);
        }

    }

    @OnTouch(R.id.vehicle_scrollView)
    public boolean onTouchView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            Utils.hideKeyboard(this, view);
        }
        return false;
    }

    @OnFocusChange(R.id.vehicle_edit_make)
    void makeFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            String make = editMake.getText().toString().trim();
            if (TextUtils.isEmpty(make)) {
                inputLayoutMake.setError(getString(R.string.error_empty_vehicle_make));
            } else {
                inputLayoutMake.setError(null);
            }
        }
    }

    @OnFocusChange(R.id.vehicle_edit_model)
    void modelFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            String model = editModel.getText().toString().trim();
            if (TextUtils.isEmpty(model)) {
                inputLayoutModel.setError(getString(R.string.error_empty_vehicle_model));
            } else {
                inputLayoutModel.setError(null);
            }
        }
    }

    @OnFocusChange(R.id.vehicle_edit_color)
    void colorFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            String color = editColor.getText().toString().trim();
            if (TextUtils.isEmpty(color)) {
                inputLayoutColor.setError(getString(R.string.error_empty_color));
            } else {
                inputLayoutColor.setError(null);
            }
        }
    }

    @OnFocusChange(R.id.vehicle_edit_plate)
    void plateFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            String plate = editPlate.getText().toString().trim();
            if (TextUtils.isEmpty(plate)) {
                inputLayoutPlate.setError(getString(R.string.error_empty_plate));
            } else {
                inputLayoutPlate.setError(null);
            }
        }
    }
}
