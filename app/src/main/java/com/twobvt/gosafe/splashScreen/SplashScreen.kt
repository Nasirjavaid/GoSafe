package com.twobvt.gosafe.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.twobvt.gosafe.R
import com.twobvt.gosafe.config.TinyDB
import com.twobvt.gosafe.config.getAccessToken
import com.twobvt.gosafe.dashboardScreen.DashboardScreen
import com.twobvt.gosafe.login.ui.LoginScreen


class SplashScreen : AppCompatActivity() {


    private lateinit var tinyDB : TinyDB
    private val secondsDelayed : Long = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

// disable the night mode for the whole application
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tinyDB = TinyDB(applicationContext)


        Handler().postDelayed(Runnable {
            checkIfUserExist()
        }, secondsDelayed * 1000)



    }


    private fun checkIfUserExist()
    {


       var accessToken :String = getAccessToken(applicationContext)

        if(accessToken==null || accessToken.isEmpty() || accessToken=="")
        {

            startActivity(Intent(this, LoginScreen::class.java))
            finish()
        }else{
            startActivity(Intent(this, DashboardScreen::class.java))
            finish()

        }



    }
}