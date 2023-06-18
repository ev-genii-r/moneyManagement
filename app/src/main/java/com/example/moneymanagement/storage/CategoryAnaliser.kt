package com.example.moneymanagement.storage

import com.example.moneymanagement.beans.CategoryValues
import com.example.moneymanagement.beans.TransactionMonth
import com.example.moneymanagement.enums.CategoryExpence

class CategoryAnaliser {

    fun findCategorySum(month: TransactionMonth, type: Boolean, category: CategoryExpence): CategoryValues{
        var sum = 0.0

        for (i in 0 until month.days.size){
            sum += month.days[i].transactions.filter { it.category == category.kategoryName && it.type == type }.sumOf { it.value }
        }
        return CategoryValues(category, sum)
    }

}