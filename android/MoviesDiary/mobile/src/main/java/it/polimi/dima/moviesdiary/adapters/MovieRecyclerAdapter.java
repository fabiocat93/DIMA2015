package it.polimi.dima.moviesdiary.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.polimi.dima.moviesdiary.R;
import it.polimi.dima.moviesdiary.model.Movie;

/**
 * Created by giovanniquattrocchi on 13/11/15.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private List<Movie> moviesList;
    private Context context;

    public MovieRecyclerAdapter(List<Movie> moviesList){
        this.moviesList = moviesList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

      //  View itemView = LayoutInflater
      //          .from(context)
      //          .inflate(R.layout.movie_card, parent, false);

      //  return new MovieViewHolder(itemView);
    	  return null;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        protected TextView vTitle;
        protected TextView vDirector;
        protected TextView vUserRating;
        protected ImageView vPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            // vTitle = (TextView) itemView.findViewById(R.id.movie_title);
            // vDirector = (TextView) itemView.findViewById(R.id.movie_director);
            // vPoster = (ImageView) itemView.findViewById(R.id.movie_poster_image);
            // vUserRating = (TextView) itemView.findViewById(R.id.movie_user_rating);
        }
    }


}
