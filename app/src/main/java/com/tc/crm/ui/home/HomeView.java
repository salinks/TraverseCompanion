package com.tc.crm.ui.home;


import com.tc.crm.model.home.dashboard.DashboardResponse;


public interface HomeView {

    void onDashboardResponse(DashboardResponse dashboardResponse);

    void onError(String text);
}
