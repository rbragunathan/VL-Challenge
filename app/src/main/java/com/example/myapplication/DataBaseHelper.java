package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Number.db";
    public static final String TABLE_NAME = "Number_table";

    public static final String COL_1 = "NAME";
    public static final String COL_2 = "NUMBER";

    public  DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("CREATE TABLE " + TABLE_NAME + "(NAME TEXT,NUMBER INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    public boolean insertData(String name,String number)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
    long result = db.insert(TABLE_NAME,null,contentValues);
    db.close();

    if (result == -1)
    {
        return  false;
    }else {
        return  true;
    }

    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String name, String number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
        int result = db.update(TABLE_NAME,contentValues,"NAME=?", new String[]{name});
        if(result>0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public Integer deleteData(String name)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        int i=db.delete(TABLE_NAME,"NAME=?",new String[]{name});
        return i;
    }



}
