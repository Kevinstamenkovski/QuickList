package com.example.QuickList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Blob;
import java.sql.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "quicklistdb";
    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    private static final String USER_TABLE_NAME = "Users";
    private static final String USER_TABLE_COLUMN_ID = "userID";
    private static final String USER_TABLE_COLUMN_PFP = "profile_picture";
    private static final String USER_TABLE_COLUMN_USERNAME = "username";
    private static final String USER_TABLE_COLUMN_FULLNAME = "fullname";
    private static final String USER_TABLE_COLUMN_EMAIL = "email";
    private static final String USER_TABLE_COLUMN_PASSWORD = "password";
    private static final String USER_TABLE_COLUMN_DOB = "DoB";   //Date of birth

    private static final String LIST_TABLE_NAME = "list";
    private static final String LIST_TABLE_COLUMN_ID = "listID";
    private static final String LIST_TABLE_COLUMN_LIST_NAME = "list_name";
    private static final String LIST_TABLE_COLUMN_LIST_IMAGE = "list_image";
    private static final String LIST_TABLE_COLUMN_PRODUCT_ID = "productID";

    private static final String PRODUCT_TABLE_NAME = "Products";
    private static final String PRODUCT_TABLE_COLUMN_ID = "productID";
    private static final String PRODUCT_TABLE_COLUMN_IMAGE = "image";
    private static final String PRODUCT_TABLE_COLUMN_NAME = "name";
    private static final String PRODUCT_TABLE_COLUMN_AMOUNT = "amount";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ USER_TABLE_NAME +" (" +
                ""+ USER_TABLE_COLUMN_ID +" int(5) PRIMARY KEY UNIQUE , " +
                ""+ USER_TABLE_COLUMN_PFP +" VARCHAR(150), " + //todo change to BLOB
                ""+ USER_TABLE_COLUMN_USERNAME +" VARCHAR(150) NOT NULL UNIQUE, " +
                ""+ USER_TABLE_COLUMN_FULLNAME +" VARCHAR(150) NOT NULL, " +
                ""+ USER_TABLE_COLUMN_PASSWORD +" VARCHAR(150) NOT NULL, " +
                ""+ USER_TABLE_COLUMN_EMAIL +" VARCHAR(150) UNIQUE NOT NULL, " +
                ""+ USER_TABLE_COLUMN_DOB +" VARCHAR(150))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ LIST_TABLE_NAME +" (" +
                ""+ LIST_TABLE_COLUMN_ID +" int(5) PRIMARY KEY NOT NULL UNIQUE , " +
                ""+ LIST_TABLE_COLUMN_LIST_IMAGE +" BLOB, " +
                ""+ LIST_TABLE_COLUMN_LIST_NAME +" VARCHAR NOT NULL UNIQUE, " +
                ""+ LIST_TABLE_COLUMN_PRODUCT_ID +" INT UNIQUE NOT NULL )");//FOREIGN KEY REFERENCE "+ PRODUCT_TABLE_NAME +"("+ PRODUCT_TABLE_COLUMN_ID +")
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ PRODUCT_TABLE_NAME +" (" +
                ""+ PRODUCT_TABLE_COLUMN_ID +" int(5) PRIMARY KEY NOT NULL UNIQUE, " +
                ""+ PRODUCT_TABLE_COLUMN_IMAGE +" BLOB, " +
                ""+ PRODUCT_TABLE_COLUMN_NAME +" VARCHAR NOT NULL, " +
                ""+ PRODUCT_TABLE_COLUMN_AMOUNT +" INT NOT NULL)");
    }

    public String createUser(String firstname, String lastname, String username, String email, String password, String profilepicture, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE_COLUMN_FULLNAME, firstname + " " + lastname);
        contentValues.put(USER_TABLE_COLUMN_EMAIL, email);
        contentValues.put(USER_TABLE_COLUMN_PASSWORD, password);
        contentValues.put(USER_TABLE_COLUMN_USERNAME, username);
        contentValues.put(USER_TABLE_COLUMN_PFP, profilepicture.toString());
        contentValues.put(USER_TABLE_COLUMN_DOB, date.toString());
        db.insert(USER_TABLE_NAME, null, contentValues);
        return "true";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor getUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM Users WHERE email = \"" + email + "\" AND password = \"" + password + "\"",null);
        Log.e(null, cursor.toString());
        return cursor;
    }

}
