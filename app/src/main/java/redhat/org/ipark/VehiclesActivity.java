package redhat.org.ipark;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import redhat.org.ipark.adapters.VehicleAdapter;

public class VehiclesActivity extends AppCompatActivity {

    private VehicleAdapter mAdapter;

    @BindView(R.id.vehicle_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.vehicle_edit_vehicle_make)
    TextInputEditText editMake;
    @BindView(R.id.vehicle_edit_vehicle_model)
    TextInputEditText editModel;
    @BindView(R.id.vehicle_edit_color)
    TextInputEditText editColor;
    @BindView(R.id.vehicle_edit_plate)
    TextInputEditText editPlate;
    @BindView(R.id.vehicle_btn_add)
    MaterialButton btnAddVehicle;

    @OnClick(R.id.vehicle_btn_add)
    public void onClickAddVehicle(MaterialButton button) {
        hideKeyboard(button);
    }

    @OnTouch(R.id.vehicle_scrollView)
    public boolean onTouchView(View view, MotionEvent event) {
        if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
            hideKeyboard(view);
        }
        return false;
    }

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

    private void hideKeyboard(View view) {
        InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        boolean isKeyboardUp = imm.isAcceptingText();

        if (isKeyboardUp) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
