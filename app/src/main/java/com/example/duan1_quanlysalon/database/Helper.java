package com.example.duan1_quanlysalon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public Helper(@Nullable Context context) {
        super(context, "quanlysalon", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE employee(userName text primary key, passWord text, name text, age int, gender text, salary int, dayStartWork text, countDayOfMonth int, classify text)");
        db.execSQL("CREATE TABLE service(id integer primary key autoincrement, name text, price int, classifyEmployee text)");
        db.execSQL("CREATE TABLE product(id integer primary key autoincrement, name text, price int, unit text, amount int, brand text, classify text)");
        db.execSQL("CREATE TABLE customer(phoneNumber text primary key, passWord text, name text, age int, gender text, totalSpend int, address text)");
        db.execSQL("CREATE TABLE bill(id integer primary key autoincrement, phoneNumberCustomer text, userNameEmployee text, idService int, idProduct int, time text, status text, totalPrice int, " +
                "FOREIGN KEY (phoneNumberCustomer) REFERENCES customer(phoneNumber)," +
                "FOREIGN KEY (userNameEmployee) REFERENCES employee(userName)," +
                "FOREIGN KEY (idService) REFERENCES service(id)," +
                "FOREIGN KEY (idProduct) REFERENCES product(id))");


        db.execSQL("INSERT INTO employee VALUES('admin','12345','Nguyen Quang Liem',20,'nam',0,'03/07/2023',1,'admin')," +
                "('minhthi98','12345','Truong Minh Thi',25,'nam',0,'03/07/2023',1,'stylist')," +
                "('hotuyen95','12345','Thach Ho Tuyen',28,'nu',0,'03/07/2023',1,'skinner')");
        db.execSQL("INSERT INTO service VALUES(1,'cat toc',80,'stylist')," +
                "(2,'goi dau',45,'skinner')," +
                "(3,'uon toc',349,'stylist')," +
                "(4,'massage',299,'skinner')");
        db.execSQL("INSERT INTO product VALUES(1,'srm oxy',249,'chai',30,'oxy','srm')," +
                "(2,'sap vuot toc UNO wet',120,'lo',30,'UNO wet','sap')," +
                "(3,'sap vuot toc volcanic clay',340,'lo',40,'volcanic clay','sap')");
        db.execSQL("INSERT INTO customer VALUES('0337295209','12345','Doan Thanh Hoa',27,'nam',329,'quan 12')," +
                "('0345982112','12345','Bui Duc Hoang',27,'nam',385,'tan binh')," +
                "('0962498265','12345','Ha Trong Vy',27,'nam',469,'thu duc')");
        db.execSQL("INSERT INTO bill VALUES(1,'0337295209','minhthi98',1,1,'03/07/2023','da thanh toan',329)," +
                "(2,'0345982112','hotuyen95',2,3,'03/07/2023','da thanh toan',385)," +
                "(3,'0962498265','hotuyen95',4,3,'03/07/2023','booking',469)," +
                "(4,'0337287365','minhthi98',1,1,'13/07/2023','booking',349)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS employee");
            db.execSQL("DROP TABLE IF EXISTS service");
            db.execSQL("DROP TABLE IF EXISTS product");
            db.execSQL("DROP TABLE IF EXISTS customer");
            db.execSQL("DROP TABLE IF EXISTS bill");
            onCreate(db);
        }

    }
}
