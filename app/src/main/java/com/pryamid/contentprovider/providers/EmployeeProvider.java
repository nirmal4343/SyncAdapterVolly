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

import java.util.HashMap;

/**
 * Created by thakurn on 6/16/2015.
 */
public class EmployeeProvider extends ContentProvider {

    // fields for my content provider
    public static final String PROVIDER_NAME = "com.pyramid.provider.Employee";
    public static final String URL = "content://" + PROVIDER_NAME + "/employee";
    public static final Uri CONTENT_URI = Uri.parse(URL);



    // integer values used in content URI
    static final int EMPLOYEE = 1;
    static final int EMPLOYEE_ID = 2;

    DBHelper dbHelper;
    private SQLiteDatabase database;
    // projection map for a query
    private static HashMap<String, String> EmployeeMap;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "employee", EMPLOYEE);
        uriMatcher.addURI(PROVIDER_NAME, "employees/#", EMPLOYEE_ID);
    }



    @Override
    public boolean onCreate() {

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

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(DBHelper.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case EMPLOYEE:
                queryBuilder.setProjectionMap(EmployeeMap);
                break;
            case EMPLOYEE_ID:
                queryBuilder.appendWhere( EmployeeTable.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = EmployeeTable.FIRST_NAME;
        }

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            // Get all friend-birthday records
            case EMPLOYEE:
                return "vnd.android.cursor.dir/vnd.pyramid.employees";
            // Get a particular employee
            case EMPLOYEE_ID:
                return "vnd.android.cursor.item/vnd.pyramid.employee";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
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
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
