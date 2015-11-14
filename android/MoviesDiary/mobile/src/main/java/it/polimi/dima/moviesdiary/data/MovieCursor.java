package it.polimi.dima.moviesdiary.data;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by giovanniquattrocchi on 13/11/15.
 */
public class MovieCursor extends CursorWrapper {

    public MovieCursor(Cursor cursor) {
        super(cursor);
    }

    public String getImdbId(){
        return getString(
                getColumnIndex(MovieDBContract.
                        MovieEntry.COLUMN_NAME_IMDB_ID));
    }

    public float getUserRating(){
        return getFloat(
                getColumnIndex(MovieDBContract.
                        MovieEntry.COLUMN_NAME_USER_RATING));
    }

    public String getUserReview(){
        return getString(
                getColumnIndex(MovieDBContract.
                        MovieEntry.COLUMN_NAME_USER_REVIEW));
    }


}
