package com.example.mortgage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.alclabs.fasttablelayout.FastTableLayout
import com.example.mortgage.databinding.ActivityMain2Binding
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    private lateinit var binding : ActivityMain2Binding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // INPUTS FROM PAGE ONE
        val name = intent.getStringExtra("name").toString()
        val downPayment = intent.getStringExtra("downPayment").toString()
        val extraPayment1 = intent.getStringExtra("extraPayment").toString()
        val extraPayment2 = intent.getStringExtra("extraPayment2").toString()
        val amount = intent.getStringExtra("amt").toString()
        val years = intent.getStringExtra("yrs").toString()
        val rate = intent.getStringExtra("rate").toString()

        //Format of OUTPUT
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN

        val title = findViewById<TextView>(R.id.tabletitle)
        title.text = "Amortization Schedule"

        // TABLE STARTED
        // Table header
        val headers = arrayOf("Month","Interest","Principal", "Total", "Payment", "Extra", "Balance")

        // Data
        val extraPay = arrayOf("0".toDouble(), extraPayment1.toDouble(), extraPayment2.toDouble())
        var dataCollection = arrayOf(arrayOf(arrayOf("")))
        var interestCollection = arrayOf("")
        for (extraPayment in extraPay) {
            //FORMULA
            val monthDuration: Int = years.toInt() * 12
            var balance: Double = amount.toDouble() - downPayment.toDouble()
            var balanceFormatted = df.format(balance)
            val monthRate: Double = (rate.toDouble() / 100)/12
            var monthlyInterest: Double = balance * monthRate
            var monthInterest = df.format(monthlyInterest)
            val monthlyPayment: Double = (((monthRate * balance) / (1 - (1+monthRate).pow(-monthDuration))))
            val monthlyPaymentFormatted = df.format(monthlyPayment)
            var principalPaid = monthlyPayment - monthlyInterest
            var principal = df.format(principalPaid)
            var payment = monthlyPayment + extraPayment
            var paymentFormatted = df.format(payment)

            var data = arrayOf(arrayOf("1", monthInterest.toString(), principal.toString(),
                monthlyPaymentFormatted, paymentFormatted.toString(), extraPayment.toString(),
                balanceFormatted.toString()))

            var eachInterest = "0".toDouble()

            for (i in 2..monthDuration) {
                if (extraPayment > 0) {
                    balance -= payment - monthlyInterest
                } else {
                    balance -= principalPaid
                }
                balanceFormatted = df.format(balance)
                monthlyInterest = balance * monthRate
                monthInterest = df.format(monthlyInterest)
                eachInterest += monthlyInterest
                principalPaid = monthlyPayment - monthlyInterest
                principal = df.format(principalPaid)
                payment = monthlyPayment + extraPayment
                paymentFormatted = df.format(payment)
                if (balance > 0) {
                    data += arrayOf(i.toString(), monthInterest.toString(), principal.toString(),
                        monthlyPaymentFormatted, paymentFormatted.toString(), extraPayment.toString(),
                        balanceFormatted.toString())
                } else {
                    val last = data.last()
                    balance = last[6].toDouble()
                    monthlyInterest = balance * monthRate
                    principalPaid = balance - monthlyInterest
                    principal = df.format(principalPaid)
                    payment = balance + monthlyInterest
                    paymentFormatted = df.format(payment)

                    data += arrayOf(i.toString(), "0", principal.toString(),
                        paymentFormatted, paymentFormatted, "0",
                        "0")
                    break
                }
            }
            dataCollection += data
            interestCollection += eachInterest.toString()
        }

        val firstcase = findViewById<TextView>(R.id.case1)
        firstcase.text = "TABLE 1"

        val secondcase = findViewById<TextView>(R.id.case2)
        secondcase.text = "TABLE 2"

        val Table1 = FastTableLayout(this, binding.tableLayout,headers,dataCollection[2])
        Table1.SET_DEAFULT_HEADER_BORDER = true
        Table1.build()

        val Table2 = FastTableLayout(this, binding.tableLayout2,headers,dataCollection[3])
        Table2.SET_DEAFULT_HEADER_BORDER = true
        Table2.build()

        val interest = interestCollection[1].toDouble()
        val interestDifference1 = interestCollection[1].toDouble() - interestCollection[2].toDouble()
        val interestDifference2 = interestCollection[1].toDouble() - interestCollection[3].toDouble()
        val interest1 = df.format(interest)
        val diff1 = df.format(interestDifference1)
        val diff2 = df.format(interestDifference2)

        //OUTPUT
        val msg = findViewById<TextView>(R.id.output)
        msg.text = "  Dear " + name + "," +
                "\n  Your Paid Interest: $" + interest1.toString() +
                "\n  For Table 1 Saving in Interest: $" + diff1.toString() +
                "\n  For Table 2 Saving in Interest: $" + diff2.toString()
    }
}
