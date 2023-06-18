package com.example.moneymanagement.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanagement.R
import com.example.moneymanagement.beans.Transaction

class TransactionDayAdapter(context: Context, transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionDayAdapter.TransactionDayViewHolder>() {

    var context = context
    var transactions = transactions

    class TransactionDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var transactionCategory: TextView = itemView.findViewById(R.id.transaction_category)
        var transactionName: TextView = itemView.findViewById(R.id.transaction_name)
        var transactionValue: TextView = itemView.findViewById(R.id.transaction_value)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionDayViewHolder {
        var transactionItem = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false)
        return TransactionDayViewHolder(transactionItem)
    }



    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: TransactionDayViewHolder, position: Int) {
        holder.transactionCategory.text = transactions[position].category
        holder.transactionName.text = transactions[position].name
        holder.transactionValue.text = transactions[position].value.toString()
        if(transactions[position].type){
            holder.transactionValue.setTextColor(Color.parseColor("#318475"))
        }else{
            holder.transactionValue.setTextColor(Color.parseColor("#FF5757"))
        }
    }
}