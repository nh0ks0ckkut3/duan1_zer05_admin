package com.example.duan1_quanlysalon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_quanlysalon.model.Product;

import java.util.ArrayList;

public class ProductDAO {
    private Helper helper;

    public ProductDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<Product> getListService(){
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM product",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),
                        cursor.getInt(4), cursor.getString(5),cursor.getString(6)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addProduct(Product product){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",product.getName());
        contentValues.put("price",product.getPrice());
        contentValues.put("unit",product.getUnit());
        contentValues.put("amount",product.getAmount());
        contentValues.put("brand",product.getBrand());
        contentValues.put("classify",product.getClassify());

        long check =sqLiteDatabase.insert("product", null, contentValues);
        return (!(check == -1));
    }

    public boolean editProduct(int idProductEdit, String name, int price, String unit, int amount, String brand, String classify){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("unit",unit);
        contentValues.put("amount",amount);
        contentValues.put("brand",brand);
        contentValues.put("classify",classify);
        long check = sqLiteDatabase.update("product", contentValues, "id= ?",new String[]{String.valueOf(idProductEdit)});
        return (!(check == -1));
    }

    public boolean deleteProduct(int idProduct){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("product", "id= ?",new String[]{String.valueOf(idProduct)});
        return (!(check == -1));
    }
}