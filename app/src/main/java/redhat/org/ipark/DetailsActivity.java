package redhat.org.ipark;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class DetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        initializeCustomeActionBar("West 90TH Garage Corp.", "7 East 14th Street, New York, NY 10003 Between 2nd Ave. and 3rd Ave.");

    }

    private void initializeCustomeActionBar(String header, String subHeader) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.header_two_lines);

        View view = actionBar.getCustomView();
        Toolbar toolbar = (Toolbar) view.getParent();
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);

        ImageButton btnBack = view.findViewById(R.id.header_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
            }
        });

        ImageButton btnFavorite = view.findViewById(R.id.header_btn_favorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView textHeader = view.findViewById(R.id.header_text_header);
        TextView textSubHeader = view.findViewById(R.id.header_text_subheader);
        textHeader.setText(header);
        textSubHeader.setText(subHeader);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.left_to_right);
    }
}
