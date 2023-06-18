package com.example.moneymanagement.beans

class TransactionMonth (val month: Int,
                        val year: Int,
                        var days: MutableList<TransactionDay>)