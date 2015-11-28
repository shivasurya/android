package com.ivb.udacity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    final CharSequence[] items = {" Most Popular ", " Highest Rated "};
    private final String MOST_POPULAR = "popularity.desc";
    private final String HIGHLY_RATED = "vote_count.desc";
    private final String TITLE = "title";
    private final String RELEASE_DATE = "release_date";
    private final String MOVIE_POSTER = "poster_path";
    private final String VOTE_AVERAGE = "vote_average";
    private final String PLOT_SYNOPSIS = "overview";
    private GridView gridView;
    private String resultJSON = null;
    private String[] imgUrl = new String[20];
    private AlertDialog choice;
    private String FLAG_CURRENT = MOST_POPULAR;
    private JSONArray movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateView(FLAG_CURRENT);
        if (savedInstanceState != null) {
            int temp = savedInstanceState.getInt("GRIDVIEWPOSITION");
            Log.d("temp", String.valueOf(temp));
            gridView.setSelection(temp);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView(FLAG_CURRENT);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateView(FLAG_CURRENT);
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
        if (FetchMovie()) {
            gridView = (GridView) findViewById(R.id.movie_grid);
            gridView.setAdapter(new ImageAdapter(this, imgUrl));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try {
                        JSONObject object = movieDetails.getJSONObject(position);
                        String title = object.getString(TITLE);
                        String poster = "" + object.getString(MOVIE_POSTER);
                        String release_date = object.getString(RELEASE_DATE);
                        String vote = object.getString(VOTE_AVERAGE);
                        String plot = object.getString(PLOT_SYNOPSIS);

                        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                        intent.putExtra(TITLE, title);
                        intent.putExtra(MOVIE_POSTER, poster);
                        intent.putExtra(RELEASE_DATE, release_date);
                        intent.putExtra(VOTE_AVERAGE, vote);
                        intent.putExtra(PLOT_SYNOPSIS, plot);

                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showErrorDialog();
                    }

                }
            });
        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setCancelable(true)
                .setMessage("Sorry Something Went Wrong.Try again Later!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int position = savedInstanceState.getInt("GRIDVIEWPOSITION");
        gridView.setSelection(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        int position = gridView.getFirstVisiblePosition();
        Log.d("append", String.valueOf(position));
        outState.putInt("GRIDVIEWPOSITION", position);
    }

    private boolean FetchMovie() {
        NetworkConnection networkConnection = new NetworkConnection();
        try {
            resultJSON = networkConnection.execute(FLAG_CURRENT).get();
            if (resultJSON != null) {
                JSONObject movie = new JSONObject(resultJSON);
                movieDetails = movie.getJSONArray("results");
                for (int i = 0; i < movieDetails.length(); i++) {
                    JSONObject temp_mov = movieDetails.getJSONObject(i);
                    imgUrl[i] = "http://image.tmdb.org/t/p/w185/" + temp_mov.getString("poster_path");
                }
                return true;
            } else
                return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }
}
