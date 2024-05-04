package com.devmasterstudy.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.devmasterstudy.fitnesstracker.login.view.LoginActivity


class SplashActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 2000
    private lateinit var imageSplash : ImageView
    private lateinit var textSplash: TextView
    private lateinit var textAssign: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageSplash = findViewById(R.id.imageSplash)
        textSplash = findViewById(R.id.textSplash)
        textAssign = findViewById(R.id.textAssign)

        val animCart = AnimationUtils.loadAnimation(this, R.anim.slide_in_cart_left_to_right)
        val animText = AnimationUtils.loadAnimation(this,R.anim.slide_in_text_below_to_top)


        imageSplash.startAnimation(animCart)
        textSplash.startAnimation(animText)
        textAssign.startAnimation(animCart)

        Handler().postDelayed({
            val loginIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
