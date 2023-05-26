package com.tc.crm.base


import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.tc.crm.R
import com.tc.crm.ui.customViews.cookiebar.CookieBar
import com.tc.crm.utils.PreferenceManager
import java.lang.reflect.Method


abstract class BaseActivity : AppCompatActivity(), MvpView {

    lateinit var primaryBaseActivity: Context

    fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0f
        var metrics = resources.displayMetrics
        var wm = (getSystemService(WINDOW_SERVICE) as WindowManager)
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)




    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(resources.configuration)



        if (Build.VERSION.SDK_INT >= 21) {
          //  window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
           /* window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
        }
         changeStatusBarColor()

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                val m: Method = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (TextUtils.isEmpty(PreferenceManager.getInstance().fcmToken)) {
            saveFCMToken()
        }

    }





    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }




    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }






  fun showMessage(type: Int?, Message: String?) {

            var color = R.color.green

                when (type) {
                    1 -> color = R.color.red
                    2 -> color = R.color.yellow_warning
                    3 -> color = R.color.green
                }



            CookieBar.build(this)
                    .setMessage(Message)
                    .setDuration(4000)
                    .setLayoutGravity(Gravity.TOP)
                    .setBackgroundColor(color)

                    .setAction("", {})
                    .show()

    }

    override fun onResume() {
        super.onResume()


    }


    override fun onResponseError(error: String?) {}

    override fun showProgressBarLoading(isCancelable: Boolean) {}

    override fun hideProgressLoading() {}




    override fun showLoading(isCancelable: Boolean) {
        /* hideLoading()
         progressDialog = DialogUtils.ShowProgressImage(this,isCancelable)*/
    }

    override fun hideLoading() {
        /*if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.cancel()
        }*/
    }


    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }


    fun isStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 10001)
                return false
            }
        } else {
            return true
        }
    }

    private fun saveFCMToken() {

        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if(result != null){
                PreferenceManager.getInstance().fcmToken =  result
            }
        }

    }



}
