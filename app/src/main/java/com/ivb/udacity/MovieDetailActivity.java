package com.ivb.udacity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {
    private final String TITLE = "title";
    private final String RELEASE_DATE = "release_date";
    private final String MOVIE_POSTER = "poster_path";
    private final String VOTE_AVERAGE = "vote_average";
    private final String PLOT_SYNOPSIS = "overview";
    private TextView plotView, voteAvg, releaseDate, Title;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String title, release, poster, vote, plot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        plotView = (TextView) findViewById(R.id.plot);
        voteAvg = (TextView) findViewById(R.id.voteAvg);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        Title = (TextView) findViewById(R.id.titlemain);
        imageView = (ImageView) findViewById(R.id.headerimage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMessage();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseBar);
        if (uiUpdate()) {
            Log.d("success", "success in updating UI");
        } else
            showErrorDialog();
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(MovieDetailActivity.this)
                .setCancelable(true)
                .setMessage("Sorry Something Went Wrong.Try again Later!")
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    private boolean uiUpdate() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(TITLE) && extras.containsKey(RELEASE_DATE) && extras.containsKey(MOVIE_POSTER) && extras.containsKey(VOTE_AVERAGE) && extras.containsKey(PLOT_SYNOPSIS)) {
                title = intent.getStringExtra(TITLE);
                release = intent.getStringExtra(RELEASE_DATE);
                poster = intent.getStringExtra(MOVIE_POSTER);
                vote = intent.getStringExtra(VOTE_AVERAGE);
                plot = intent.getStringExtra(PLOT_SYNOPSIS);
                poster = "http://image.tmdb.org/t/p/w500/" + poster;
            } else
                return false;
        } else
            return false;
        collapsingToolbarLayout.setTitle(title);
        plotView.setText(plot);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(release);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        releaseDate.setText(df.format(date).toString());
        Title.setText(title);
        voteAvg.setText(vote.toString());
        Picasso.with(this)
                .load(poster)
                .into(imageView);
        return true;
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (title != null && release != null && plot != null && vote != null && poster != null) {
            outState.putString(TITLE, title);
            outState.putString(RELEASE_DATE, release);
            outState.putString(PLOT_SYNOPSIS, plot);
            outState.putString(VOTE_AVERAGE, vote);
            outState.putString(MOVIE_POSTER, poster);
        }
    }

    private void shareMessage() {
        String shareBody = title + " " + plot + " " + "check out Movie Popular App!";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Movie App - shivasurya");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
