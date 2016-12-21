package com.example.rsalesarm.events;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.rsalesarm.events.data.EventContract.EventEntry;

public class Events extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EVENT_LOADER =0;
    EventCAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        ListView eventlistview = (ListView) findViewById(R.id.list);

        View emptyView =findViewById(R.id.empty_view);
        eventlistview.setEmptyView(emptyView);

        mCursorAdapter = new EventCAdapter(this,null);
        eventlistview.setAdapter(mCursorAdapter);

        eventlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)

            {
                Intent i = new Intent(Events.this,CreateEvent.class);


                Uri currentEventUri = ContentUris.withAppendedId(EventEntry.CONTENT_URI,id);

                i.setData(currentEventUri);

                startActivity(i);

            }
        });
        getLoaderManager().initLoader(EVENT_LOADER,null,this);
    }



    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */


    private void insertEvent()
    {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(EventEntry.COLUMN_NAME, "Ram Leela");
        values.put(EventEntry.COLUMN_DATE, 121212);
        values.put(EventEntry.COLUMN_VENUE, EventEntry.VENUE_1);
        values.put(EventEntry.COLUMN_TIME, 1230);
// Insert the new row, returning the primary key value of the new row

        getContentResolver().insert(EventEntry.CONTENT_URI,values);
    }

    private void deleteAllEvents() {
        int rowsDeleted = getContentResolver().delete(EventEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertEvent();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllEvents();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection =
                {
                        EventEntry._ID,
                        EventEntry.COLUMN_NAME ,
                        EventEntry.COLUMN_DATE ,
                };
        return new CursorLoader(this, EventEntry.CONTENT_URI,projection,null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }


}



