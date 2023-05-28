package com.example.sep

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE if not exists posts(" +
                "id integer,"+
                "type text,"+
                "title text,"+
                "description text,"+
                "date text,"+
                "time text,"+
                "location text,"+
                "PRIMARY KEY (id, type)"+
                ");")
    }

    override fun onUpgrade (db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE if exists posts")
    }
}