package com.tc.crm.ui.splash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;

import com.tc.crm.R;
import com.tc.crm.base.BaseActivity;
import com.tc.crm.databinding.ActivitySplashBinding;
import com.tc.crm.ui.home.HomeActivity;
import com.tc.crm.ui.login.LoginActivity;
import com.tc.crm.ui.login.LoginPresenter;
import com.tc.crm.utils.PreferenceManager;


public class SplashActivity extends BaseActivity {



    ActivitySplashBinding binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_splash);


        new Handler().postDelayed(() -> {
        gotoHome();
        }, 5000);

    }

    private void gotoHome() {

        if(!TextUtils.isEmpty(PreferenceManager.getInstance().getUserId()))
        {
            finish();
         startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }
        else{
            finish();
          startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }


}
