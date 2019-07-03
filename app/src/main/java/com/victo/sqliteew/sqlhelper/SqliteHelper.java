package com.victo.sqliteew.sqlhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Environment;
import android.telephony.mbms.FileInfo;
import android.util.Log;

import com.victo.sqliteew.model.MyClass;
import com.victo.sqliteew.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DBname = "teSt";
    public static final String DBbaseTbl = "TblUser";
    public static final int DBversion = 1;
    static SQLiteDatabase dbThis;
    ListenerDB listener;
    DatabaseErrorHandler errorHandler = new ErrHandle();

    public static final String DBNull = null;


    public SqliteHelper(Context ctx) {
        super(ctx, DBname, null, DBversion);
        listener = new ListenerDB();
        int nn = getWritableDatabase().delete(DBbaseTbl, null, null);
        Log.e("<<delelte", nn + "");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            int nn = db.delete(DBbaseTbl, null, null);
            Log.e("<<delelte", nn + "");
        } catch (Exception ex) {
            Log.e("<<sqliet", ex.getMessage());
        }
    }

    public void copyDataOut(String filepath) {

        dbThis = getWritableDatabase();
        String sysfile = dbThis.getPath();
        Log.e("<<filedb", sysfile);
        FileInputStream fis;
        FileOutputStream fos;
        try {
            fis = new FileInputStream(sysfile);
            File f = Environment.getExternalStorageDirectory();
            String ffoutput = f.getAbsolutePath();
            f = new File(ffoutput, filepath);
            if (!f.exists())
                f.mkdir();

            fos = new FileOutputStream(ffoutput + "/thanhapp/" + DBname, false);
            int len = fis.available();
            byte[] mm = new byte[len];
            fis.read(mm);

            fos.write(mm);
            fos.flush();
            fos.close();
            fis.close();

        } catch (Exception ex) {
            Log.e("<<<Exppp", ex.getMessage());
        }

    }

    public void copyDataIn(String filepath) {

        dbThis = getReadableDatabase();
        String sysfile = dbThis.getPath();
        Log.e("<<filedb", sysfile);
        FileInputStream fis;
        FileOutputStream fos;
        try {
            File f = Environment.getExternalStorageDirectory();
            File fff = new File(f.getAbsolutePath(), filepath);
            String ffinn = fff.getAbsolutePath() + "/" + DBname;

            fis = new FileInputStream(ffinn);

            fos = new FileOutputStream(sysfile, false);
            int len = fis.available();
            byte[] mm = new byte[len];
            fis.read(mm);

            fos.write(mm);
            fos.flush();
            fos.close();
            fis.close();

        } catch (Exception ex) {
            Log.e("<<<Exppp", ex.getMessage());
        }

    }


    public Long insertData() {
        ContentValues values = new ContentValues();
        values.put("name", "thwnhd");
        values.put("phone", "015798889");
        Long n = -1L;
        dbThis = getWritableDatabase();
        try {
            n = dbThis.insert(DBbaseTbl, "name", values);
        } catch (Exception ex) {
            Log.e("<<<<dbblog>", ex.getMessage());
        }

        return n;
    }


    public MyClass QueryList(String table, String[] cols, String conditional, String[] Agrs, String colgroup, String colshave, String order, Class cl) {

        MyClass u = new MyClass<>();
        u.ListItem = new ArrayList();
        dbThis = getReadableDatabase();
        Cursor cursor = dbThis.query(table, cols, conditional, Agrs, colgroup, colshave, order);
        int index, type;
        while (cursor.moveToNext()) {
            final ContentValues cv = new ContentValues();
            for (String col : cursor.getColumnNames()) {
                index = cursor.getColumnIndex(col);
                type = cursor.getType(index);
                switch (type) {
                    case Cursor.FIELD_TYPE_NULL:
                        cv.put(col, DBNull);
                        break;
                    case Cursor.FIELD_TYPE_BLOB:
                        cv.put(col, cursor.getBlob(index));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        cv.put(col, cursor.getBlob(index));
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        cv.put(col, cursor.getInt(index));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        cv.put(col, cursor.getString(index));
                        break;

                }
            }

            u.ListItem.add(cv);
        }

        return u;
    }

    public User QueryDB() {
        User mm = new User();
        //dbThis = SQLiteDatabase.openDatabase(getDBfrom(),null,SQLiteDatabase.OPEN_READWRITE,errorHandler);//database in strorage
        dbThis = getReadableDatabase();// in system databases folder
        Cursor cursor = dbThis.query(DBbaseTbl, null, null, null, null, null, "name");
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            mm.setName(cursor.getString(cursor.getColumnIndex("name")));
            mm.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        }
        cursor.close();
        return mm;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

    private String getDBfrom() {
        String patfile = Environment.getExternalStorageDirectory().getPath() + "/thanhapp/" + DBname;
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

    public class ErrHandle implements DatabaseErrorHandler {
        @Override
        public void onCorruption(SQLiteDatabase dbObj) {
            Log.e("<<EErrrDB", dbObj.getPath());
        }
    }
}
