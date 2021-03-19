package com.team8.universitybazaar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.team8.universitybazaar.model.SaleItem;
import com.team8.universitybazaar.model.Transaction;
import com.team8.universitybazaar.model.User;

import java.util.ArrayList;
import java.util.List;

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
    private static final String OFFER_PRICE = "OFFER_PRICE";
    private static final String IS_VISIBLE = "IS_VISIBLE";

    private static final String TRANSACTION_TABLE = "transaction_table";
    private static final String TRANSACTION_ID = "TRANSACTION_ID";
    private static final String CARD_NO = "CARD_NO";
    private static final String SECURITY_CODE = "SECURITY_CODE";
    private static final String NAME_ON_CARD = "NAME_ON_CARD";
    private static final String EXPIRY_DATE = "EXPIRY_DATE";
    private static final String TRANSACTION_DATE = "TRANSACTION_DATE";
    private static final String TRANSACTION_AMOUNT = "TRANSACTION_AMOUNT";

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
                + OFFER_PRICE + " INTEGER, "
                + IS_VISIBLE + " INTEGER, "
                + " FOREIGN KEY ( " + USERNAME + " ) REFERENCES " + USERS_TABLE + " ( " + USERNAME + " ) )";

        String createTransactionsTable = " CREATE TABLE " + TRANSACTION_TABLE
                + " ( " + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SALE_ID + " INTEGER, "
                + USERNAME + " TEXT, "
                + CARD_NO + " TEXT, "
                + SECURITY_CODE + " TEXT, "
                + NAME_ON_CARD + " TEXT, "
                + EXPIRY_DATE + " TEXT, "
                + ZIPCODE + " TEXT, "
                + TRANSACTION_DATE + " TEXT, "
                + TRANSACTION_AMOUNT + " TEXT, "
                + " FOREIGN KEY ( " + USERNAME + " ) REFERENCES " + USERS_TABLE + " ( " + USERNAME + " ), "
                + " FOREIGN KEY ( " + SALE_ID + " ) REFERENCES " + SALES_EXCHANGE_TABLE + " ( " + SALE_ID + " ) )";

        Log.i(TAG, "createSalesTable: " + createSalesTable);

        db.execSQL(createUserTable);
        db.execSQL(createSalesTable);
        db.execSQL(createTransactionsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SALES_EXCHANGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);

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

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + USERS_TABLE ,null);

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
        contentValues.put(OFFER_PRICE, item.getPrice());
        contentValues.put(IS_VISIBLE, item.getIsVisible());

        db.insert(SALES_EXCHANGE_TABLE, null, contentValues);
        db.close();
    }

    public ArrayList<SaleItem> getSaleItemList() {
        ArrayList<SaleItem> saleItemArrayList = new ArrayList<SaleItem>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String qry = "select * from " + SALES_EXCHANGE_TABLE + " where " + IS_VISIBLE + " = 1";
        try {
            Cursor c = sqLiteDatabase.rawQuery(qry, null);
            if (c.getCount() == 0) {
                return saleItemArrayList;
            } else {
                while (c.moveToNext()) {
                    int saleId = Integer.parseInt(c.getString(c.getColumnIndex(SALE_ID)));
                    String userName = c.getString(c.getColumnIndex(USERNAME));
                    String itemName = c.getString(c.getColumnIndex(ITEM_NAME));
                    String itemDescription = c.getString(c.getColumnIndex(ITEM_DESCRIPTION));
                    String postDate = c.getString(c.getColumnIndex(POST_DATE));
                    String itemCategory = c.getString(c.getColumnIndex(ITEM_CATEGORY));
                    String offerType = c.getString(c.getColumnIndex(OFFER_TYPE));
                    int price = c.getInt(c.getColumnIndex(OFFER_PRICE));
                    int isVisible = c.getInt(c.getColumnIndex(IS_VISIBLE));
                    SaleItem saleItem = new SaleItem( saleId, userName,  itemName,  itemDescription,  postDate,  itemCategory, offerType, price, isVisible);
                    saleItemArrayList.add(saleItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saleItemArrayList;
    }

    public void hideSoldItem(int saleId) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_VISIBLE, 0);
        sqLiteDatabase.update(SALES_EXCHANGE_TABLE, contentValues, SALE_ID + "=?", new String[]{String.valueOf(saleId)});
        sqLiteDatabase.close();
    }

    public void addTransaction(Transaction transaction) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SALE_ID, transaction.getSaleId());
        contentValues.put(USERNAME, transaction.getUserName());
        contentValues.put(CARD_NO, transaction.getCardNo());
        contentValues.put(SECURITY_CODE, transaction.getSecurityCode());
        contentValues.put(NAME_ON_CARD, transaction.getNameOnCard());
        contentValues.put(EXPIRY_DATE, transaction.getExpiryDate());
        contentValues.put(ZIPCODE, transaction.getZipCode());
        contentValues.put(TRANSACTION_DATE, transaction.getTransactionDate());
        contentValues.put(TRANSACTION_AMOUNT, transaction.getTransactionAmount());

        db.insert(TRANSACTION_TABLE, null, contentValues);
        db.close();
    }

    public List<Transaction> getPurchaseHistory(String userName) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TRANSACTION_TABLE + " WHERE " + USERNAME + " = '" + userName + "'";
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 0)
                return transactions;
            else {
                while (cursor.moveToNext()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(cursor.getInt(cursor.getColumnIndex(SALE_ID)));
                    transaction.setSaleId(cursor.getInt(cursor.getColumnIndex(SALE_ID)));
                    transaction.setUserName(cursor.getString(cursor.getColumnIndex(USERNAME)));
                    transaction.setCardNo(cursor.getString(cursor.getColumnIndex(CARD_NO)));
                    transaction.setSecurityCode(cursor.getString(cursor.getColumnIndex(SECURITY_CODE)));
                    transaction.setNameOnCard(cursor.getString(cursor.getColumnIndex(NAME_ON_CARD)));
                    transaction.setExpiryDate(cursor.getString(cursor.getColumnIndex(EXPIRY_DATE)));
                    transaction.setZipCode(cursor.getString(cursor.getColumnIndex(ZIPCODE)));
                    transaction.setTransactionDate(cursor.getString(cursor.getColumnIndex(TRANSACTION_DATE)));
                    transaction.setTransactionAmount(cursor.getString(cursor.getColumnIndex(TRANSACTION_AMOUNT)));
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
