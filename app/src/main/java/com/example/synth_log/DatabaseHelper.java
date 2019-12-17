package com.example.synth_log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "patches.db";
    public static final String TABLE_NAME = "patches_table";
    public static final String COL1 = "DEVICE";
    public static final String COL2 = "PATCH";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (DEVICE TEXT, PATCH TEXT, PRIMARY KEY (DEVICE, PATCH))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String device, String patch){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,device);
        contentValues.put(COL2,patch);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    public Cursor searchData(String device, String patch){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +" where DEVICE=? and PATCH=?",new String[] {device,patch});
        return res;
    }

    public Cursor getAllDevices(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(true, "patches_table", new String[] {"device","patch"}, null, null, "device", null, null, null);
        return res;
    }

    public Integer deleteData(String device, String patch){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "DEVICE=? and PATCH=?",new String[] {device,patch});
    }

    public Integer deleteDevice(String device){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "DEVICE=?",new String[] {device});
    }


    public Integer clearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null,null);
    }
}
