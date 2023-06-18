package com.example.moneymanagement.storage
import com.example.moneymanagement.beans.Transaction
import com.example.moneymanagement.beans.TransactionDay
import com.example.moneymanagement.beans.TransactionMonth
import com.example.moneymanagement.beans.User
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class JSONConvertor {
    private val mapper = jacksonObjectMapper()

    fun convertUserDataToJSON(users: MutableList<User>): String {
        return mapper.writeValueAsString(users)
    }

    fun convertJsonToUserData(json: String): MutableList<User>{
        if(json == "" || json == null){
            return mutableListOf(User("", "", ""))
        }
        return mapper.readValue<MutableList<User>>(json)
    }

    fun convertMonthToJson(month: TransactionMonth): String{
        return mapper.writeValueAsString(month)
    }

    fun convertJsonToMonth(json: String): TransactionMonth{
        if(json == "" || json == null){
            return TransactionMonth(0,0,
                mutableListOf( TransactionDay(0,0.0, 0.0,
                    mutableListOf( Transaction(true,"","", 0.0)))))
        }
        return mapper.readValue(json)
    }

}