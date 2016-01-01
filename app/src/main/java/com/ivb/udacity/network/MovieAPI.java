package com.ivb.udacity.network;

import com.ivb.udacity.modal.movieGeneral;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by S.Shivasurya on 1/1/2016 - androidStudio.
 */
public interface MovieAPI {

    //this method is to fetch the ALL movies with specific sort
    @GET("/3/discover/movie")
    void fetchMovies(
            @Query("sort_by") String mSort,
            @Query("api_key") String mApiKey,
            Callback<movieGeneral> cb
    );

}
