package com.example.hw2_ver2

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button)
        button1.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
        //取得database
        dbrw = hw2DBHelper(this).writableDatabase
        //宣告Adapter並連結Listview
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,items)
        findViewById<ListView>(R.id.ListView).adapter = adapter
        //設定監聽器
        setListener()
    }
    //建立Toast
    private fun showToast(text:String) = Toast.makeText(this,text, Toast.LENGTH_LONG).show()

    //清空
    private fun cleanEditText(){
        findViewById<EditText>(R.id.editTextTime).setText("")
        findViewById<EditText>(R.id.editText).setText("")
    }
    //設定監聽器
    private fun setListener(){
        val time = findViewById<EditText>(R.id.editTextTime2)
        findViewById<Button>(R.id.button2).setOnClickListener{
            val queryString = if (time.length() < 1)
                "SELECT * FROM myTable"
            else
                "SELECT * FROM myTable WHERE time LIKE '${time.text}'"
            val c = dbrw.rawQuery(queryString, null)
            c.moveToFirst() //從第一筆開始輸出
            items.clear() //清空舊資料
            showToast("共有${c.count}筆資料")
            for (i in 0 until c.count) {
                //加入新資料
                items.add("時間:${c.getString(0)}\t\t\t\t 姓名:${c.getString(1)}")
                c.moveToNext() //移動到下一筆
            }
            adapter.notifyDataSetChanged() //更新列表資料
            c.close() //關閉 Cursor
        }
    }

}