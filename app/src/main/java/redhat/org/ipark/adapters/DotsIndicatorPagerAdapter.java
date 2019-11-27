package redhat.org.ipark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import redhat.org.ipark.R;

public class DotsIndicatorPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Integer> data;

    public DotsIndicatorPagerAdapter(Context context, List<Integer> list) {
        this.mContext = context;
        this.data = list;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager, parent, false);

        ImageView imageView = view.findViewById(R.id.item_pager_image);
        imageView.setImageResource(data.get(position));

        parent.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup parent, int position, Object view) {
        parent.removeView((View) view);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
