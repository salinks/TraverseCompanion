package com.tc.crm.ui.login;

import com.tc.crm.model.fcm.FCMResponse;
import com.tc.crm.model.fcm.RegisterFCMRequest;
import com.tc.crm.model.login.LoginRequest;
import com.tc.crm.model.login.LoginResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter {
    private LoginView loginView;
    private TCService  tcService;

    public LoginPresenter(LoginView view) {
        this.loginView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void userLogin(LoginRequest dataModal) {
        tcService
                .getAPI()
                .doLogin(dataModal)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> res) {
                        loginView.onLoginResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        try {
                            loginView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void registerFCM(RegisterFCMRequest dataModal) {
        tcService
                .getAPI()
                .doLogin(dataModal)
                .enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> res) {
                        loginView.onFCMResults(res.body());
                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {
                        try {
                            loginView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
