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

    private LayoutInflater inflater;
    private Context mContext;
    private String[] img;

    public ImageAdapter(Context c, String[] img) {
        mContext = c;
        this.img = img;
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
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        picture = (ImageView) v.getTag(R.id.grid_image);
        Picasso.with(mContext)
                .load(img[position])
                .into(picture);
        return v;
    }
}
