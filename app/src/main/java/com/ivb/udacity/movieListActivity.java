package com.ivb.udacity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ivb.udacity.adapter.movieGeneralAdapter;
import com.ivb.udacity.modal.Results;
import com.ivb.udacity.modal.movieGeneral;
import com.ivb.udacity.modal.movieGeneralModal;
import com.ivb.udacity.network.MovieAPI;
import com.ivb.udacity.network.NetworkAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * An activity representing a list of movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link movieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class movieListActivity extends AppCompatActivity {
    final CharSequence[] items = {" Most Popular ", " Highest Rated ", " My Favourites "};
    private final String MOST_POPULAR = "popularity.desc";
    private final String HIGHLY_RATED = "vote_count.desc";
    private final String ACCESS_TOKEN = "X";
    View recyclerView;
    private AlertDialog choice;
    private String FLAG_CURRENT = MOST_POPULAR;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private movieGeneral mMoviegeneralData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);


        recyclerView = findViewById(R.id.movie_list);

        assert recyclerView != null;

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }
        if (savedInstanceState == null)
            FetchMovie((RecyclerView) recyclerView, FLAG_CURRENT);
        else {
            if (savedInstanceState.getSerializable("adapter") != null) {
                mMoviegeneralData = (movieGeneral) savedInstanceState.getSerializable("adapter");
                drawLayout((RecyclerView) recyclerView, mMoviegeneralData);
            } else {
                FetchMovie((RecyclerView) recyclerView, FLAG_CURRENT);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapMenu:
                showChoices();
                break;
        }
        return true;
    }

    private void showChoices() {

        choice = new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                FetchMovie((RecyclerView) recyclerView, MOST_POPULAR);
                                break;
                            case 1:
                                FetchMovie((RecyclerView) recyclerView, HIGHLY_RATED);
                                break;
                            case 2:
                                break;
                        }
                        choice.dismiss();
                    }
                }).setTitle("Choose")
                .show();
    }


    protected void getPaneChanges() {
        mTwoPane = findViewById(R.id.movie_detail_container) != null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        getPaneChanges();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("adapter", mMoviegeneralData);

    }

    private void drawLayout(@NonNull final RecyclerView recyclerView, movieGeneral mMoviegeneral) {
        List<movieGeneralModal> movieGeneralModals = new ArrayList<movieGeneralModal>();
        Results[] mResult = mMoviegeneral.getResults();
        for (Results result : mResult) {
            movieGeneralModal obj = new movieGeneralModal(result.getTitle(), result.getPoster_path(), result.getVote_average()
                    , result.getId(), result.getVote_count(), result.getRelease_date(), result.getOverview());
            movieGeneralModals.add(obj);
            Log.d("value", result.getId());
            Log.d("value", String.valueOf(result.getOverview().length()));

        }
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int number;
        if (!mTwoPane) {
            number = width / 170;
        } else {
            number = (width / 2) / 170;
        }
        GridLayoutManager lLayout = new GridLayoutManager(getApplicationContext(), number);

        RecyclerView rView = recyclerView;
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        FragmentManager fm = getSupportFragmentManager();
        movieGeneralAdapter mMovieGeneralAdapter = new movieGeneralAdapter(getApplicationContext(), movieGeneralModals, mTwoPane, fm);
        rView.setAdapter(mMovieGeneralAdapter);

    }

    private void FetchMovie(@NonNull final RecyclerView recyclerView, String temp) {
        FLAG_CURRENT = temp;
        MovieAPI mMovieAPI = NetworkAPI.createService(MovieAPI.class);
        mMovieAPI.fetchMovies(FLAG_CURRENT, ACCESS_TOKEN, "ta", new Callback<movieGeneral>() {
            @Override
            public void success(movieGeneral mMoviegeneral, Response response) {
                mMoviegeneralData = mMoviegeneral;
                drawLayout(recyclerView, mMoviegeneral);
            }

            @Override
            public void failure(RetrofitError error) {
                // you should handle errors, too
                Log.d("error", error.getMessage());
            }
        });

    }


}
