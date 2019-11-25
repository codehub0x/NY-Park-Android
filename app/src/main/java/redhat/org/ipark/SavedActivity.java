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
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initialize() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SavedAdapter();
        recyclerView.setAdapter(mAdapter);
    }

}
