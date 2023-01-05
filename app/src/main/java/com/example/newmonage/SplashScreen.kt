package com.example.newmonage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    private  val SPLASH_TIME_OUT:Long = 4000 // delay 4 detik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}