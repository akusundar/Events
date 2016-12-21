package com.example.rsalesarm.events.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rsalesarm on 21/12/16.
 */

public class EventContract {

    public static final String CONTENT_AUTHORITY = "com.example.rsalesarm.events";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_EVENTS = "event1";
    public static abstract class EventEntry implements BaseColumns
    {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EVENTS);

        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "event1";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_VENUE= "venue";
        public static final String COLUMN_TIME = "time";

        /**
         * Possible values for the venues of the event.
         */
        public static final int VENUE_1 = 0;
        public static final int VENUE_2 = 1;
        public static final int VENUE_3 = 2;


    }
}
