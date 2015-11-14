package it.polimi.dima.moviesdiary.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import it.polimi.dima.moviesdiary.model.Movie;

import static it.polimi.dima.moviesdiary.data.MovieDBContract.COMMA_SEP;
import static it.polimi.dima.moviesdiary.data.MovieDBContract.MovieEntry;
import static it.polimi.dima.moviesdiary.data.MovieDBContract.getWritableDatabase;

/**
 * Created by giovanniquattrocchi on 13/11/15.
 */
public class MovieSQLiteRepository {

    private SQLiteDatabase db;

    public MovieSQLiteRepository(Context context) {
        db = getWritableDatabase(context);
    }

    public void add(Movie movie) {
        db.execSQL("INSERT OR REPLACE INTO " +
                        MovieEntry.TABLE_NAME + "(" +
                        MovieEntry.COLUMN_NAME_IMDB_ID + COMMA_SEP +
                        MovieEntry.COLUMN_NAME_USER_RATING + COMMA_SEP +
                        MovieEntry.COLUMN_NAME_USER_REVIEW + ") " +
                        "VALUES(?" + COMMA_SEP + "?" + COMMA_SEP + "?)",
                new Object[]{movie.getImdbId(), movie.getUserRating(),
                        movie.getUserReview()});
    }

    public void delete(Movie movie) {
        db.execSQL("DELETE FROM " + MovieEntry.TABLE_NAME + " WHERE "
                        + MovieEntry.COLUMN_NAME_IMDB_ID + " = ?",
                new Object[]{movie.getImdbId()});
    }

    public MovieCursor findById(String id) {
        return new MovieCursor(db.rawQuery("SELECT * FROM " + MovieEntry.TABLE_NAME +
                        " WHERE " + MovieEntry.COLUMN_NAME_IMDB_ID + " = ?",
                new String[]{id}));
    }

    public MovieCursor findAll() {
        return new MovieCursor(
                db.rawQuery("SELECT * FROM " + MovieEntry.TABLE_NAME,
                        null));
    }

}
