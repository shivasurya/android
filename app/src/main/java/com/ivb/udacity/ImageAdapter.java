package com.ivb.udacity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by S.Shivasurya on 11/28/2015 - androidStudio.
 */
public class ImageAdapter extends BaseAdapter {
    public Integer[] mThumbIds = {
            R.drawable.rich, R.drawable.rich2, R.drawable.rich3, R.drawable.rich4,
            R.drawable.rich, R.drawable.rich2, R.drawable.rich3, R.drawable.rich4
    };
    private LayoutInflater inflater;

    public ImageAdapter(Context c) {
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
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
        picture.setImageResource(mThumbIds[position]);
        return v;
    }
}
