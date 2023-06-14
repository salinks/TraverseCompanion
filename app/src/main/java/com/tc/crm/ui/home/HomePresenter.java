package com.tc.crm.ui.home;

import com.tc.crm.model.fcm.FCMResponse;
import com.tc.crm.model.fcm.RegisterFCMRequest;
import com.tc.crm.model.home.dashboard.DashboardRequest;
import com.tc.crm.model.home.dashboard.DashboardResponse;
import com.tc.crm.model.login.LoginRequest;
import com.tc.crm.model.login.LoginResponse;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.login.LoginView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePresenter {
    private HomeView homeView;
    private TCService  tcService;

    public HomePresenter(HomeView view) {
        this.homeView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getDashboard(DashboardRequest dataModal) {
        tcService
                .getAPI()
                .getDashboard(dataModal)
                .enqueue(new Callback<DashboardResponse>() {
                    @Override
                    public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> res) {
                        homeView.onDashboardResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<DashboardResponse> call, Throwable t) {
                        try {
                            homeView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
