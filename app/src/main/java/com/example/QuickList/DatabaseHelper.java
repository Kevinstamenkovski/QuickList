package com.example.QuickList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quicklistdb";

    public static final String USER_TABLE_NAME = "Users";
    public static final String USER_TABLE_COLUMN_ID = "userID";
    public static final String USER_TABLE_COLUMN_USERNAME = "username";
    public static final String USER_TABLE_COLUMN_FULLNAME = "fullname";
    public static final String USER_TABLE_COLUMN_EMAIL = "email";
    public static final String USER_TABLE_COLUMN_PASSWORD = "password";

    public static final String LIST_TABLE_NAME = "Lists";
    public static final String LIST_TABLE_COLUMN_ID = "listID";
    public static final String LIST_TABLE_COLUMN_LIST_NAME = "list_name";
    public static final String LIST_TABLE_COLUMN_USER_ID = "userID";

    private static final String PRODUCT_TABLE_NAME = "Products";
    private static final String PRODUCT_TABLE_COLUMN_ID = "productID";
    private static final String PRODUCT_TABLE_COLUMN_NAME = "name";
    private static final String PRODUCT_TABLE_COLUMN_AMOUNT = "amount";
    public static final String PRODUCT_TABLE_COLUMN_LIST_ID = "listID";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(null, "ONCREATE DATABASEHELPER");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ USER_TABLE_NAME +" (" +
                ""+ USER_TABLE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+ USER_TABLE_COLUMN_USERNAME +" VARCHAR(150) NOT NULL UNIQUE, " +
                ""+ USER_TABLE_COLUMN_FULLNAME +" VARCHAR(150) NOT NULL, " +
                ""+ USER_TABLE_COLUMN_PASSWORD +" VARCHAR(150) NOT NULL, " +
                ""+ USER_TABLE_COLUMN_EMAIL +" VARCHAR(150) /*UNIQUE*/ NOT NULL)"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ LIST_TABLE_NAME +" (" +
                ""+ LIST_TABLE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                ""+ LIST_TABLE_COLUMN_LIST_NAME +" VARCHAR NOT NULL UNIQUE, " +
                ""+ LIST_TABLE_COLUMN_USER_ID +" VARCHAR NOT NULL)");//FOREIGN KEY REFERENCE "+ PRODUCT_TABLE_NAME +"("+ PRODUCT_TABLE_COLUMN_ID +")

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ PRODUCT_TABLE_NAME +" (" +
                ""+ PRODUCT_TABLE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                ""+ PRODUCT_TABLE_COLUMN_NAME +" VARCHAR NOT NULL, " +
                ""+ PRODUCT_TABLE_COLUMN_LIST_ID +" INT NOT NULL,  " +
                ""+ PRODUCT_TABLE_COLUMN_AMOUNT +" INT NOT NULL)");
        db. execSQL("INSERT INTO list (list_name, userID) VALUES (\"testName1\", 1 ), (\"testName2\", 2), (\"testName3\",  1)");
        db. execSQL("INSERT INTO Products (name, amount, listID) VALUES (\"testNameProd1\", 2, 1), (\"testNameProd2\", 4, 2), (\"testNameProd3\",  1, 1)");
    }

    public String createUser(String firstname, String lastname, String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_TABLE_COLUMN_FULLNAME, firstname + " " + lastname);
        contentValues.put(USER_TABLE_COLUMN_EMAIL, email);
        contentValues.put(USER_TABLE_COLUMN_PASSWORD, password);
        contentValues.put(USER_TABLE_COLUMN_USERNAME, username);
        db.insert(USER_TABLE_NAME, null, contentValues);
        return "true";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ USER_TABLE_NAME +" WHERE "+USER_TABLE_COLUMN_EMAIL+" = \""+email+"\" AND "+USER_TABLE_COLUMN_PASSWORD+" = \""+password+"\"", null);
        return cursor;
    }

    public Cursor getListsByUserID(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT listID, list_name, list.userID " +
                "FROM list " +
                "JOIN Users ON Users.userID = list.userID " +
                "WHERE Users.userID = "+ID, null);
        return cursor;
    }

    public int getProductNumbers(int id){
        int productNumber = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(productID) " +
                "FROM Products " +
                "JOIN list ON list.listID = Products.productID " +
                "WHERE Products.listID = "+id, null);
        cursor.moveToLast();

        productNumber = cursor.getCount();
        return productNumber;
    }

    public Cursor getProducts(int listID, int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Products " +
                "JOIN list ON Products.listID = list.listID " +
                "JOIN Users ON Users.userID = list.userID " +
                "WHERE list.listID = " +listID+ " " +
                "AND Users.userID = " +userID,null);
        return cursor;
    }
    public boolean createList(String name, int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIST_TABLE_COLUMN_LIST_NAME, name);
        contentValues.put(LIST_TABLE_COLUMN_USER_ID, userID);
        db.insert(LIST_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean createProduct(String name, int amount, int listID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_TABLE_COLUMN_NAME, name);
        contentValues.put(PRODUCT_TABLE_COLUMN_AMOUNT, amount);
        contentValues.put(PRODUCT_TABLE_COLUMN_LIST_ID, listID);
        db.insert(PRODUCT_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateProduct(int productID){
        SQLiteDatabase db = this.getWritableDatabase();
//        db.update();
        return true;
    }
}
