package com.example.rsalesarm.events.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rsalesarm.events.data.EventContract.EventEntry;

import static java.sql.Types.INTEGER;

/**
 * Created by rsalesarm on 19/12/16.
 */

public class EventDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "MainEvents.db";

    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_EVENTS_TABLE = "CREATE TABLE " + EventEntry.TABLE_NAME + " ("
                + EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EventEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + EventEntry.COLUMN_TIME + " INTEGER NOT NULL, "
                + EventEntry.COLUMN_VENUE + " INTEGER NOT NULL, "
                + EventEntry.COLUMN_DATE + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_EVENTS_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
