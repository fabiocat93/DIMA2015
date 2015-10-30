package it.polimi.dima.moviesdiary.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import it.polimi.dima.moviesdiary.R;
import it.polimi.dima.moviesdiary.adapters.MovieArrayAdapter;
import it.polimi.dima.moviesdiary.model.Movie;

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

    */

    private void setupMoviesView() {

        List<Movie> movies = Movie.getMovies();
        ListView movieListView = (ListView)
                findViewById(R.id.movies_list);
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
