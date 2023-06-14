package com.tc.crm.remote;

import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.countries.CountriesResponse;
import com.tc.crm.model.countries.CountryRequest;
import com.tc.crm.model.fcm.FCMResponse;
import com.tc.crm.model.fcm.RegisterFCMRequest;
import com.tc.crm.model.home.dashboard.DashboardRequest;
import com.tc.crm.model.home.dashboard.DashboardResponse;
import com.tc.crm.model.login.LoginRequest;
import com.tc.crm.model.login.LoginResponse;
import com.tc.crm.model.userGroups.UserGroupsRequest;
import com.tc.crm.model.userGroups.UserGroupsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TCAPI {
    @POST("login")
    Call<LoginResponse> doLogin(@Body LoginRequest dataModal);

    @POST("registerFcm")
    Call<FCMResponse> doLogin(@Body RegisterFCMRequest dataModal);

    @POST("dashboard")
    Call<DashboardResponse> getDashboard(@Body DashboardRequest dataModal);

    @POST("countryMaster")
    Call<CountriesResponse> getCountryMasters(@Body CommonRequest dataModal);


    @POST("enableDisableCountryMaster")
    Call<CommonResult> enableDisableCountryMaster(@Body EnableDisableRequest dataModal);

    @POST("createCountryMaster")
    Call<CommonResult> createCountryMaster(@Body CountryRequest dataModal);

    @POST("updateCountryMaster")
    Call<CommonResult> updateCountryMaster(@Body CountryRequest dataModal);

    @POST("userGroups")
    Call<UserGroupsResponse> getUserGroups(@Body CommonRequest dataModal);

    @POST("enableDisableUserGroup")
    Call<CommonResult> enableDisableUserGroup(@Body EnableDisableRequest dataModal);

    @POST("createUserGroup")
    Call<CommonResult> createUserGroup(@Body UserGroupsRequest dataModal);

    @POST("updateUserGroup")
    Call<CommonResult> updateUserGroup(@Body UserGroupsRequest dataModal);

    @POST("deleteUserGroup")
    Call<CommonResult> deleteUserGroup(@Body EnableDisableRequest dataModal);


}
