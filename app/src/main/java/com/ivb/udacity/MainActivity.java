package com.ivb.udacity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

public class MainActivity extends AppCompatActivity {
    final CharSequence[] items = {" Most Popular ", " Highest Rated ", " My Favourites "};
    private final String MOST_POPULAR = "popularity.desc";
    private final String HIGHLY_RATED = "vote_count.desc";
    private final String ACCESS_TOKEN = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private AlertDialog choice;
    private String FLAG_CURRENT = MOST_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateView(FLAG_CURRENT);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  updateView(FLAG_CURRENT);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //    updateView(FLAG_CURRENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        choice = new AlertDialog.Builder(MainActivity.this)
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                updateView(MOST_POPULAR);
                                break;
                            case 1:
                                updateView(HIGHLY_RATED);
                                break;
                        }
                        choice.dismiss();
                    }
                }).setTitle("Choose")
                .show();
    }

    private void updateView(String type) {
        FLAG_CURRENT = type;
        FetchMovie();
    }


    private void FetchMovie() {
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
                int number = width / 170;

                GridLayoutManager lLayout = new GridLayoutManager(MainActivity.this, number);

                RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerview);
                rView.setHasFixedSize(true);
                rView.setLayoutManager(lLayout);

                //    movieGeneralAdapter mMovieGeneralAdapter = new movieGeneralAdapter(MainActivity.this, movieGeneralModals);
                //  rView.setAdapter(mMovieGeneralAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                // you should handle errors, too
                Log.d("error", error.getMessage());
            }
        });


    }
}
