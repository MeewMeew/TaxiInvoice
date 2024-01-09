package mew.id.vn.justtesting.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mew.id.vn.justtesting.Invoice;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "taxiinvoice.db", null, 1);
    }

    public Invoice addInvoice(Invoice i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("carNumber", i.getCarNumber());
        v.put("distance", i.getDistance());
        v.put("price", i.getPrice());
        v.put("discount", i.getDiscount());
        long id = db.insert("invoice", null, v);
        i.setId(id);
        db.close();
        return i;
    }

    public Invoice updateInvoice(Invoice i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("carNumber", i.getCarNumber());
        v.put("distance", i.getDistance());
        v.put("price", i.getPrice());
        v.put("discount", i.getDiscount());
        db.update("invoice", v, "id=?", new String[]{String.valueOf(i.getId())});
        db.close();
        return i;
    }

    public void deleteInvoice(Invoice i) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("invoice", "id=?", new String[]{String.valueOf(i.getId())});
        db.close();
    }

    public ArrayList<Invoice> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Invoice> invoices = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from invoice", null);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(("id")));
            String carNumber = cursor.getString(cursor.getColumnIndexOrThrow("carNumber"));
            double distance = cursor.getDouble(cursor.getColumnIndexOrThrow("distance"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int discount = cursor.getInt(cursor.getColumnIndexOrThrow("discount"));
            invoices.add(new Invoice(id, carNumber, distance, price, discount));
        }
        cursor.close();
        db.close();
        return invoices;
    }

    public Invoice getInvoice(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Invoice invoice = new Invoice();
        Cursor cursor = db.rawQuery("select * from invoice", null);
        while (cursor.moveToFirst()) {
            long _id = cursor.getLong(cursor.getColumnIndexOrThrow(("id")));
            String carNumber = cursor.getString(cursor.getColumnIndexOrThrow("carNumber"));
            double distance = cursor.getDouble(cursor.getColumnIndexOrThrow("distance"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int discount = cursor.getInt(cursor.getColumnIndexOrThrow("discount"));
            invoice.setId(_id);
            invoice.setCarNumber(carNumber);
            invoice.setDistance(distance);
            invoice.setPrice(price);
            invoice.setDistance(discount);
        }
        cursor.close();
        db.close();
        return invoice;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists invoice " +
                "(" +
                "id integer primary key autoincrement, " +
                "carNumber text, " +
                "distance real, " +
                "price real, " +
                "discount integer" +
        ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
