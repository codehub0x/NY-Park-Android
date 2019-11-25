package redhat.org.ipark;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import redhat.org.ipark.adapters.ReservationsAdapter;

public class ReservationsActivity extends AppCompatActivity {

    private ReservationsAdapter adapter;

    @BindView(R.id.reservations_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.reservations_recyclerView)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reservations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservationsAdapter(this);
        recyclerView.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        adapter.setType(ReservationsAdapter.ReservationsType.UPCOMING);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        adapter.setType(ReservationsAdapter.ReservationsType.PAST);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        adapter.setType(ReservationsAdapter.ReservationsType.CANCELED);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
}
