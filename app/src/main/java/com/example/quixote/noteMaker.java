package com.example.quixote;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class noteMaker extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    public static final String uid = null;


    public noteMaker(Context context) {
        super(context, "notes.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDb) {
        MyDb.execSQL("create Table notes(ID INTEGER PRIMARY KEY   AUTOINCREMENT,userid TEXT, title TEXT, details TEXT,image BLOB, image2 BLOB, image3 BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDb, int oldVersion, int newVersion) {
        MyDb.execSQL("drop Table if exists notes");
    }

    public  Boolean insertData(String userid, String title, String details, byte[] img, byte[] img2, byte[] img3){
        SQLiteDatabase MyDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid",userid);
        contentValues.put("title",title);
        contentValues.put("details",details);
        contentValues.put("image", img);
        contentValues.put("image2", img2);
        contentValues.put("image3", img3);
        long result = MyDb.insert("notes",null, contentValues);

        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public Cursor getdatabase(){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor c = mydb.rawQuery("select * from notes", null);
        return c;
    }
}
