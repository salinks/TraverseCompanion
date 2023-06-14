package com.tc.crm.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.tc.crm.R
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.TCApp.Companion.showAlertDialog
import com.tc.crm.base.BaseActivity
import com.tc.crm.databinding.ActivityLoginBinding
import com.tc.crm.model.fcm.FCMResponse
import com.tc.crm.model.fcm.RegisterFCMRequest
import com.tc.crm.model.login.LoginRequest
import com.tc.crm.model.login.LoginResponse
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager


class LoginActivity : BaseActivity(),LoginView {

    var binder: ActivityLoginBinding? = null
     var loginPresenter: LoginPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val windowInsetsController = WindowCompat.getInsetsController(
            window, window.decorView
        )

        windowInsetsController.isAppearanceLightStatusBars = true
         loginPresenter = LoginPresenter(this)
        binder?.btnLogin?.setOnClickListener {
           doLogin()
        }
        Log.e("FCM-TOKEN",PreferenceManager.getInstance().fcmToken)

    }

    private fun doLogin() {

        if (TextUtils.isEmpty(binder!!.etUsername.text.toString())) {
            AnimationUtil.TranslateView(this, binder!!.etUsername)
            binder!!.etUsername.error = "Enter your username"
            binder!!.etUsername.requestFocus()
            return
        }
        if (TextUtils.isEmpty(binder!!.etPassword.text.toString())) {
            AnimationUtil.TranslateView(this, binder!!.etPassword)
            binder!!.etPassword.error = "Enter your password"
            binder!!.etPassword.requestFocus()
            return
        }

        showAlertDialog(0, this, "Please Wait", "Validating your credentials")
        val request = LoginRequest()
        request.userName = binder!!.etUsername.text.toString().trim()
        request.password = binder!!.etPassword.text.toString().trim()
        loginPresenter?.userLogin(request);
    }

    override fun onLoginResponse(loginResponse: LoginResponse?) {

        if(loginResponse?.results?.status == true){
            PreferenceManager.getInstance().userId = loginResponse.userData?.memId?.toString()
            PreferenceManager.getInstance().userName = loginResponse.userData?.name?.toString()
            PreferenceManager.getInstance().desName = loginResponse.userData?.desName?.toString()
            val request = RegisterFCMRequest()
            request.userId = loginResponse.userData?.memId?.toString()
            request.fcmToken = PreferenceManager.getInstance().fcmToken
            request.deviceType = "Android"
            loginPresenter?.registerFCM(request);
        }
        else{
            showMessage(1,loginResponse?.results?.message)
        }


    }

    override fun onFCMResults(fcmResults:  FCMResponse?) {
        hideAlertDialog()
        finish()
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }

    override fun onError(text: String?) {
        hideAlertDialog()
        showMessage(1,text)

    }



}