package com.example.midexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.id_exam.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val radioGroupTicketType = findViewById<RadioGroup>(R.id.radioGroupTicketType)
        val editTextQuantity = findViewById<EditText>(R.id.editTextQuantity)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val genderId = radioGroupGender.checkedRadioButtonId
            val ticketTypeId = radioGroupTicketType.checkedRadioButtonId
            val quantity = editTextQuantity.text.toString()

            if (genderId == -1 || ticketTypeId == -1 || quantity.isEmpty()) {
                // 如果有未选择的选项或未填写数量，显示消息并返回
                // 这里可以根据需要自定义错误处理
                // 例如使用Toast显示错误消息
                return@setOnClickListener
            }

            val radioButtonGender = findViewById<RadioButton>(genderId)
            val radioButtonTicketType = findViewById<RadioButton>(ticketTypeId)

            val gender = radioButtonGender.text.toString()
            val ticketType = radioButtonTicketType.text.toString()
            val price = getPrice(ticketTypeId)

            // 将信息传递到下一个Activity
            val intent = Intent(this@MainActivity, TicketConfirmationActivity::class.java)
            intent.putExtra("gender", gender)
            intent.putExtra("ticketType", ticketType)
            intent.putExtra("quantity", quantity)
            intent.putExtra("price", price)
            startActivity(intent)
        }
    }

    // 辅助函数，根据选中的票种ID返回对应的价格
    private fun getPrice(ticketTypeId: Int): Int {
        return when (ticketTypeId) {
            R.id.radioButtonAdult -> 200
            R.id.radioButtonChild -> 150
            R.id.radioButtonStudent -> 100
            else -> 0
        }
    }
}
