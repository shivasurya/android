package com.ivb.udacity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ivb.udacity.R;
import com.squareup.picasso.Picasso;

/**
 * Created by S.Shivasurya on 1/2/2016 - androidStudio.
 */
public class youtubeFragment extends Fragment {
    private String imageURL;

    public static final youtubeFragment newInstance(String message) {
        youtubeFragment f = new youtubeFragment();
        return f;
    }

    public void LoadImage(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.youtube_fragment, container, false);
        ImageView yimg = (ImageView) rootView.findViewById(R.id.youtubeimage);
        Picasso.with(getContext())
                .load(this.imageURL)
                .into(yimg);

        return rootView;
    }
}
