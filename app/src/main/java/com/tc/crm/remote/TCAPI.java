package com.tc.crm.remote;

import com.tc.crm.model.fcm.FCMResponse;
import com.tc.crm.model.fcm.RegisterFCMRequest;
import com.tc.crm.model.login.LoginRequest;
import com.tc.crm.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TCAPI {
    @POST("login")
    Call<LoginResponse> doLogin(@Body LoginRequest dataModal);

    @POST("registerFcm")
    Call<FCMResponse> doLogin(@Body RegisterFCMRequest dataModal);


   /* @GET("country/get/all")
    Call<Data> getResults();

    @GET("country/get/iso2code/{alpha2_code}")
    Call<Data> getByAlpha2Code(@Path("alpha2_code") String alpha2Code);*/
}
