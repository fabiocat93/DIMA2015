package it.polimi.dima.moviesdiary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import it.polimi.dima.moviesdiary.R;
import it.polimi.dima.moviesdiary.model.Movie;

/**
 * Created by giovanniquattrocchi on 30/10/15.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movieList) {
        super(context, 0, movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // View Holder pattern

        Movie movie = getItem(position);


        MovieViewHolder movieViewHolder;

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.movie_row, null);

            movieViewHolder = new MovieViewHolder();

            movieViewHolder.titleTextView = (TextView) convertView.
                    findViewById(R.id.movie_title_text_view);
            movieViewHolder.directorTextView = (TextView) convertView
                    .findViewById(R.id.director_name_text_view);

            convertView.setTag(movieViewHolder);

        }
        else {
            movieViewHolder = (MovieViewHolder) convertView.getTag();
        }



        movieViewHolder.titleTextView.setText(movie.getTitle());
        movieViewHolder.directorTextView.setText(movie.getDirectorName());

        return convertView;

    }

    static class MovieViewHolder {
        TextView titleTextView;
        TextView directorTextView;
    }

}
