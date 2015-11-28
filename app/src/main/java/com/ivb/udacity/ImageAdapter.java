package com.ivb.udacity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by S.Shivasurya on 11/28/2015 - androidStudio.
 */
public class ImageAdapter extends BaseAdapter {
    public Integer[] mThumbIds = {
            R.drawable.rich, R.drawable.rich2, R.drawable.rich3, R.drawable.rich4,
            R.drawable.rich, R.drawable.rich2, R.drawable.rich3, R.drawable.rich4
    };
    public String[] img = {"http://static.dnaindia.com/sites/default/files/2014/10/27/278392-jeff-bezos.jpg",
            "https://www.australiansolarquotes.com.au/wp-content/uploads/2015/08/17337481415_cb22284bab_z1.jpg",
            "http://data1.ibtimes.co.in/cache-img-0-450/en/full/580057/imgmark-zuckerberg.jpg", "http://c7.nrostatic.com/sites/default/files/uploaded/pic_related_082914_SM_Warren-Buffett-G_0.jpg",
            "http://static.dnaindia.com/sites/default/files/2014/05/28/239989-bg-srinivas-infy.jpg"};
    private LayoutInflater inflater;
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        ImageView picture;

        if (v == null) {
            v = inflater.inflate(R.layout.grid_layout_main, null);
            v.setTag(R.id.grid_image, v.findViewById(R.id.grid_image));
        }
        picture = (ImageView) v.getTag(R.id.grid_image);
        Picasso.with(mContext)
                .load(img[position])
                .fit()
                .into(picture);
        return v;
    }
}
