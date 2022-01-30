package com.lrcortizo.android.mathematicdice.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "History";

    private static final int DATABASE_VERSION = 2;

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS history(fecha string PRIMARY KEY, resultado string NOT NULL)");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLite.class.getName(),
                "Upgrading database from version "
                        + oldVersion
                        + " to " + newVersion
                        + ", which will destroy all old data");
        db.beginTransaction();
        try {
            db.execSQL("DROP TABLE IF EXISTS history");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        this.onCreate(db);
    }


}
