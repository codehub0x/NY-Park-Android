package redhat.org.ipark;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import redhat.org.ipark.adapters.SavedAdapter;

public class SavedActivity extends AppCompatActivity {

    private SavedAdapter mAdapter;

    @BindView(R.id.saved_recyclerView)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initialize();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
    }

    private void initialize() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SavedAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

}
