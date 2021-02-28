package com.team8.universitybazaar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "bazaar.db";

    private static final String USERS_TABLE = "users";
    private static final String USER_ID = "USER_ID";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String PHONE = "PHONE";
    private static final String EMAIL = "EMAIL";
    private static final String STR_ADDRESS = "STR_ADDRESS";
    private static final String CITY = "CITY";
    private static final String STATE = "STATE";
    private static final String ZIPCODE = "ZIPCODE";

    private static final String SALES_EXCHANGE_TABLE = "sales_exchange";
    private static final String SALE_ID = "SALE_ID";
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    private static final String POST_DATE = "POST_DATE";
    private static final String ITEM_CATEGORY = "ITEM_CATEGORY";
    private static final String OFFER_TYPE = "OFFER_TYPE";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserTable = "CREATE TABLE " + USERS_TABLE
                + " ( " + USER_ID + " TEXT, "
                + USERNAME + " TEXT PRIMARY KEY, "
                + PASSWORD + " TEXT, "
                + FIRST_NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + PHONE + " TEXT, "
                + EMAIL + " TEXT, "
                + STR_ADDRESS + " TEXT, "
                + CITY + " TEXT, "
                + STATE + " TEXT, "
                + ZIPCODE + " TEXT " + " ) ";

        String createSalesTable = "CREATE TABLE " + SALES_EXCHANGE_TABLE
                + " ( " + SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT, "
                + ITEM_NAME + " TEXT, "
                + ITEM_DESCRIPTION + " TEXT, "
                + POST_DATE + " TEXT, "
                + ITEM_CATEGORY + " TEXT, "
                + OFFER_TYPE + " TEXT, "
                + " FOREIGN KEY ( " + USERNAME + " ) REFERENCES " + USERS_TABLE + " ( " + USERNAME + " ) )";

        Log.i(TAG, "createSalesTable: " + createSalesTable);

        db.execSQL(createUserTable);
        db.execSQL(createSalesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SALES_EXCHANGE_TABLE);

        onCreate(db);
    }

    public void addUser(User user) {

        SQLiteDatabase db = getWritableDatabase();

        /*long count = DatabaseUtils.queryNumEntries(db, USERS_TABLE);

        if (count == 0) {

            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + USERS_TABLE + "'");
        }*/

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, user.getUserId());
        contentValues.put(USERNAME, user.getUserName());
        contentValues.put(PASSWORD, user.getPassword());
        contentValues.put(FIRST_NAME, user.getFirstName());
        contentValues.put(LAST_NAME, user.getLastName());
        contentValues.put(PHONE, user.getPhone());
        contentValues.put(EMAIL, user.getEmail());
        contentValues.put(STR_ADDRESS, user.getStAddress());
        contentValues.put(CITY, user.getCity());
        contentValues.put(STATE, user.getState());
        contentValues.put(ZIPCODE, user.getZipCode());

        db.insert(USERS_TABLE, null, contentValues);
        db.close();
    }

    public boolean updateUser(User user) {

        try {

            SQLiteDatabase db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            //contentValues.put(USERNAME, user.getUserName());
            contentValues.put(PASSWORD, user.getPassword());
            contentValues.put(FIRST_NAME, user.getFirstName());
            contentValues.put(LAST_NAME, user.getLastName());
            contentValues.put(PHONE, user.getPhone());
            contentValues.put(EMAIL, user.getEmail());
            contentValues.put(STR_ADDRESS, user.getStAddress());
            contentValues.put(CITY, user.getCity());
            contentValues.put(STATE, user.getState());
            contentValues.put(ZIPCODE, user.getZipCode());

            db.update(USERS_TABLE, contentValues, USERNAME + "=?", new String[]{user.getUserName()});

            db.close();

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean ifExists(User user) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + "=?", new String[]{user.getUserName()});

        if (cursor.getCount() > 0) {

            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }

    public boolean ifEmailExists(User user) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + EMAIL + "=?", new String[]{user.getEmail()});

        if (cursor.getCount() > 0) {

            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }

    public boolean checkValidUser(User user) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + "=? AND " + PASSWORD + "=?", new String[]{user.getUserName(), user.getPassword()});

        if (cursor.getCount() > 0) {

            cursor.close();
            return true;
        } else {

            cursor.close();
            return false;
        }
    }

    public User getDetails(String userName) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + " = '" + userName + "'", null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(cursor.getColumnIndex(USER_ID)),
                cursor.getString(cursor.getColumnIndex(USERNAME)),
                cursor.getString(cursor.getColumnIndex(PASSWORD)),
                cursor.getString(cursor.getColumnIndex(FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(LAST_NAME)),
                cursor.getString(cursor.getColumnIndex(PHONE)),
                cursor.getString(cursor.getColumnIndex(EMAIL)),
                cursor.getString(cursor.getColumnIndex(STR_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(CITY)),
                cursor.getString(cursor.getColumnIndex(STATE)),
                cursor.getString(cursor.getColumnIndex(ZIPCODE)));

        cursor.close();

        return user;
    }


    public boolean isValidUserName(String userName) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + " = '" + userName + "'", null);

        if (cursor.getCount() == 1) {

            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void addListing(SaleItem item) {

        SQLiteDatabase db = getWritableDatabase();

        /*long count = DatabaseUtils.queryNumEntries(db, USERS_TABLE);

        if (count == 0) {

            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + USERS_TABLE + "'");
        }*/

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, item.getUserName());
        contentValues.put(ITEM_NAME, item.getItemName());
        contentValues.put(ITEM_DESCRIPTION, item.getItemDescription());
        contentValues.put(POST_DATE, item.getPostDate());
        contentValues.put(ITEM_CATEGORY, item.getItemCategory());
        contentValues.put(OFFER_TYPE, item.getOfferType());

        db.insert(SALES_EXCHANGE_TABLE, null, contentValues);
        db.close();
    }
}
