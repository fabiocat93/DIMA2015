package it.polimi.dima.moviesdiary.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.dima.moviesdiary.R;
import it.polimi.dima.moviesdiary.adapters.MovieArrayAdapter;
import it.polimi.dima.moviesdiary.model.Movie;
import it.polimi.dima.moviesdiary.rest.MovieRestService;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // R.<resource type>.<resource name>
        setContentView(R.layout.activity_movies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupMoviesView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    /*
    private void setupMoviesView(){

        List<String> movies = Arrays.asList("Mulholland Drive",
                "Interstellar", "Kill Bill");
        ListView movieListView = (ListView) findViewById(R.id.movies_list);

        ArrayAdapter<String> movieArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies);
        movieListView.setAdapter(movieArrayAdapter);

    }


    private void setupMoviesView(){

        List<Movie> movies = Movie.getMovies();
        ListView movieListView = (ListView) findViewById(R.id.movies_list);

        ArrayAdapter<Movie> movieArrayAdapter = new ArrayAdapter<Movie>(this,
                android.R.layout.simple_list_item_1, movies);
        movieListView.setAdapter(movieArrayAdapter);


    }

    private void setupMoviesView() {

        final List<Movie> movies = Movie.getMovies();
        ListView movieListView = (ListView)
                findViewById(R.id.movies_list);


        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = movies.get(position);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(MoviesActivity.this);

                CharSequence message = Html.fromHtml(String.format(getResources().
                                getString(R.string.click_on_movie),
                        movie.getTitle(),
                        movie.getDirectorName()));

                alertDialogBuilder
                        .setTitle(R.string.hello_user)
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

            }
        });

        MovieArrayAdapter movieArrayAdapter
                = new MovieArrayAdapter(this, movies);

        movieListView.setAdapter(movieArrayAdapter);

    }
    */

    private void setupMoviesView() {


        final List<Movie> movies = Collections
                .synchronizedList(new ArrayList<Movie>());

        final ProgressDialog progressDialog =
                ProgressDialog.show(this,
                        getResources().getString(R.string.wait),
                        getResources().getString(R.string.wait_movie_lib), true, false);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieRestService restService = retrofit
                .create(MovieRestService.class);

        final TypedArray movieIds = getResources()
                .obtainTypedArray(R.array.movie_imdb_ids);

        for (int i = 0; i < movieIds.length(); i++) {

            restService
                    .getMovie(movieIds.getString(i)).enqueue(
                    new Callback<Movie>() {
                        @Override
                        public void onResponse(Response<Movie> response,
                                               Retrofit retrofit) {

                            Movie movie = response.body();
                            movies.add(movie);

                            if(movies.size() == movieIds.length()){
                                loadListView(movies);
                                progressDialog.dismiss();
                            }


                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });


        }


    }


    private void loadListView(final List<Movie> movies){

        ListView movieListView = (ListView)
                findViewById(R.id.movies_list);


        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = movies.get(position);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(MoviesActivity.this);

                CharSequence message = Html.fromHtml(String.format(getResources().
                                getString(R.string.click_on_movie),
                        movie.getTitle(),
                        movie.getDirectorName()));

                alertDialogBuilder
                        .setTitle(R.string.hello_user)
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

            }
        });

        MovieArrayAdapter movieArrayAdapter
                = new MovieArrayAdapter(this, movies);

        movieListView.setAdapter(movieArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
