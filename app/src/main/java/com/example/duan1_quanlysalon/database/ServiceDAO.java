package com.example.duan1_quanlysalon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;

public class ServiceDAO {
    private Helper helper;

    public ServiceDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<Service> getListService(){
        ArrayList<Service> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM service",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Service(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addService(Service service){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",service.getName());
        contentValues.put("price",service.getPrice());
        contentValues.put("classifyEmployee",service.getClassifyEmployee());

        long check =sqLiteDatabase.insert("service", null, contentValues);
        return (!(check == -1));
    }

    public boolean editService(int idServiceEdit, String name, int price, String classifyEmployee){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("classifyEmployee",classifyEmployee);
        long check = sqLiteDatabase.update("service", contentValues, "id= ?",new String[]{String.valueOf(idServiceEdit)});
        return (!(check == -1));
    }

    public boolean deleteService(int idService){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("service", "id= ?",new String[]{String.valueOf(idService)});
        return (!(check == -1));
    }
}