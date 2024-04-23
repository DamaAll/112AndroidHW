package com.example.midexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.id_exam.R

class TicketConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_confirmation)

        val textViewConfirmation = findViewById<TextView>(R.id.textViewConfirmation)

        // 获取上一个Activity传递的购票信息
        val intent = intent
        val gender = intent.getStringExtra("gender")
        val ticketType = intent.getStringExtra("ticketType")
        val quantity = intent.getStringExtra("quantity")
        val price = intent.getIntExtra("price", 0)

        // 构建购票信息字符串
        val confirmationMessage = "性別: $gender\n" +
                "票種: $ticketType\n" +
                "數量: $quantity\n" +
                "總價: ${price * quantity!!.toInt()} 元"

        // 显示购票信息
        textViewConfirmation.text = confirmationMessage
    }
}
