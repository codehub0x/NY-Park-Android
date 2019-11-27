package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import redhat.org.ipark.R;
import redhat.org.ipark.models.FaqItem;
import redhat.org.ipark.models.FaqSubItem;

public class FAQAdapter extends BaseExpandableListAdapter {

    public interface ClickListener {
        void onChildClick(int groupPosition, int childPosition);
    }

    private Context mContext;
    private ClickListener mListener;
    private List<FaqItem> data;

    public FAQAdapter(Context context, List<FaqItem> list, ClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.data = list;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FaqItem item = (FaqItem) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_faq_group, null);
        }

        TextView textTitle = convertView.findViewById(R.id.item_faq_group_text);
        textTitle.setText(item.getTitle());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        FaqSubItem item = (FaqSubItem) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_faq_child, null);
        }

        LinearLayout mainLayout = convertView.findViewById(R.id.item_faq_child_layout);
        LinearLayout topLayout = convertView.findViewById(R.id.item_faq_child_layout_top);
        TextView textView = convertView.findViewById(R.id.item_faq_child_text);
        TextView subTextView = convertView.findViewById(R.id.item_faq_child_subtext);
        ImageView imageArrow = convertView.findViewById(R.id.item_faq_child_arrow);

        textView.setText(item.getQuestion());
        subTextView.setText(item.getAnswer());
        if (item.getItemType() == FaqSubItem.ItemType.Expanded) {
            mainLayout.setSelected(true);
            textView.setSelected(true);
            imageArrow.setSelected(true);
            subTextView.setVisibility(View.VISIBLE);
            imageArrow.setImageResource(R.drawable.ic_arrow_up_black_24dp);
        } else {
            mainLayout.setSelected(false);
            textView.setSelected(false);
            imageArrow.setSelected(false);
            subTextView.setVisibility(View.GONE);
            imageArrow.setImageResource(R.drawable.ic_arrow_down_black_24dp);
        }

        topLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onChildClick(groupPosition, childPosition);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
