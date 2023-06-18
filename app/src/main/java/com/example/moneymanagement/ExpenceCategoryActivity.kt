package com.example.moneymanagement

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.Time
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanagement.adapter.CategoryAdapter
import com.example.moneymanagement.beans.CategoryValues
import com.example.moneymanagement.enums.CategoryExpence
import com.example.moneymanagement.storage.CategoryAnaliser
import com.example.moneymanagement.storage.FileWorker
import com.example.moneymanagement.storage.JSONConvertor
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.ValueDependentColor
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint


class ExpenceCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expence_category)


        var today = Time(Time.getCurrentTimezone())
        today.setToNow()
        var fileName = "${today.month}_${today.year}"

        val graphView = findViewById<GraphView>(R.id.graph_expense)
        val gear = findViewById<ImageView>(R.id.gear_expense)
        val diagram = findViewById<ImageView>(R.id.diagram_expense)
        val list = findViewById<ImageView>(R.id.white_list_expense)

        val fileWorker = FileWorker()
        val jsonConvertor = JSONConvertor()
         try {

            val month =
                jsonConvertor.convertJsonToMonth(fileWorker.readFile(applicationContext, fileName))


            val values = mutableListOf<CategoryValues>()
            val categoryAnaliser = CategoryAnaliser()
            println("hello")
            for(i in 0 until CategoryExpence.values().size){
                values.add(categoryAnaliser.findCategorySum(month, false, CategoryExpence.values()[i]))
            }
            val clearValues = values.filter { it.value > 0.001 }.toMutableList()

            val dataPoints = clearValues.mapIndexed { index, categoryValues ->
                DataPoint(index.toDouble(), categoryValues.value)
            }.toTypedArray()

            val series = BarGraphSeries(dataPoints)

            series.valueDependentColor =
                ValueDependentColor { data ->
                    Color.rgb(
                        data.x.toInt() * 255 / 4,
                        Math.abs(data.y * 255 / 6).toInt(),
                        100
                    )
                }

            series.isDrawValuesOnTop = true
            series.valuesOnTopColor = Color.BLACK
            series.spacing = -100

            graphView.addSeries(series)
            graphView.viewport.isXAxisBoundsManual = true
            graphView.viewport.setMinX(0.0)
            graphView.viewport.setMaxX(clearValues.size.toDouble())
            graphView.viewport.isYAxisBoundsManual = true
            graphView.viewport.setMinY(0.0)
            graphView.viewport.setMaxY(clearValues.maxOf { it.value })

            graphView.addSeries(series)

            list.setOnClickListener {
                val intent = Intent(this, TransactionTrackerActivity::class.java)
                startActivity(intent)
            }
            try {
                setCategoryRecycler(clearValues)
            }catch (e:Exception){

            }
        }catch (e: Exception){
             val intent = Intent(this, TransactionTrackerActivity::class.java)
             startActivity(intent)
        }

    }

    private fun setCategoryRecycler(categories: MutableList<CategoryValues>) {
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        var categoryRecycler: RecyclerView = findViewById(R.id.category_recycler)
        categoryRecycler.layoutManager = layoutManager
        var transactionDayAdapter = CategoryAdapter(this, categories)
        categoryRecycler.adapter = transactionDayAdapter
    }
}