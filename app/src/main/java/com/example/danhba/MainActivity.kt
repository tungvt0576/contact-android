package com.example.danhba

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var danhBaAdapter: DanhBaAdapter
    private var danhBaList = ArrayList<DanhBaModel>()
    var actionMode: ActionMode? = null
    val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.main_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            val selectedContact = danhBaAdapter.getSelectedContact() // Lấy mục được chọn từ adapter

            when (item?.itemId) {
                R.id.action_phone -> {
                    // Thực hiện cuộc gọi điện
                    val dialIntent =
                        Intent(Intent.ACTION_DIAL, Uri.parse("tel:${selectedContact?.phone}"))
                    startActivity(dialIntent)
                }

                R.id.action_message -> {
                    // Thực hiện gửi tin nhắn SMS
                    val smsIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("sms:${selectedContact?.phone}"))
                    startActivity(smsIntent)
                }

                R.id.action_email -> {
                    // Thực hiện gửi email
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:${selectedContact?.email}")
                        putExtra(Intent.EXTRA_SUBJECT, "Chủ đề email")
                        putExtra(Intent.EXTRA_TEXT, "Nội dung email")
                    }
                    startActivity(emailIntent)
                }
            }
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generateFakeData()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        danhBaAdapter = DanhBaAdapter(danhBaList, { item ->
            // Xử lý khi item được click
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("name", item.username)
            intent.putExtra("phone", item.phone)
            intent.putExtra("email", item.email)
            startActivity(intent)
        }, { item ->
            if (actionMode == null) {
                actionMode = startSupportActionMode(actionModeCallback)
            }
            true
        })
        recyclerView.adapter = danhBaAdapter
    }

    private fun generateFakeData() {
        danhBaList.add( DanhBaModel(1, "Ronaldo JR", "123456789", "john.doe@example.com"));
        danhBaList.add( DanhBaModel(2, "Jane Smith", "987654321", "jane.smith@example.com"));
        danhBaList.add( DanhBaModel(3, "Bob Johnson", "555666777", "bob.johnson@example.com"));
        danhBaList.add( DanhBaModel(4, "Alice Brown", "111223344", "alice.brown@example.com"));
        danhBaList.add( DanhBaModel(5, "David White", "999888777", "david.white@example.com"));
        danhBaList.add( DanhBaModel(6, "Eva Black", "444333222", "eva.black@example.com"));
        danhBaList.add( DanhBaModel(7, "Michael Green", "777888999", "michael.green@example.com"));
        danhBaList.add( DanhBaModel(8, "Sophia Gray", "222333444", "sophia.gray@example.com"));
        danhBaList.add( DanhBaModel(9, "William Taylor", "666555444", "william.taylor@example.com"));
    }
}