package com.example.moneymanagement

import com.example.moneymanagement.beans.Transaction
import com.example.moneymanagement.beans.TransactionDay
import com.example.moneymanagement.beans.TransactionMonth
import com.example.moneymanagement.beans.User
import com.example.moneymanagement.enums.Country
import com.example.moneymanagement.storage.FileWorker
import com.example.moneymanagement.storage.JSONConvertor
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun appTest(){

        var transactions1: MutableList<Transaction> = ArrayList()

        transactions1.add(Transaction(true, "Zarplata", "Zarplata za oktober", 100.1))
        transactions1.add(Transaction(false, "Eda", "cheburek", 10.1))
        transactions1.add(Transaction(false, "Eda", "shavuha", 110.1))
        transactions1.add(Transaction(true, "Zarplata", "Zarplata za december", 12.1))
        transactions1.add(Transaction(true, "Zarplata", "Zarplata za march", 13.1))
        transactions1.add(Transaction(false, "Eda", "chai", 140.1))
        transactions1.add(Transaction(true, "Zarplata", "Zarplata za may", 180.1))
        transactions1.add(Transaction(false, "Eda", "konfeta", 10000.1))
        transactions1.add(Transaction(true, "Zarplata", "Zarplata za november", 10900.1))
        transactions1.add(Transaction(false, "Eda", "baton", 0.1))
        transactions1.add(Transaction( true, "Zarplata", "Zarplata za V", 0.11111))

        var transactions2: MutableList<Transaction> = ArrayList()

        transactions2.add(Transaction( true, "Zarplata", "Zarplata za oktober1", 100.1))
        transactions2.add(Transaction( false, "Eda", "cheburek1", 10.1))
        transactions2.add(Transaction( false, "Eda", "shavuha1", 110.1))
        transactions2.add(Transaction( true, "Zarplata", "Zarplata za december1", 12.1))
        transactions2.add(Transaction( true, "Zarplata", "Zarplata za march1", 13.1))
        transactions2.add(Transaction( false, "Eda", "chai1", 140.1))
        transactions2.add(Transaction( true, "Zarplata", "Zarplata za may1", 180.1))
        transactions2.add(Transaction( false, "Eda", "konfeta1", 10000.1))
        transactions2.add(Transaction( true, "Zarplata", "Zarplata za november1", 10900.1))
        transactions2.add(Transaction( false, "Eda", "baton1", 0.1))
        transactions2.add(Transaction( true, "Zarplata", "Zarplata za V1", 0.11111))

        var day1 = TransactionDay(23,
            transactions1.filter { it.type }.sumOf { it.value },
            transactions1.filter { !it.type }.sumOf { it.value },
            transactions1
        )

        var day2 = TransactionDay(24,
            transactions2.filter { it.type }.sumOf { it.value },
            transactions2.filter { !it.type }.sumOf { it.value },
            transactions2
        )

//        var month = TransactionMonth(11, 2023, listOf(day1, day2))
//
//        val jsonConvertor = JSONConvertor()
//        val monthString = jsonConvertor.convertMonthToJson(month)
//        println(monthString)
//        val fileWorker = FileWorker()
//        fileWorker.writeFile(monthString)
    }
}