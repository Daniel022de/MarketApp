package com.devmasterstudy.fitnesstracker.login.presenter

interface LoginContract {

    interface View {
        fun onLoginSuccess()
        fun onLoginError()
    }

    interface Presenter {

        fun loginUser(username: String, password: String)

    }

}