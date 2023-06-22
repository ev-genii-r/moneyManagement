package com.example.moneymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import com.example.moneymanagement.beans.Transaction
import com.example.moneymanagement.beans.TransactionDay
import com.example.moneymanagement.enums.CategoryExpence
import com.example.moneymanagement.exceptions.LongNameException
import com.example.moneymanagement.storage.FileWorker
import com.example.moneymanagement.storage.JSONConvertor

class CreateTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)

        val transactionName = findViewById<EditText>(R.id.inputTransactionName)
        val transactionCategory = findViewById<Spinner>(R.id.category_spinner)
        val transactionSum = findViewById<EditText>(R.id.inputTransactionSum)
        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        val categoriesExpence = CategoryExpence.values().map { it.kategoryName }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoriesExpence)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        transactionCategory.adapter = adapter


        var category: String = ""
        transactionCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category = CategoryExpence.values()[p2].kategoryName
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                category = CategoryExpence.OTHER.kategoryName
            }

        }

        var transactionType = false
        toggleButton.setOnCheckedChangeListener{ _, isChecked ->
            transactionType = isChecked
        }

        val button = findViewById<Button>(R.id.create_transaction_button)
        button.setOnClickListener {

            var olderTransactions = String()

            var args = intent.extras
            val date = args?.get("date").toString().split(" ")
            val aDay = date[0].toInt()
            val aMonth =date[1].toInt()
            val aYear =date[2].toInt()

            val fileName = "${aMonth}_${aYear}"
            val fileWorker = FileWorker()

//            var today = Time(Time.getCurrentTimezone())
//            today.setToNow()
//            val fileName = "${today.month}_${today.year}"
//            val fileWorker = FileWorker()

            try {
                olderTransactions = fileWorker.readFile(applicationContext, fileName)
            } catch (e: Exception) {
            }

            val jsonConvertor = JSONConvertor()
            var month = jsonConvertor.convertJsonToMonth(olderTransactions)

            try {
                if (transactionName.text.toString().length > 25) {
                    throw LongNameException()
                }
                if (month.days.last().day == aDay) {
                    month.days.last().transactions.add(
                        Transaction(
                            transactionType,
                            category,
                            transactionName.text.toString(),
                            transactionSum.text.toString().toDouble()
                        )
                    )
                } else {
                    month.days.add(
                        TransactionDay(
                            aDay, 0.0, 0.0, mutableListOf(
                                Transaction(
                                    transactionType,
                                    category,
                                    transactionName.text.toString(),
                                    transactionSum.text.toString().toDouble()
                                )
                            )
                        )
                    )
                }
                month.days.last().incomeSum =
                    month.days.last().transactions.filter { it.type }.sumOf { it.value }
                month.days.last().expenceSum =
                    month.days.last().transactions.filter { !it.type }.sumOf { it.value }

                fileWorker.writeFile(applicationContext, jsonConvertor.convertMonthToJson(month))
                val intent = Intent(this, TransactionTrackerActivity::class.java)
                startActivity(intent)
            }catch (ex: LongNameException){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Ошибка")
                builder.setMessage("Длина названия должна быть менее 26-ти символов")
                builder.create().show()
            }catch (e: Exception){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Ошибка")
                builder.setMessage("Проверьте правильность заполнения полей")
                builder.create().show()
            }
        }


    }
}