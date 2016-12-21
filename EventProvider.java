package com.example.rsalesarm.events.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rsalesarm.events.data.EventContract.EventEntry;

public class EventProvider extends ContentProvider {
    private EventDbHelper mDbHelper;

    private static final int EVENTS =100;
    private static final int EVENTS_ID=101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static
    {
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY,EventContract.PATH_EVENTS,EVENTS);
        sUriMatcher.addURI(EventContract.CONTENT_AUTHORITY,EventContract.PATH_EVENTS +"/#",EVENTS);
    }
    @Override
    public boolean onCreate() {
        mDbHelper = new EventDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match)
        {
            case EVENTS:


                cursor = database.query(EventEntry.TABLE_NAME, projection,selection,selectionArgs
                        ,null,null,sortOrder);
                break;
            case EVENTS_ID:
                selection = EventEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(EventEntry.TABLE_NAME, projection,selection,selectionArgs
                        ,null,null,sortOrder);

            default:
                throw new IllegalArgumentException("Cannot query unknown uri"+uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                return insertEvents(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Uri insertEvents(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(EventEntry.TABLE_NAME,null,values);


        String name = values.getAsString(EventEntry.COLUMN_NAME);
        if(name == null)
        {
            throw new IllegalArgumentException("Event Requires a name!");
        }
getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                // Delete all rows that match the selection and selection args
                return deleteEvents(uri, selection, selectionArgs);
            case EVENTS_ID:
                // Delete a single row given by the ID in the URI
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                // Delete all rows that match the selection and selection args
                return updateEvents(uri, values, selection, selectionArgs);
            case EVENTS_ID:
                // Delete a single row given by the ID in the URI
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.update(EventEntry.TABLE_NAME, values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }


    public int deleteEvents(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENTS:
                // Delete all rows that match the selection and selection args;
            int rowsDeleted = database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
            return rowsDeleted;
            case EVENTS_ID:
                // Delete a single row given by the ID in the URI
                selection = EventEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

    }




    private int updateEvents(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        if (values.containsKey(EventEntry.COLUMN_NAME)) {
            String name = values.getAsString(EventEntry.COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("event requires a name");
            }
        }

        if (values.containsKey(EventEntry.COLUMN_DATE)) {
            String date = values.getAsString(EventEntry.COLUMN_DATE);
            if (date ==null) {
                throw new IllegalArgumentException("event requires a date");
            }
        }

        if (values.containsKey(EventEntry.COLUMN_TIME)) {
            String time = values.getAsString(EventEntry.COLUMN_TIME);
            if (time == null) {
                throw new IllegalArgumentException("event requires a date");
            }
        }

        final int match = sUriMatcher.match(uri);
        // Delete a single row given by the ID in the URI
        selection = EventEntry._ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
           int rowsUpdated = database.delete(EventEntry.TABLE_NAME, selection, selectionArgs);
        return rowsUpdated;



}
}
