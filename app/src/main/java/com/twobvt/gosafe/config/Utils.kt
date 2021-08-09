package com.twobvt.gosafe.config

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.twobvt.gosafe.login.ui.LoginScreen
import com.twobvt.gosafe.network.Resource




    fun View.visible(isVisible: Boolean){

        visibility =if(isVisible)  View.VISIBLE else View.GONE
    }


    fun  View.enable(enabled: Boolean){

        isEnabled = enabled
        alpha = if (enabled) 1f else 0.5f
    }


  fun View.snackbar(
      message: String, action: (() -> Unit)? = null,

      ){


      val snackbar = Snackbar.make(this,message,Snackbar.LENGTH_INDEFINITE)


      action?.let {
          snackbar.setAction("Retry"){

              it()
          }
      }

      snackbar.show()

      val handler = Handler()
      handler.postDelayed(Runnable { snackbar.dismiss() }, 4000)
  }


    fun Activity.handleApiErrors (
        failure: Resource.Failure,
        retry :(()->Unit )? = null,

    ){
        when {
            failure.isNetworkError-> getWindow().getDecorView().getRootView().snackbar("Please check your internet connection",retry)
            failure.errorCode ==401 || failure.errorCode == 400  ->{
                if (this is LoginScreen)
                {
                    getWindow().getDecorView().getRootView().snackbar("You've entered incorrect email or password")
                }else{

                    //TODO : logout the user and get back to login Screen
                    println("Log out the user because the session is expired")

                   var  tinyDB = TinyDB(applicationContext)
                    tinyDB.putString("accessToken","")
                    finish()
                    startActivity(Intent(this, LoginScreen::class.java))
                }
            }
            else ->{
                val error = failure.errorBody?.string().toString()
                getWindow().getDecorView().getRootView().snackbar(error)

            }


        }


    }


    fun saveAccessToken(context: Context,accessToken :String){

        var  tinyDB = TinyDB(context)
        tinyDB.putString("accessToken",accessToken)

    }

  fun deleteAccessToken(context: Context,accessToken :String){

    var  tinyDB = TinyDB(context)
    tinyDB.putString("accessToken",accessToken)

}

fun getAccessToken(context: Context, ): String {

    var tinyDB = TinyDB(context)
    return tinyDB.getString("accessToken")
}




