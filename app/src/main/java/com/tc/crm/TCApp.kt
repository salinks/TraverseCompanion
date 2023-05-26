package com.tc.crm

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.tc.crm.base.BaseActivity

import com.tc.crm.ui.customViews.alert.AlertFragment
import com.tc.crm.ui.customViews.alert.AlertFragment.Companion.getInstance

class TCApp : Application() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        FirebaseApp.initializeApp(this);



     //   NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted("http://192.168.3.135")




//        if (BuildConfig.DEBUG) {
//            TLog.enableLogging()
//        } else {
//            TLog.disableLogging()
//        }
    }


    companion object {
        lateinit var context: Context
        private var fragment : AlertFragment?=null
            set
        operator fun get(context: Context): TCApp {
            return context.applicationContext as TCApp
        }

        fun showAlertDialog(resource: Int, ctx: Context, title: String, contentDesc: String) {
            try {
                fragment = getInstance()
                fragment?.setMessageText(contentDesc)
                fragment?.setAlertTitle(title)
                fragment?.showImageView(true, resource)
                (ctx as? BaseActivity)?.supportFragmentManager?.let { fragment?.show(it, "Alert") }
            } catch (e: Exception) {
            }
        }


        fun hideAlertDialog() {
            if (fragment != null) {
                try {
                    fragment?.dismissAllowingStateLoss()

                } catch (e: Exception) {

                }
            }
        }

    }
}