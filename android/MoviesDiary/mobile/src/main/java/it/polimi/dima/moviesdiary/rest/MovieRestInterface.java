package it.polimi.dima.moviesdiary.rest;

import it.polimi.dima.moviesdiary.model.Movie;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by giovanniquattrocchi on 06/11/15.
 */
public interface MovieRestInterface {

    @GET("/?")
    public Call<Movie> getMovie(@Query("i") String imdbId);

}
