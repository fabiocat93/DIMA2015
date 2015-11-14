package it.polimi.dima.moviesdiary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by giovanniquattrocchi on 13/11/15.
 */
public class MovieDBContract {

    public static final String DATABASE_NAME = "MovieDiary.db";
    public static final int DATABASE_VERSION = 1;

    public static final String VAR_CHAR_TYPE = " varchar";
    public static final String FLOAT_TYPE = " float";
    public static final String TEXT_TYPE = " text";

    public static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE IF NOT EXISTS "+MovieEntry.TABLE_NAME+" ("+
                    MovieEntry.COLUMN_NAME_IMDB_ID+VAR_CHAR_TYPE
                    +" PRIMARY KEY"+COMMA_SEP+
                    MovieEntry.COLUMN_NAME_USER_RATING+FLOAT_TYPE+COMMA_SEP+
                    MovieEntry.COLUMN_NAME_USER_REVIEW+TEXT_TYPE+" )";


    public static SQLiteDatabase getWritableDatabase(Context context){
        return new MovieDBHelper(context).getWritableDatabase();
    }

    public static SQLiteDatabase getReadableDatabase(Context context){
        return new MovieDBHelper(context).getReadableDatabase();
    }

    public static abstract class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME_IMDB_ID = "imdb_id";
        public static final String COLUMN_NAME_USER_RATING = "user_rating";
        public static final String COLUMN_NAME_USER_REVIEW = "user_review";

    }

    private static class MovieDBHelper extends SQLiteOpenHelper{


        public MovieDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MOVIE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
