package com.example.moneymanagement.beans

import java.util.Date

class TransactionDay (var day: Int,
                      var incomeSum: Double,
                      var expenceSum: Double,
                      var transactions: MutableList<Transaction>)