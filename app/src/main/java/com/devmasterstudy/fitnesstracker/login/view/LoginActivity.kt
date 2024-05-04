package com.devmasterstudy.fitnesstracker.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devmasterstudy.fitnesstracker.MainActivity
import com.devmasterstudy.fitnesstracker.R
import com.devmasterstudy.fitnesstracker.login.presenter.LoginContract
import com.devmasterstudy.fitnesstracker.login.presenter.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.editUsername).text.toString()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString()

            presenter.loginUser(username,password)

        }

    }

    override fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginError() {
       Toast.makeText(this,"Usuário ou Senha incorretos!",Toast.LENGTH_SHORT).show()
        val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        service.hideSoftInputFromWindow(currentFocus?.windowToken,0)
    }


}