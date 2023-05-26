package com.tc.crm.ui.login;

import com.tc.crm.model.fcm.FCMResponse;
import com.tc.crm.model.login.LoginResponse;

public interface LoginView {
    void onLoginResponse(LoginResponse loginResponse);
    void onFCMResults(FCMResponse loginResponse);

    void onError(String text);
}
