package com.example.duan1_quanlysalon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_quanlysalon.model.Customer;

import java.util.ArrayList;

public class CustomerDAO {
    private Helper helper;

    public CustomerDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<Customer> getListService(){
        ArrayList<Customer> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM customer",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Customer(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),
                        cursor.getString(4), cursor.getInt(5),cursor.getString(6)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addCustomer(Customer customer){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("phoneNumber",customer.getPhoneNumber());
        contentValues.put("passWord",customer.getPassWord());
        contentValues.put("name",customer.getName());
        contentValues.put("age",customer.getAge());
        contentValues.put("gender",customer.getGender());
        contentValues.put("totalSpend",customer.getTotalSpend());
        contentValues.put("address",customer.getAddress());

        long check =sqLiteDatabase.insert("customer", null, contentValues);
        return (!(check == -1));
    }

    public boolean editCustomer(String phoneNumberCustomerEdit, Customer customer){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("passWord",customer.getPassWord());
        contentValues.put("name",customer.getName());
        contentValues.put("age",customer.getAge());
        contentValues.put("gender",customer.getGender());
        contentValues.put("totalSpend",customer.getTotalSpend());
        contentValues.put("address",customer.getAddress());
        long check = sqLiteDatabase.update("customer", contentValues, "phoneNumber= ?",new String[]{phoneNumberCustomerEdit});
        return (!(check == -1));
    }

    public boolean deleteCustomer(String phoneNumberCustomerEdit){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("customer", "phoneNumber= ?",new String[]{phoneNumberCustomerEdit});
        return (!(check == -1));
    }
}
