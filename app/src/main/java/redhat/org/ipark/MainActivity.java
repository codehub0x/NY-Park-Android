package redhat.org.ipark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        break;
                    case R.id.navigation_reservations:
                        intent = new Intent(MainActivity.this, ReservationsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
                        break;
                    case R.id.navigation_rewards:
                        intent = new Intent(MainActivity.this, RewardsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.bottom_up, R.anim.nothing);
                        break;
                    case R.id.navigation_menu:
                        intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.nothing);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


}
