package com.example.moneymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.moneymanagement.beans.User
import com.example.moneymanagement.storage.FileWorker
import com.example.moneymanagement.storage.JSONConvertor

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val login = findViewById<EditText>(R.id.input_registration_login)
        val password = findViewById<EditText>(R.id.input_registration_password)
        val confirmPassword = findViewById<EditText>(R.id.input_registration_password_confirm)
        val button = findViewById<Button>(R.id.registration_button)

        button.setOnClickListener {
            println("user hello")
            if(password.text.toString() == confirmPassword.text.toString()) {
                val user = User("", login.text.toString(), password.text.toString())
                val fileWorker = FileWorker()
                val jsonConvertor = JSONConvertor()
                println("user")

                var users = jsonConvertor.convertJsonToUserData(fileWorker.readFile(applicationContext, "users"))
                if(users.contains(user)){
                    println("hello")
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Ошибка")
                    builder.setMessage("Пользователь с такими данными существует")
                    builder.create().show()
                }else{
                    users.add(user)
                    fileWorker.writeFileUser(applicationContext, jsonConvertor.convertUserDataToJSON(users))
                    val intent = Intent(    this, TransactionTrackerActivity::class.java)
                    startActivity(intent)
                    println("hello hello")
                }

            }else{
                println("user hello wor")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Ошибка")
                builder.setMessage("Пароли не совпадают")
                builder.create().show()
            }
        }


    }
}