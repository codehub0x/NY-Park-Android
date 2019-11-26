package redhat.org.ipark;

import android.content.Intent;
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
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }

    private void initialize() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SavedAdapter(this, new SavedAdapter.ClickListener() {
            @Override
            public void onBookClicked(int position) {
                Intent intent = new Intent(SavedActivity.this, BookActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            }

            @Override
            public void onDetailsClicked(int position) {
                // TODO: get item by position
                Intent intent = new Intent(SavedActivity.this, DetailsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

}
