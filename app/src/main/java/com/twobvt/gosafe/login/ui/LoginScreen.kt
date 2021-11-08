package com.twobvt.gosafe.login.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.twobvt.gosafe.R
import com.twobvt.gosafe.base.BaseActivity
import com.twobvt.gosafe.config.*
import com.twobvt.gosafe.dashboardScreen.DashboardScreen
import com.twobvt.gosafe.databinding.ActivityLoginScreenBinding
import com.twobvt.gosafe.login.authApi.AuthApi
import com.twobvt.gosafe.login.authRepository.AuthRepository
import com.twobvt.gosafe.login.authViewModel.AuthViewModel
import com.twobvt.gosafe.network.Resource


class LoginScreen : BaseActivity<AuthViewModel,ActivityLoginScreenBinding,AuthRepository>()  {

    private lateinit var userName : String
    private lateinit var password : String
    private lateinit var grantType : String
    private  var rememberMeCheckBox :Boolean = false

    //Shared preferences object
    private lateinit var tinyDB : TinyDB
    private lateinit var methods: Methods



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

// disable the night mode for the whole application
 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        // instantiate


        userName = binding.userName.text.toString()
        password = binding.password.text.toString()
        grantType = "password"
        tinyDB = TinyDB(applicationContext)
        methods = Methods()


        //checking if user saved his data with Remember me option
        getUserValuesFromSharedPrefAsIfRememberIsTrue()

    }
    
    // Storing user credentials in Shared Preferences
   private fun rememberMeCheckBox(){

        if(binding.rememberMeCheckBox.isChecked)
        {


            println("Value stored in Shared Storage ${tinyDB.getString("accessToken")}")

            tinyDB.putBoolean("remeberMe",true)
            tinyDB.putString("userName",userName)
            tinyDB.putString("password",password)
            tinyDB.putString("grantType",grantType)

        } else if(!binding.rememberMeCheckBox.isChecked){

            tinyDB.putBoolean("remeberMe",false)
            tinyDB.putString("userName","")
            tinyDB.putString("password","")
            tinyDB.putString("grantType","")

        }
    }


   private fun getUserValuesFromSharedPrefAsIfRememberIsTrue(){


       if (tinyDB.getBoolean("remeberMe"))
       {
           binding.userName.setText(tinyDB.getString("userName"))

           binding.password.setText( tinyDB.getString("password"))
           binding.rememberMeCheckBox.isChecked =true
       }
   }
    // passing viewModel to Base class
     override fun getViewModel() = AuthViewModel::class.java

    //Passing View binding to Base class
     override fun getViewBinding() = ActivityLoginScreenBinding.inflate(layoutInflater)

    //passing repository to Base class
     override fun getRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))

    //Screen clicks setup
     override fun onClick(v: View?) {

         when (v!!.id) {
             R.id.login_button -> {
                 callLoginApiFromViewModel()}
             }
     }

    private fun navigateToDashboard(){
        startActivity(Intent(this, DashboardScreen::class.java))
        finish()
    }

    private fun callLoginApiFromViewModel() {
         var loginButton = binding.loginButton
         var progressBarCircle = binding.progressBarCircle

         //TODO: ADD network check
          userName = binding.userName.text.toString()
          password = binding.password.text.toString()

         when {
             userName.isEmpty() -> {
                 Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show()
             }
             password.isEmpty() -> {
                 Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
             }
             password.length < 4 -> {
                 Toast.makeText(this, "Please enter Valid Password", Toast.LENGTH_SHORT)
                     .show()
             }
             else -> {

                 //Button Visibility and click event working
                 loginButton.isClickable = false
                 loginButton.setEnabled(false);
                 //loginButton.visibility = View.INVISIBLE
                 //progress bar Visibility

                 println("Data is here $userName , $password and grant Type '$grantType' ::: @@@@@@@@@@@@@@@@@")

                 //Custom waiting
                  val handler = Handler()
                          handler.postDelayed(Runnable {

                 //Calling login function from view model
                 viewModel.login(userName, password, grantType)
                 viewModel.loginResponse.observe(this, Observer {

                     binding.progressBarCircle.visible(it is Resource.Loading)
                     when (it) {

                         //if response is success then we will go to dash board
                         is Resource.Success -> {


                             //Button Visibility and click event working
                             loginButton.isClickable = true
                             loginButton.setEnabled(true);
                             //loginButton.visibility = View.VISIBLE
                             //progress bar Visibility
                             progressBarCircle.visibility = View.INVISIBLE
                             println("Login Success")

                             //Storing value to local storage
                             saveAccessToken(applicationContext,it.value.access_token)

                             rememberMeCheckBox()
                             //navigate to Dashboard
                             navigateToDashboard()

                         }

                         is Resource.Failure -> {

                             //Button Visibility and click event working
                             loginButton.isClickable = true
                             loginButton.setEnabled(true);
                             handleApiErrors(it)
                             println("Login Failed")
                         }
                     }

                 })
                          }, 500) //1 seconds

             }

         }

     }

 }

//     override fun onCreate(savedInstanceState: Bundle?) {
//
//         super.onCreate(savedInstanceState)
//
//         // hiding status bar
//         supportActionBar?.hide()
//         //making full screen
//         window.setFlags(
//             WindowManager.LayoutParams.FLAG_FULLSCREEN,
//             WindowManager.LayoutParams.FLAG_FULLSCREEN
//         );
//
//         //attach binding here
//         setContentView(R.layout.activity_login_screen)
//         println("Data is here $ , $ and grant Type")
//
//
//
//     }


//
//    fun onClickLoginButton(v: View?) {
////        val myIntent = Intent(this, DashboardScreen()::class.java)
////        startActivity(myIntent)
//    }