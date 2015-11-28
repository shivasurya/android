package com.ivb.udacity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by S.Shivasurya on 11/28/2015 - androidStudio.
 */
public class NetworkConnection extends AsyncTask<String, Void, String> {
    private final String ACCESS_TOKEN = "0a585d631bed6abcb1e16d13b3da675b";
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String MovieDBjson = null;

    @Override
    protected String doInBackground(String... params) {
        try {

            URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + ACCESS_TOKEN);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            MovieDBjson = buffer.toString();
            Log.d("result", MovieDBjson);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        return MovieDBjson;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
