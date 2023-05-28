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
                "id integer primary key autoincrement,"+
                "type text,"+
                "title text,"+
                "description text,"+
                "date text,"+
                "time text,"+
                "location text,"+
                "image text"+
                ");")
        db.execSQL("CREATE TABLE if not exists lastlogin(" +
                "email text,"+
                "password text"+
                ");")
    }

    override fun onUpgrade (db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE if exists posts")
        db.execSQL("DROP TABLE if exists lastemail")
    }
}