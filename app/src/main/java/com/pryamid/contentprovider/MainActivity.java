package com.pryamid.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pryamid.contentprovider.connection.RestCall;
import com.pryamid.contentprovider.model.Status;
import com.pryamid.contentprovider.parser.EmployeeParser;
import com.pryamid.contentprovider.providers.BirthProvider;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    public void addBirthday(View view) {
        // Add a new birthday record
        ContentValues values = new ContentValues();
/*
        values.put(BirthProvider.BIRTH_TABLE_COLUMN.NAME, ((EditText)findViewById(R.id.name)).getText().toString());

        values.put(BirthProvider.BIRTH_TABLE_COLUMN.BIRTHDAY, ((EditText)findViewById(R.id.birthday)).getText().toString());

        Uri uri = getContentResolver().insert(BirthProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), "Provider Demo: " + uri.toString() + " inserted!", Toast.LENGTH_LONG).show();*/
    }

    public void deleteAllBirthdays (View view) {


        // delete all the records and the table of the database provider

/*     String URL = "content://" + BirthProvider.PROVIDER_NAME + "/friends";

        Uri friends = Uri.parse(URL);

        int count = getContentResolver().delete(friends, null, null);

        String countNum = "Birth Record : "+ count +" records are deleted.";

        Toast.makeText(getBaseContext(), "Hello", Toast.LENGTH_LONG).show();
*/
    }


    public void showAllBirthdays(View view) {
        // Show all the birthdays sorted by friend's name
        String URL = "content://" + BirthProvider.PROVIDER_NAME +"/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "name");
        String result = "Provider Demo Results:";

        if (!c.moveToFirst()) {
            Toast.makeText(this, result+" no content yet!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n" + c.getString(c.getColumnIndex(BirthProvider.BIRTH_TABLE_COLUMN.NAME)) +
                        " with id " +  c.getString(c.getColumnIndex(BirthProvider.BIRTH_TABLE_COLUMN.ID)) +
                        " has birthday: " + c.getString(c.getColumnIndex(BirthProvider.BIRTH_TABLE_COLUMN.BIRTHDAY));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }

    }

}
