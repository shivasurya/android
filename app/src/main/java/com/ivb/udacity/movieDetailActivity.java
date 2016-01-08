package com.ivb.udacity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ivb.udacity.modal.movieGeneralModal;

/**
 * An activity representing a single movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link movieListActivity}.
 */
public class movieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        movieGeneralModal moviegeneralModal = (movieGeneralModal) intent.getSerializableExtra("DATA_MOVIE");

        if (savedInstanceState == null) {

            movieDetailFragment fragment = new movieDetailFragment();
            fragment.setMovieData(moviegeneralModal);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, movieListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}