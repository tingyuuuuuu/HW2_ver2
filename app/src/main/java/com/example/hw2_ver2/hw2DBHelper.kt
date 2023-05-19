package com.example.hw2_ver2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class hw2DBHelper(
    context: Context,
    name: String = database,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = ver
) : SQLiteOpenHelper(context, name, factory, version){
    companion object {
        private const val database = "hw2Database" //資料庫名稱
        private const val ver = 1 //資料庫版本
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE myTable(time text PRIMARY KEY, name text NOT NULL)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS myTable")
        onCreate(db)
    }

}