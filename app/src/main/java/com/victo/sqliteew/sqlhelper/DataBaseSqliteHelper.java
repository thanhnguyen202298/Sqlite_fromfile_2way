package com.victo.sqliteew.sqlhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Environment;
import android.telephony.mbms.FileInfo;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class DataBaseSqliteHelper {
    SQLiteDatabase db;
    String pathFileStr = "/data/data/" + "packageName" + "/databases/";
    String dbName = "thanhTest";
    SQLiteTransactionListener listener;

    public DataBaseSqliteHelper(Context context, String filename) {
        try {
            db = SQLiteDatabase.openDatabase(getFileLocal(filename),null, SQLiteDatabase.CREATE_IF_NECESSARY);
            listener = new ListenerDB();
        } catch (Exception ex) {
            Log.e("<<SqLite", ex.getMessage());
        }
    }

    public void addTable(String tblName) {
        if(db == null)Log.e("<<<Null DB", "null rooi");
        db.beginTransactionWithListener(listener);
        try {

            db.execSQL("create table " + tblName +
                    "(recID integer primary key autoincrement, name text, phone text)");
            ContentValues values = new ContentValues();
            values.put("name", "thwnhd");
            values.put("phone", "015798889");

            //db.insert(tblName,"name", values);
        } catch (Exception ex) {
            Log.e("<<sqliet", ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void createOrUpdateFile(String fileName, String content, boolean update) {
        FileOutputStream outputStream;

        try {
            File file = Environment.getDataDirectory();
            outputStream = new FileOutputStream(file.getAbsolutePath() + "/" + fileName, update);//(fileName, Context.MODE_APPEND);
            Log.e("<<<thajh", file.getAbsolutePath());
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Log.e("<<fileeddd", e.getMessage());
        }
    }

    private String getFileLocal(String filename) {
        String patfile = Environment.getExternalStorageDirectory().getPath() + "/thanh.App/" + filename;
        return patfile;
    }

    public class ListenerDB implements SQLiteTransactionListener {
        @Override
        public void onBegin() {
            Log.e("<<stringBeginSQK", "start");
        }

        @Override
        public void onCommit() {
            Log.e("<<committed", "start");

        }

        @Override
        public void onRollback() {

            Log.e("<<rollback", "start");

        }
    }
}
