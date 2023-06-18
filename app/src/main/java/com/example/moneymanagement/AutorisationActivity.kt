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

class AutorisationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autorisation)

        val password = findViewById<EditText>(R.id.input_autorisation_password)
        val login = findViewById<EditText>(R.id.input_autorisation_login)
        val button = findViewById<Button>(R.id.autorisation_button)
        val createButton = findViewById<Button>(R.id.create_user_autorisation)



        val fileWorker = FileWorker()
        val jsonConvertor = JSONConvertor()

        createButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener{
            val inputUser = User("", login.text.toString(), password.text.toString())
            val users = jsonConvertor.convertJsonToUserData(fileWorker.readFile(applicationContext, "users"))
            println("hello")
            println(fileWorker.readFile(applicationContext, "users"))
            println("hello")
            println("${inputUser.login} ${inputUser.password}")
            println("${users.last().password}  ${users.last().password == inputUser.password}")
            if(!users.none { it.login == inputUser.login && it.password == inputUser.password }){
                val intent = Intent(this, TransactionTrackerActivity::class.java)
                startActivity(intent)
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Ошибка")
                builder.setMessage("Пользователь с введенными данными не существует, попробуйте снова")
                builder.create().show()
            }

        }

    }
}