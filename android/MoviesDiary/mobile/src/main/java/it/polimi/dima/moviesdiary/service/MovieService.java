package it.polimi.dima.moviesdiary.service;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import it.polimi.dima.moviesdiary.R;
import it.polimi.dima.moviesdiary.data.MovieCursor;
import it.polimi.dima.moviesdiary.data.MovieSQLiteRepository;
import it.polimi.dima.moviesdiary.model.Movie;
import it.polimi.dima.moviesdiary.rest.MovieRestInterface;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by giovanniquattrocchi on 13/11/15.
 */
public class MovieService {

    private static final String baseUrl = "http://www.omdbapi.com";

    private static MovieService instance;
    private MovieSQLiteRepository repository;
    private MovieRestInterface restInterface;

    private MovieService(Context context) {

        repository = new MovieSQLiteRepository(context);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        restInterface = retrofit.create(MovieRestInterface.class);

        fillWithDefault(context);
    }

    public static synchronized MovieService getInstance(Context context) {
        if (instance == null)
            instance = new MovieService(context);
        return instance;
    }

    public void getAllMovies(final Callback callback) {

        MovieCursor cursor = repository.findAll();

        final int count = cursor.getCount();

        final List<Movie> movies = Collections.
                synchronizedList(new ArrayList<Movie>());

        final AtomicBoolean alreadyFailed = new AtomicBoolean(false);


        while (cursor.moveToNext()) {

            final String userReview = cursor.getUserReview();
            final float userRating = cursor.getUserRating();

            restInterface.getMovie(cursor.getImdbId()).
                    enqueue(new retrofit.Callback<Movie>() {

                        @Override
                        public void onResponse(Response<Movie> response, Retrofit retrofit) {
                            Movie movie = response.body();
                            movie.setUserRating(userRating);
                            movie.setUserReview(userReview);
                            movies.add(movie);
                            if (movies.size() == count)
                                callback.onLoad(movies);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            if (alreadyFailed.compareAndSet(false, true)) {
                                callback.onFailure();
                            }
                        }
                    });

        }

        cursor.close();

    }

    public void getMovie(String imdbId, final Callback callback) {

        MovieCursor cursor = repository.findById(imdbId);
        cursor.moveToNext();

        final String userReview = cursor.getUserReview();
        final float userRating = cursor.getUserRating();

        restInterface.getMovie(cursor.getImdbId()).
                enqueue(new retrofit.Callback<Movie>() {

                    @Override
                    public void onResponse(Response<Movie> response, Retrofit retrofit) {
                        Movie movie = response.body();
                        movie.setUserRating(userRating);
                        movie.setUserReview(userReview);
                        callback.onLoad(Collections.singletonList(movie));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        callback.onFailure();
                    }
                });

        cursor.close();
    }

    public void deleteMovie(Movie movie){
        repository.delete(movie);
    }

    private void fillWithDefault(Context context){
        if(repository.findAll().getCount() == 0){
            TypedArray moviesIds = context.getResources()
                    .obtainTypedArray(R.array.movies_imdb_ids);
            TypedArray userRatings = context.getResources()
                    .obtainTypedArray(R.array.movies_imdb_ratings);
            TypedArray userReviews = context.getResources()
                    .obtainTypedArray(R.array.movies_imdb_reviews);

            for(int i = 0; i < moviesIds.length(); i++){
                repository.add(
                        new Movie(moviesIds.getString(i),
                                userRatings.getFloat(i, 0.0f),
                                userReviews.getString(i)));
            }
        }
    }


    public static interface Callback {
        public void onLoad(List<Movie> movies);
        public void onFailure();
    }

}
