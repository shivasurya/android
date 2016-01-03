package com.ivb.udacity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivb.udacity.adapter.youtubeAdapter;
import com.ivb.udacity.fragments.youtubeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single movie detail screen.
 * This fragment is either contained in a {@link movieListActivity}
 * in two-pane mode (on tablets) or a {@link movieDetailActivity}
 * on handsets.
 */
public class movieDetailFragment extends Fragment {

    private FragmentManager fm;
    public movieDetailFragment() {

    }

    public void setArgument(FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.youtubefragment);
            List<Fragment> fList = getYoutubeBanner();
            youtubeAdapter mYoutubeAdapter = new youtubeAdapter(getActivity().getSupportFragmentManager(), fList);
            pager.setAdapter(mYoutubeAdapter);
        }

    }

    protected List<Fragment> getYoutubeBanner() {
        String[] url = new String[]{"http://media1.santabanta.com/full1/Bollywood%20Movies/Guru/gur4e.jpg", "https://madaboutmoviez.files.wordpress.com/2012/04/guru2.jpg", "http://media1.santabanta.com/full1/bollywood%20movies/guru/gur3h.jpg", "http://farm1.static.flickr.com/173/367437103_917159d9f9_o.jpg"};
        List<Fragment> fragmentList = new ArrayList<>();
        youtubeFragment[] mYoutubeFragment = new youtubeFragment[4];
        for (int i = 0; i < 4; i++) {
            mYoutubeFragment[i] = youtubeFragment.newInstance("hello");
            mYoutubeFragment[i].LoadImage(url[i]);
            fragmentList.add(mYoutubeFragment[i]);
        }
        return fragmentList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}