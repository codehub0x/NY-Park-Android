package redhat.org.ipark.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import redhat.org.ipark.R;

public class DotsIndicatorPagerAdapter extends PagerAdapter {

    public interface ClickListener {
        void onClickItem(int position);
    }

    private Context mContext;
    private List<String> data;
    private ClickListener mListener;

    public DotsIndicatorPagerAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.data = list;
    }

    public void setOnClickListener(ClickListener listener) {
        mListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager, parent, false);

        SimpleDraweeView imageView = view.findViewById(R.id.item_pager_image);
        Uri uri = Uri.parse(data.get(position));
        imageView.setImageURI(uri);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickItem(position);
                }
            }
        });

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
