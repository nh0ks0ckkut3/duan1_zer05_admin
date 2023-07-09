package com.example.duan1_quanlysalon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_quanlysalon.model.Employee;

import java.util.ArrayList;

public class EmployeeDAO {
    private Helper helper;

    public EmployeeDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<Employee> getListEmployee(){
        ArrayList<Employee> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM employee",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Employee(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getInt(3),cursor.getString(4),cursor.getInt(5),
                        cursor.getString(6),cursor.getInt(7), cursor.getString(8)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addEmployee(Employee employee){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",employee.getUserName());
        contentValues.put("passWord",employee.getPassWord());
        contentValues.put("name",employee.getName());
        contentValues.put("age",employee.getAge());
        contentValues.put("gender",employee.getGender());
        contentValues.put("dayStartWork",employee.getDayStartWork());
        contentValues.put("classify",employee.getClassify());

        long check =sqLiteDatabase.insert("employee", null, contentValues);
        return (!(check == -1));
    }

    public boolean editEmployee(Employee employee, String userNameEdit){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("passWord",employee.getPassWord());
        contentValues.put("name",employee.getName());
        contentValues.put("age",employee.getAge());
        contentValues.put("gender",employee.getGender());
        contentValues.put("dayStartWork",employee.getDayStartWork());
        contentValues.put("classify",employee.getClassify());
        long check = sqLiteDatabase.update("employee", contentValues, "userName= ?",new String[]{userNameEdit});
        return (!(check == -1));
    }

    public boolean deleteEmployee(String userName){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("employee", "userName= ?",new String[]{userName});
        return (!(check == -1));
    }
}