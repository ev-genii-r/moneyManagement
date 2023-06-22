package com.example.moneymanagement

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Environment
import android.text.format.Time
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanagement.adapter.TransactionDayAdapter
import com.example.moneymanagement.beans.Transaction
import com.example.moneymanagement.beans.TransactionDay
import com.example.moneymanagement.beans.TransactionMonth
import com.example.moneymanagement.storage.FileWorker
import com.example.moneymanagement.storage.JSONConvertor
import java.io.File
import java.time.Month


class TransactionTrackerActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    var aDay: Int = 0
    var aMonth: Int = 0
    var aYear: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_tracker)

        var today = Time(Time.getCurrentTimezone())
        today.setToNow()

        aDay = today.monthDay
        aMonth = today.month
        aYear = today.year

        var fileName = "${today.month}_${today.year}"
        val fileWorker = FileWorker()
        val jsonConvertor = JSONConvertor()
        var month = jsonConvertor.convertJsonToMonth(fileWorker.readFile(applicationContext, fileName))

        val incomeSum = findViewById<TextView>(R.id.income_sum)
        val expenseSum = findViewById<TextView>(R.id.expence_sum)
        val dayView = findViewById<TextView>(R.id.day)
        val dateView = findViewById<TextView>(R.id.date)
        val dateButton = findViewById<View>(R.id.period_button)
        val gear = findViewById<ImageView>(R.id.gear_tracker)
        val bar = findViewById<ImageView>(R.id.bar_tracker)
        val diagram = findViewById<ImageView>(R.id.diagram_tracker)

        dayView.text = today.monthDay.toString()
        dateView.text = "${today.month}.${today.year}"

        incomeSum.text = month.days.last().incomeSum.toString()
        expenseSum.text = month.days.last().expenceSum.toString()

        showStartList(dateView,dayView,incomeSum, expenseSum, aMonth, aYear, aDay)

        dateButton.setOnClickListener{
            showDialog(dateView,dayView,incomeSum, expenseSum)
        }

        bar.setOnClickListener {
            val intent = Intent(this@TransactionTrackerActivity, ExpenceCategoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setTransactionDayRecycler(transactions: MutableList<Transaction>) {
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        var transactionRecycler: RecyclerView = findViewById(R.id.transaction_day_recycler)
        transactionRecycler.layoutManager = layoutManager
        var transactionDayAdapter = TransactionDayAdapter(this, transactions)
        transactionRecycler.adapter = transactionDayAdapter
    }

    fun goToAnotherActivity(view: View) {
        val intent = Intent(this@TransactionTrackerActivity, CreateTransactionActivity::class.java)
        val selectedDate = Calendar.getInstance()

        intent.putExtra("date", "$aDay $aMonth $aYear")
        startActivity(intent)
    }

    private fun showStartList(dateView: TextView,
                              dayView: TextView,
                              incomeSum: TextView,
                              expenseSum: TextView,
                              month: Int, year: Int, dayOfMonth: Int){
        try {
            val fileName = "${month}_${year}"
            val fileWorker = FileWorker()
            val jsonConvertor = JSONConvertor()
            val monthTrans = jsonConvertor.convertJsonToMonth(fileWorker.readFile(applicationContext, fileName))

            dayView.text = dayOfMonth.toString()
            dateView.text = "${month}.${year}"
            incomeSum.text = monthTrans.days.find { it.day == dayOfMonth }?.incomeSum.toString()

            expenseSum.text = monthTrans.days.find { it.day ==  dayOfMonth}?.expenceSum.toString()

            setTransactionDayRecycler(monthTrans.days.find { it.day ==  dayOfMonth}?.transactions!!)
        }catch (e: Exception){
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("Ошибка")
//                builder.setMessage("За данный период не было произведено транзакций")
//                showDialog(dateView,dayView,incomeSum, expenseSum)
//                builder.create().show()
            setTransactionDayRecycler(TransactionMonth(month,
                year,
                mutableListOf<TransactionDay>(TransactionDay(0,0.0,0.0, mutableListOf())))
                .days.first().transactions)
            incomeSum.text = "0"
            expenseSum.text = "0"
        }
    }

    private fun showDialog(dateView: TextView, dayView: TextView, incomeSum: TextView, expenseSum: TextView) {

        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_calendar)

        val calendarView: CalendarView = dialog.findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            aDay = dayOfMonth
            aMonth = month
            aYear = year

            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            dialog.dismiss()

            try {
                val fileName = "${month}_${year}"
                val fileWorker = FileWorker()
                val jsonConvertor = JSONConvertor()
                val monthTrans = jsonConvertor.convertJsonToMonth(fileWorker.readFile(applicationContext, fileName))

                dayView.text = dayOfMonth.toString()
                dateView.text = "${month}.${year}"
                incomeSum.text = monthTrans.days.find { it.day == dayOfMonth }?.incomeSum.toString()

                expenseSum.text = monthTrans.days.find { it.day ==  dayOfMonth}?.expenceSum.toString()

                setTransactionDayRecycler(monthTrans.days.find { it.day ==  dayOfMonth}?.transactions!!)
            }catch (e: Exception){
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("Ошибка")
//                builder.setMessage("За данный период не было произведено транзакций")
//                showDialog(dateView,dayView,incomeSum, expenseSum)
//                builder.create().show()
                setTransactionDayRecycler(TransactionMonth(month,
                    year,
                    mutableListOf<TransactionDay>(TransactionDay(0,0.0,0.0, mutableListOf())))
                    .days.first().transactions)
                incomeSum.text = "0"
                expenseSum.text = "0"
            }
        }

        dialog.show()
    }
}