package com.example.moneymanagement.beans

class Transaction (var type: Boolean,
                   /*
                           true -- incomes
                           false -- expenses
                    */
                   var category: String,
                   var name: String,
                   var value: Double)
