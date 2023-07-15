package com.example.duan1_quanlysalon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_quanlysalon.model.Bill;

import java.util.ArrayList;

public class BillDAO {
    private Helper helper;

    public BillDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<Bill> getListBill(){
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM bill",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Bill(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),
                        cursor.getInt(4), cursor.getString(5),cursor.getString(6),cursor.getInt(7)));
            }while (cursor.moveToNext());
        }

        return list;
    }
    public ArrayList<Bill> getListBooking(){
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM bill WHERE status like 'booking'",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Bill(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),
                        cursor.getInt(4), cursor.getString(5),cursor.getString(6),cursor.getInt(7)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addBill(Bill bill){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("phoneNumberCustomer",bill.getPhoneNumberCustomer());
        contentValues.put("userNameEmployee",bill.getUserNameEmployee());
        contentValues.put("idService",bill.getIdService());
        contentValues.put("idProduct",bill.getIdProduct());
        contentValues.put("time",bill.getTime());
        contentValues.put("status",bill.getStatus());
        contentValues.put("totalPrice",bill.getTotalPrice());

        long check =sqLiteDatabase.insert("bill", null, contentValues);
        return (!(check == -1));
    }

    public boolean editBill(int idBillEdit, Bill bill){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("phoneNumberCustomer",bill.getPhoneNumberCustomer());
        contentValues.put("userNameEmployee",bill.getUserNameEmployee());
        contentValues.put("idService",bill.getIdService());
        contentValues.put("idProduct",bill.getIdProduct());
        contentValues.put("time",bill.getTime());
        contentValues.put("status",bill.getStatus());
        contentValues.put("totalPrice",bill.getTotalPrice());
        long check = sqLiteDatabase.update("bill", contentValues, "id= ?",new String[]{String.valueOf(idBillEdit)});
        return (!(check == -1));
    }

    public boolean deleteBill(int idBill){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("bill", "id= ?",new String[]{String.valueOf(idBill)});
        return (!(check == -1));
    }
}
