package com.tsofen.onthegoshopClient.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tsofen.onthegoshopClient.Beans.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDBHandler extends SQLiteOpenHelper {

    private static final String TAG = "CartDBHandler";

    private static final String DATABASE_NAME = "cart.db";
    private static final String TABLE_NAME = "cart_product";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRODUCT_ID = "productid";

    private static int size = 0;

    public CartDBHandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PRICE + " FLOAT," +
                COLUMN_AMOUNT + " INTEGER," +
                COLUMN_PRODUCT_ID + " INTEGER" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addProduct(Product product){
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "='" + product.getName() + "';";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            return false;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_AMOUNT, product.getAmount());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_ID, product.getId());
        db.insert(TABLE_NAME, null, values);
        size++;
        db.close();
        return true;
    }

    public void deleteProduct (String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "='" + productName + "';");
        db.close();
        size--;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1" ;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            float price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE));
            int amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
            Product product = new Product(name, amount, price);
            product.setId(id);
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public void deleteProducts(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
        size = 0;
    }

    public int getSize() {
        return size;
    }
}
