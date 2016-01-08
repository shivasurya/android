package com.ivb.udacity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivb.udacity.modal.movieGeneralModal;
import com.ivb.udacity.modal.review.movieReview;
import com.ivb.udacity.network.MovieAPI;
import com.ivb.udacity.network.NetworkAPI;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a single movie detail screen.
 * This fragment is either contained in a {@link movieListActivity}
 * in two-pane mode (on tablets) or a {@link movieDetailActivity}
 * on handsets.
 */
public class movieDetailFragment extends Fragment {

    private final String ACCESS_TOKEN = "X";
    private FragmentManager fm;
    private movieGeneralModal moviegeneralModal;
    private TextView reviewText, titleText, voteText, peoplesText, calendarText, plotSynopsis;
    private ImageView titleImage;
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
        if (savedInstanceState != null) {
            this.moviegeneralModal = (movieGeneralModal) savedInstanceState.getSerializable("DATA");
        }
        updateGeneralUI(rootView);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("DATA", moviegeneralModal);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setMovieData(movieGeneralModal moviegeneralModal) {
        this.moviegeneralModal = moviegeneralModal;
    }

    private void updateGeneralUI(View v) {
        titleText = (TextView) v.findViewById(R.id.titleText);
        voteText = (TextView) v.findViewById(R.id.rating);
        calendarText = (TextView) v.findViewById(R.id.calendar);
        peoplesText = (TextView) v.findViewById(R.id.people);
        titleImage = (ImageView) v.findViewById(R.id.titleimg);
        plotSynopsis = (TextView) v.findViewById(R.id.plotsynopsis);

        titleText.setText(moviegeneralModal.getTitle());
        voteText.setText(moviegeneralModal.getmVote());
        peoplesText.setText(moviegeneralModal.getmPeople());
        calendarText.setText(moviegeneralModal.getmReleaseDate());
        plotSynopsis.setText(moviegeneralModal.getmOverview());

        Picasso.with(getContext())
                .load(moviegeneralModal.getThumbnail())
                .into(titleImage);
        getMovieReview();
    }

    protected void getMovieReview() {
        MovieAPI mMovieAPI = NetworkAPI.createService(MovieAPI.class);
        mMovieAPI.fetchReview(ACCESS_TOKEN, this.moviegeneralModal.getmId(), new Callback<movieReview>() {

            @Override
            public void success(movieReview movieReview, Response response) {
                Log.d("moviereview", movieReview.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("error", error.toString());
            }
        });
    }

    protected void generateThumbnail() {

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