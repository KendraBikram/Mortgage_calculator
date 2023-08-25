package com.example.mortgage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputName = findViewById<EditText>(R.id.name)
        val inputAmount = findViewById<EditText>(R.id.amt)
        val inputDownPayment = findViewById<EditText>(R.id.downPayment)
        val extraPayment = findViewById<EditText>(R.id.extraPayment)
        val extraPayment2 = findViewById<EditText>(R.id.extraPayment2)
        val inputYears = findViewById<EditText>(R.id.yrs)
        val inputRate = findViewById<EditText>(R.id.rate)

        val firstActButton = findViewById<Button>(R.id.calculate)

        firstActButton.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            intent.putExtra("name", inputName.text.toString())
            intent.putExtra("amt", inputAmount.text.toString())
            intent.putExtra("downPayment", inputDownPayment.text.toString())
            intent.putExtra("extraPayment", extraPayment.text.toString())
            intent.putExtra("extraPayment2", extraPayment2.text.toString())
            intent.putExtra("yrs", inputYears.text.toString())
            intent.putExtra("rate", inputRate.text.toString())
            startActivity(intent)
        }
    }
}

