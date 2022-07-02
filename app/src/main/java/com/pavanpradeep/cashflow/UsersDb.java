package com.pavanpradeep.cashflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersDb extends SQLiteOpenHelper {
    public UsersDb(Context context){
        //Database name
        super(context,"Onke.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table in database
        db.execSQL("create table users(username Text primary key,password Text)");
        db.execSQL("create table products(name Text primary key,price Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Delete or drop database
//        db.execSQL("drop table if exists users");
        db.execSQL("create table if not exists users(username Text primary key,password Text)");
        db.execSQL("create table if not exists products(name Text primary key,price Text)");
    }
    public Boolean insertData(String username, String password){

        //Insert Data in to the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        //Store the data
        long result = db.insert("users",null,contentValues);

        // negative if it failed to insert data in db
        if(result == -1){
            return false;
        }
        else{
            return  true;
        }
    }
    public Boolean updatepassword(String username, String password){

        //Insert Data in to the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        //Store the data
        long result = db.update("users",contentValues,"username = ?",new String[]{username});

        // negative if it failed to insert data in db
        if(result == -1){
            return false;
        }
        else{
            return  true;
        }
    }
    public Boolean chkUserName(String username){
        //Check if username already exists
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ?",new String[]{username});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return  false;
        }
    }
    public Boolean chkUserNamePassword(String username,String password){
        //Check if password already exists
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ? and password = ?",new String[]{username,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return  false;
        }
    }
    //Products
    public Boolean addData(String name, String price){

        //Insert Data in to the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        //Store the data
        long result = db.insert("products",null,contentValues);

        // negative if it failed to insert data in db
        if(result == -1){
            return false;
        }
        else{
            return  true;
        }
    }
    public Boolean updateData(String name, String price){

        //Insert Data in to the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        //Cursor for selecting data
        //Cursor object will store the data
        Cursor cursor =db.rawQuery("Select * from products where name =?",new String[]{name});
        if(cursor.getCount()>0){
            //Store the data
            long result = db.update("products",contentValues,"name=?",new String[]{name});

            // negative if it failed to insert data in db
            if(result == -1){
                return false;
            }
            else{
                return  true;
            }
        }
        else{
            return false;
        }

    }
    public Boolean deleteData(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from products where name =?", new String[]{name});
        if (cursor.getCount() > 0) {
            //Store the data
            long result = db.delete("products", "name=?", new String[]{name});

            // negative if it failed to insert data in db
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Cursor getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from products", null);
        return cursor;
    }
}
