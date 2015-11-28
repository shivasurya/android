package com.ivb.udacity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private String resultJSON = null;
    private String[] imgUrl = new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateView("same");
    }

    private void updateView(String type) {
        if (FetchMovie()) {
            gridView = (GridView) findViewById(R.id.movie_grid);
            gridView.setAdapter(new ImageAdapter(this, imgUrl));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setCancelable(true)
                .setMessage("Sorry Something Went Wrong!Try again Later!")
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

    private boolean FetchMovie() {
        NetworkConnection networkConnection = new NetworkConnection();
        try {
            resultJSON = networkConnection.execute().get();
            if (resultJSON != null) {
                JSONObject movie = new JSONObject(resultJSON);
                JSONArray movieDetails = movie.getJSONArray("results");
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
