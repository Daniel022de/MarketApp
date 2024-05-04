package com.devmasterstudy.fitnesstracker.login.presenter

import com.devmasterstudy.fitnesstracker.login.model.User

class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {

    private val userList = listOf(
        User("user","password"),
    )


    override fun loginUser(username: String, password: String) {

        val user = userList.find { it.username == username && it.password == password }

        if (user != null) {
           view.onLoginSuccess()
        } else {
            view.onLoginError()
        }

    }
}