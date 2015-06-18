package com.pryamid.contentprovider.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import java.util.HashMap;

/**
 * Created by thakurn on 6/16/2015.
 */
public class BirthProvider extends ContentProvider{


    // fields for my content provider
    public static final String PROVIDER_NAME = "com.pyramid.provider.Birthday";
    public static final String URL = "content://" + PROVIDER_NAME + "/friends";
    public static final Uri CONTENT_URI = Uri.parse(URL);



    // integer values used in content URI
    static final int FRIENDS = 1;
    static final int FRIENDS_ID = 2;

    DBHelper dbHelper;
    private SQLiteDatabase database;
    // projection map for a query
    private static HashMap<String, String> BirthMap;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "friends", FRIENDS);
        uriMatcher.addURI(PROVIDER_NAME, "friends/#", FRIENDS_ID);
    }



    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        Context context = getContext();
        dbHelper = new DBHelper(context);
        // permissions to be writable
        database = dbHelper.getWritableDatabase();

        if(database == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(DBHelper.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case FRIENDS:
                queryBuilder.setProjectionMap(BirthMap);
                break;
            case FRIENDS_ID:
                queryBuilder.appendWhere( BIRTH_TABLE_COLUMN.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = BIRTH_TABLE_COLUMN.NAME;
        }

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        long row = database.insert(DBHelper.TABLE_NAME, "", values);

        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        int count = 0;

        switch (uriMatcher.match(uri)){
            case FRIENDS:
                count = database.update(DBHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FRIENDS_ID:
                count = database.update(DBHelper.TABLE_NAME, values, BIRTH_TABLE_COLUMN.ID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        int count = 0;

        switch (uriMatcher.match(uri)){
            case FRIENDS:
                // delete all the records of the table
                count = database.delete(DBHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case FRIENDS_ID:
                String id = uri.getLastPathSegment();	//gets the id
                count = database.delete( DBHelper.TABLE_NAME, BIRTH_TABLE_COLUMN.ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            // Get all friend-birthday records
            case FRIENDS:
                return "vnd.android.cursor.dir/vnd.pyramid.friends";
            // Get a particular friend
            case FRIENDS_ID:
                return "vnd.android.cursor.item/vnd.pyramid.friends";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }


    public class BIRTH_TABLE_COLUMN {
        // fields for the database
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String BIRTHDAY = "birthday";

    }

}
