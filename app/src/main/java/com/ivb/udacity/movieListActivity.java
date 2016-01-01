package com.ivb.udacity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private final String ACCESS_TOKEN = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx";
    private AlertDialog choice;
    private String FLAG_CURRENT = MOST_POPULAR;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.movie_list);

        assert recyclerView != null;

        if (findViewById(R.id.movie_detail_container) != null) {
            //this is two pane mode
            mTwoPane = true;
        }
        FetchMovie((RecyclerView) recyclerView);

    }

    private void FetchMovie(@NonNull final RecyclerView recyclerView) {
        MovieAPI mMovieAPI = NetworkAPI.createService(MovieAPI.class);
        mMovieAPI.fetchMovies(FLAG_CURRENT, ACCESS_TOKEN, new Callback<movieGeneral>() {
            @Override
            public void success(movieGeneral mMoviegeneral, Response response) {
                // here you do stuff with returned tasks
                List<movieGeneralModal> movieGeneralModals = new ArrayList<movieGeneralModal>();
                Results[] mResult = mMoviegeneral.getResults();
                for (Results result : mResult) {
                    movieGeneralModal obj = new movieGeneralModal(result.getTitle(), result.getPoster_path(), result.getVote_average());
                    movieGeneralModals.add(obj);
                }
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;
                int number = 3;
                if (!mTwoPane) {
                    number = width / 170;
                } else {
                    number = (width / 2) / 170;
                }
                GridLayoutManager lLayout = new GridLayoutManager(getApplicationContext(), number);

                RecyclerView rView = recyclerView;
                rView.setHasFixedSize(true);
                rView.setLayoutManager(lLayout);

                movieGeneralAdapter mMovieGeneralAdapter = new movieGeneralAdapter(getApplicationContext(), movieGeneralModals, mTwoPane);
                rView.setAdapter(mMovieGeneralAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                // you should handle errors, too
                Log.d("error", error.getMessage());
            }
        });

    }


}
