package com.pooja.myapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dumbledore on 10/28/16.
 */
public class ShoppingCartDB extends SQLiteOpenHelper {
    private final static String DB_NAME = "ShoppingCart.db";
    private final static String TABLE_NAME = "ShoppingCart";
    private final static int DB_VERSION = 1;
    private static final String CART_TABLE_CREATE =
            "CREATE TABLE " + ShoppingCartTable.tableName + " (" +
                    ShoppingCartTable.PRODUCT_NAME + " TEXT PRIMARY KEY, " +
                    ShoppingCartTable.PRICE + " INTEGER," + ShoppingCartTable.QUANTITY+ " INTEGER," +
                    ShoppingCartTable.IMAGE + " TEXT );";
    private static final String CART_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + DB_NAME;

    private static class ShoppingCartTable {
        private static final String tableName = "ShoppingCart";
        private static final String PRODUCT_NAME = "ProductName";
        private static final String PRICE = "Price";
        private static final String QUANTITY = "Rating";
        private static final String IMAGE = "Image";
        private ShoppingCartTable() {

        }
    }
    public  String getTableName() {
        return ShoppingCartTable.tableName;
    }
    public  String getPrice() {
        return ShoppingCartTable.PRICE;
    }

    public  String getProductName() {
        return ShoppingCartTable.PRODUCT_NAME;
    }
    public  String getImage() {
        return ShoppingCartTable.IMAGE;
    }
    public  String getQuantity() {
        return ShoppingCartTable.QUANTITY;
    }

    public ShoppingCartDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
    }

    public ShoppingCartDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CART_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
