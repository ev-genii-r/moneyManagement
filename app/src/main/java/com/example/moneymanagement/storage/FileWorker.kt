package com.example.moneymanagement.storage
import android.content.Context
import android.os.Environment
import android.text.format.Time
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class FileWorker : AppCompatActivity() {

    fun writeFile(context: Context, data: String): String {
        val today = Time(Time.getCurrentTimezone())
        today.setToNow()
        val fileName = "${today.month}_${today.year}.txt"
        val file = File(context.filesDir, fileName)

        try {
            if (!file.exists()) {
                file.createNewFile()
            }

            val outputStream = FileOutputStream(file)
            outputStream.write(data.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return fileName
    }

    fun writeFileUser(context: Context, data: String): String {
        val today = Time(Time.getCurrentTimezone())
        today.setToNow()
        val fileName = "users.txt"
        val file = File(context.filesDir, fileName)

        try {
            if (!file.exists()) {
                file.createNewFile()
            }

            val outputStream = FileOutputStream(file)
            outputStream.write(data.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return fileName
    }

    fun readFile(context: Context, fileName: String): String {
        val file = File(context.filesDir, "$fileName.txt")
        val stringBuilder = StringBuilder()

        try {
            val inputStream = FileInputStream(file)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var line: String? = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

}