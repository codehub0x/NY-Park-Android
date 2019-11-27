package redhat.org.ipark;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import redhat.org.ipark.adapters.FAQAdapter;
import redhat.org.ipark.models.FaqItem;
import redhat.org.ipark.models.FaqSubItem;

public class FAQActivity extends AppCompatActivity implements FAQAdapter.ClickListener {

    private FAQAdapter mAdapter;
    private List<FaqItem> data = new ArrayList<>();

    @BindView(R.id.faq_listview)
    ExpandableListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_faq);
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
        prepareData();

        View header = getLayoutInflater().inflate(R.layout.item_faq_header, null);
        listView.addHeaderView(header);

        mAdapter = new FAQAdapter(this, data, this);
        listView.setAdapter(mAdapter);

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int len = mAdapter.getGroupCount();
                for (int i = 0; i < len; i ++) {
                    if (i != groupPosition) {
                        listView.collapseGroup(i);
                    }
                }
            }
        });

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                List<FaqSubItem> items = data.get(groupPosition).getItems();
                for (int i = 0; i < items.size(); i ++) {
                    items.get(i).setItemType(FaqSubItem.ItemType.Collapsed);
                }
            }
        });
    }

    private void prepareData() {
        String answer = "We guarantee you will have a spot to park at the rate you selected when you reserve through iPark! In the rare occasion there is an issue, please call our support line at (855) 472-7569.";
        FaqSubItem subItem = new FaqSubItem("How does iPark work?", answer);
        List<FaqSubItem> items = new ArrayList<>();
        items.add(new FaqSubItem("How does iPark work?", answer));
        items.add(new FaqSubItem("Are iPark parking spots guaranteed?", answer));
        items.add(new FaqSubItem("What is iPark's cancellation policy?", answer));
        items.add(new FaqSubItem("Do I have to enter and exit at the exact times on the parking pass?", answer));
        items.add(new FaqSubItem("What do the prices mean?", answer));

        data.add(new FaqItem("How it Works", items));

        List<FaqSubItem> items1 = new ArrayList<>();
        items1.add(new FaqSubItem("Are iPark parking spots guaranteed?", answer));
        items1.add(new FaqSubItem("Do I have to enter and exit at the exact times on the parking pass?", answer));
        items1.add(new FaqSubItem("Why isn't my normal spot showing up in the app?", answer));
        data.add(new FaqItem("What is iPark", items1));

        List<FaqSubItem> items2 = new ArrayList<>();
        items2.add(new FaqSubItem("Do I have to enter and exit at the exact times on the parking pass?", answer));
        items2.add(new FaqSubItem("What do the prices mean?", answer));
        items2.add(new FaqSubItem("Why can't I extend my reservation?", answer));
        items2.add(new FaqSubItem("Why isn't my normal spot showing up in the app?", answer));
        data.add(new FaqItem("Booking", items2));

        List<FaqSubItem> items3 = new ArrayList<>();
        items3.add(new FaqSubItem("Why can't I extend my reservation?", answer));
        items3.add(new FaqSubItem("Why isn't my normal spot showing up in the app?", answer));
        data.add(new FaqItem("Monthly Parking", items3));

        List<FaqSubItem> items4 = new ArrayList<>();
        items4.add(new FaqSubItem("What is iPark's cancellation policy?", answer));
        items4.add(new FaqSubItem("Do I have to enter and exit at the exact times on the parking pass?", answer));
        items4.add(new FaqSubItem("What if something goes wrong with my reservation?", answer));
        data.add(new FaqItem("Post Purchase", items4));

        List<FaqSubItem> items5 = new ArrayList<>();
        items5.add(new FaqSubItem("What if something goes wrong with my reservation?", answer));
        data.add(new FaqItem("Parking Pass", items5));

        List<FaqSubItem> items6 = new ArrayList<>();
        items6.add(new FaqSubItem("How does iPark work?", answer));
        items6.add(new FaqSubItem("Are iPark parking spots guaranteed?", answer));
        items6.add(new FaqSubItem("What is iPark's cancellation policy?", answer));
        items6.add(new FaqSubItem("Why can't I extend my reservation?", answer));
        items6.add(new FaqSubItem("Why isn't my normal spot showing up in the app?", answer));
        data.add(new FaqItem("Commercial Accounts", items6));

        List<FaqSubItem> items7 = new ArrayList<>();
        items7.add(new FaqSubItem("Are iPark parking spots guaranteed?", answer));
        items7.add(new FaqSubItem("What is iPark's cancellation policy?", answer));
        items7.add(new FaqSubItem("Do I have to enter and exit at the exact times on the parking pass?", answer));
        items7.add(new FaqSubItem("What do the prices mean?", answer));
        items7.add(new FaqSubItem("What if something goes wrong with my reservation?", answer));
        items7.add(new FaqSubItem("Why isn't my normal spot showing up in the app?", answer));
        data.add(new FaqItem("Account/Billing", items7));
    }

    @Override
    public void onChildClick(int groupPosition, int childPosition) {
        FaqSubItem item = (FaqSubItem) mAdapter.getChild(groupPosition, childPosition);
        if (item.getItemType() == FaqSubItem.ItemType.Collapsed) {
            List<FaqSubItem> items = data.get(groupPosition).getItems();
            for (int i = 0; i < items.size(); i ++) {
                if (i == childPosition) {
                    items.get(i).setItemType(FaqSubItem.ItemType.Expanded);
                } else {
                    items.get(i).setItemType(FaqSubItem.ItemType.Collapsed);
                }
            }
        } else {
            item.setItemType(FaqSubItem.ItemType.Collapsed);
        }
        mAdapter.notifyDataSetChanged();
    }
}
