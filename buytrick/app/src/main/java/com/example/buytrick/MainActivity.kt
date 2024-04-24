package com.example.buytrick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyticket)

        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val radioGroupTicketType = findViewById<RadioGroup>(R.id.radioGroupTicketType)
        val editTextQuantity = findViewById<EditText>(R.id.editTextQuantity)
        val buttonPurchase = findViewById<Button>(R.id.buttonPurchase)

        buttonPurchase.setOnClickListener {
            // 獲取選擇的性別
            val genderRadioButton = findViewById<RadioButton>(radioGroupGender.checkedRadioButtonId)
            val gender = genderRadioButton.text.toString()

            // 獲取選擇的票種
            val ticketTypeRadioButton =
                findViewById<RadioButton>(radioGroupTicketType.checkedRadioButtonId)
            val ticketType = ticketTypeRadioButton.text.toString()

            // 獲取填寫的張數
            val quantity = editTextQuantity.text.toString().toInt()

            // 根據票種計算總金額
            val totalAmount = calculateTotalAmount(ticketType, quantity)

            // 創建 Intent 將購票資訊傳遞到 TicketConfirmationActivity
            val intent = Intent(this, TicketConfirmationActivity::class.java)
            intent.putExtra("GENDER", gender)
            intent.putExtra("TICKET_TYPE", ticketType)
            intent.putExtra("QUANTITY", quantity.toString())
            intent.putExtra("TOTAL_AMOUNT", totalAmount.toString())
            startActivity(intent)
        }
    }

    private fun calculateTotalAmount(ticketType: String, quantity: Int): Int {
        return when (ticketType) {
            "成人 500元" -> 500 * quantity
            "孩童 250元" -> 250 * quantity
            "學生 400元" -> 400 * quantity
            else -> 0
        }
    }
}
