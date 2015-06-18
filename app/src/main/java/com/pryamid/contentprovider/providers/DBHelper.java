package com.pryamid.contentprovider.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by thakurn on 6/16/2015.
 */

// class that creates and manages the provider's database

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "employees";
    static final String TABLE_NAME = "employee";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " firstName TEXT NOT NULL, " +
                    " lastName TEXT NOT NULL, " +
                    " managerId TEXT NOT NULL, " +
                    " department TEXT NOT NULL, " +
                    " officePhone TEXT NOT NULL, " +
                    " cellPhone TEXT NOT NULL, " +
                    " email TEXT NOT NULL, " +
                    " city TEXT NOT NULL, " +
                    " title TEXT NOT NULL, " +
                    " picture TEXT NOT NULL);";

    public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(CREATE_TABLE);
    }

        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.w(DBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
            onCreate(db);
    }

}



