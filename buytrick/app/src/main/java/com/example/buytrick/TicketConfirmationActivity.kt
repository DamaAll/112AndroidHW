package com.example.buytrick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TicketConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_confirmation)

        // 獲取從 MainActivity 傳遞過來的資訊
        val gender = intent.getStringExtra("GENDER")
        val ticketType = intent.getStringExtra("TICKET_TYPE")
        val quantityStr = intent.getStringExtra("QUANTITY")

        // 將字串轉換為整數
        val quantity = quantityStr?.toIntOrNull() ?: 0

        // 設定顯示購票資訊的 TextView
        val textViewConfirmation = findViewById<TextView>(R.id.textViewConfirmation)
        textViewConfirmation.text = "性別：$gender\n票種：$ticketType\n張數：$quantity"

        // 計算總金額並設定到總金額的 TextView 上
        val totalAmountValue = calculateTotalAmount(ticketType, quantity)
        val textViewTotalAmountValue = findViewById<TextView>(R.id.textViewTotalAmountValue)
        textViewTotalAmountValue.text = totalAmountValue.toString()
    }

    // 計算總金額的函數
    private fun calculateTotalAmount(ticketType: String?, quantity: Int): Int {
        return when (ticketType) {
            "成人 500元" -> 500 * quantity
            "孩童 250元" -> 250 * quantity
            "學生 400元" -> 400 * quantity
            else -> 0
        }
    }
}
