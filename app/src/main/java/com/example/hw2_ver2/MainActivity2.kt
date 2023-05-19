package com.example.hw2_ver2

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

private lateinit var toolbar: Toolbar

class MainActivity2 : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //設定toolbar
        toolbar = findViewById(R.id.include2)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
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
    private fun showToast(text:String) = Toast.makeText(this,text,Toast.LENGTH_LONG).show()

    //清空
    private fun cleanEditText(){
        findViewById<EditText>(R.id.editTextTime).setText("")
        findViewById<EditText>(R.id.editText).setText("")
    }
    //設定監聽器
    private fun setListener(){
        val time = findViewById<EditText>(R.id.editTextTime)
        val name = findViewById<EditText>(R.id.editText)
        findViewById<Button>(R.id.button_add).setOnClickListener {
            if (time.length()<1 || name.length()<1)
                showToast("欄位請勿留空")
            else
                try {
                    dbrw.execSQL(
                        "INSERT INTO myTable(time, name) VALUES(?,?)",
                        arrayOf(time.text.toString(),name.text.toString())
                    )
                    showToast("新增: ${time.text},客人:${name.text}")
                    cleanEditText()
                }
                catch (e:java.lang.Exception){
                    showToast("新增失敗$e")
                }
        }
        findViewById<Button>(R.id.button_delete).setOnClickListener {
            if ( time.length()<1)
                showToast("時間請物留空")
            else
                try {
                    dbrw.execSQL("DELETE FROM myTable WHERE time LIKE '${time.text}'")
                    showToast("刪除: ${time.text}")
                    cleanEditText()
                }catch (e:Exception){
                    showToast("刪除失敗:$e")
                }
        }


    }
}