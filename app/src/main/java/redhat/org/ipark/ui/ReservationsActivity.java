package redhat.org.ipark.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import redhat.org.ipark.R;
import redhat.org.ipark.adapters.ReservationsAdapter;

public class ReservationsActivity extends AppCompatActivity {

    private ReservationsAdapter adapter;
    private boolean closeLeft;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservationsAdapter(this, new ReservationsAdapter.ClickListener() {
            @Override
            public void onDirectionsClicked(int position) {
                LatLng destLatlng = new LatLng(41.8057, 123.4315);
                String urlStr = "http://maps.google.com/maps?daddr=" + destLatlng.latitude + "," + destLatlng.longitude;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
                startActivity(browserIntent);
            }

            @Override
            public void onReBookClicked(int position) {
                Intent intent = new Intent(ReservationsActivity.this, BookActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            }

            @Override
            public void onDetailsClicked(int position) {
                Intent intent = new Intent(ReservationsActivity.this, DetailsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            }

            @Override
            public void onUpcomingDetailsClicked(int position) {
                Intent intent = new Intent(ReservationsActivity.this, UpcomingDetailsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
            }
        });
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
}
