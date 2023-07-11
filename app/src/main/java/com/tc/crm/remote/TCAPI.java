package com.tc.crm.remote;

import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.activityLogs.ActivityLogsResponse;
import com.tc.crm.model.additionalCost.AdditionalCostRequest;
import com.tc.crm.model.additionalCost.AdditionalCostResponse;
import com.tc.crm.model.airports.AirportListResponse;
import com.tc.crm.model.airports.AirportRequest;
import com.tc.crm.model.amenities.AmenitiesRequest;
import com.tc.crm.model.amenities.AmenitiesResponse;
import com.tc.crm.model.clientDetails.ClientDetailsRequest;
import com.tc.crm.model.clientDetails.ClientDetailsResponse;
import com.tc.crm.model.clientList.ClientListResponse;
import com.tc.crm.model.countries.CountriesResponse;
import com.tc.crm.model.countries.CountryRequest;
import com.tc.crm.model.countryList.CountryListResponse;
import com.tc.crm.model.emailConfig.EmailConfigurationRequest;
import com.tc.crm.model.emailConfig.EmailConfigurationResponse;
import com.tc.crm.model.fcm.FCMResponse;
import com.tc.crm.model.fcm.RegisterFCMRequest;
import com.tc.crm.model.home.dashboard.DashboardRequest;
import com.tc.crm.model.home.dashboard.DashboardResponse;
import com.tc.crm.model.intakeSections.IntakeSectionRequest;
import com.tc.crm.model.intakeSections.IntakeSectionResponse;
import com.tc.crm.model.login.LoginRequest;
import com.tc.crm.model.login.LoginResponse;
import com.tc.crm.model.sourceCategory.SourceCategoryRequest;
import com.tc.crm.model.sourceCategory.SourceCategoryResponse;
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

    @POST("activeCountryMaster")
    Call<CountriesResponse> activeCountryMaster(@Body CommonRequest dataModal);


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

    @POST("sourceCategory")
    Call<SourceCategoryResponse> getSourceCategory(@Body CommonRequest dataModal);

    @POST("enableDisableSourceCategory")
    Call<CommonResult> enableDisableSourceCategory(@Body EnableDisableRequest dataModal);

    @POST("deleteSourceCategory")
    Call<CommonResult> deleteSourceCategory(@Body EnableDisableRequest dataModal);

    @POST("createSourceCategory")
    Call<CommonResult> createSourceCategory(@Body SourceCategoryRequest dataModal);

    @POST("updateSourceCategory")
    Call<CommonResult> updateSourceCategory(@Body SourceCategoryRequest dataModal);

    @POST("countryList")
    Call<CountryListResponse> getCountryList(@Body CommonRequest dataModal);

    @POST("enableDisableCountryList")
    Call<CommonResult> enableDisableCountryList(@Body EnableDisableRequest dataModal);

    @POST("airportList")
    Call<AirportListResponse> getAirportList(@Body CommonRequest dataModal);
    @POST("enableDisableAirportList")
    Call<CommonResult> enableDisableAirport(@Body EnableDisableRequest dataModal);

    @POST("deleteAirport")
    Call<CommonResult> deleteAirport(@Body EnableDisableRequest dataModal);

    @POST("createAirportList")
    Call<CommonResult> createAirport(@Body AirportRequest dataModal);

    @POST("updateAirportList")
    Call<CommonResult> updateAirport(@Body AirportRequest dataModal);

    @POST("IntakeSections")
    Call<IntakeSectionResponse> getIntakeSections(@Body CommonRequest dataModal);

    @POST("enableDisableIntakeSections")
    Call<CommonResult> enableDisableIntakeSection(@Body EnableDisableRequest dataModal);


    @POST("createIntakeSection")
    Call<CommonResult> createIntakeSection(@Body IntakeSectionRequest dataModal);

    @POST("updateIntakeSection")
    Call<CommonResult> updateIntakeSection(@Body IntakeSectionRequest dataModal);

    @POST("Amenities")
    Call<AmenitiesResponse> getAmenities(@Body CommonRequest dataModal);

    @POST("enableDisableAmenity")
    Call<CommonResult> enableDisableAmenity(@Body EnableDisableRequest dataModal);
    @POST("deleteAmenity")
    Call<CommonResult> deleteAmenity(@Body EnableDisableRequest dataModal);

    @POST("createAmenity")
    Call<CommonResult> createAmenity(@Body AmenitiesRequest dataModal);

    @POST("updateAmenity")
    Call<CommonResult> updateAmenity(@Body AmenitiesRequest dataModal);

    @POST("AdditionalCost")
    Call<AdditionalCostResponse> getAdditionalCosts(@Body CommonRequest dataModal);

    @POST("enableDisableAdditionalCost")
    Call<CommonResult> enableDisableAdditionalCost(@Body EnableDisableRequest dataModal);
    @POST("deleteAdditionalCost")
    Call<CommonResult> deleteAdditionalCost(@Body EnableDisableRequest dataModal);

    @POST("createAdditionalCost")
    Call<CommonResult> createAdditionalCost(@Body AdditionalCostRequest dataModal);

    @POST("updateAdditionalCost")
    Call<CommonResult> updateAdditionalCost(@Body AdditionalCostRequest dataModal);

    @POST("EmailConfiguration")
    Call<EmailConfigurationResponse> getEmailConfiguration(@Body CommonRequest dataModal);

    @POST("updateEmailConfiguration")
    Call<CommonResult> updateEmailConfiguration(@Body EmailConfigurationRequest dataModal);

    @POST("activityLogs")
    Call<ActivityLogsResponse> getActivityLogs(@Body CommonRequest dataModal);

    @POST("clearActivityLogs")
    Call<CommonResult> clearActivityLogs(@Body CommonRequest dataModal);

    @POST("clientsList")
    Call<ClientListResponse> getClientLists(@Body CommonRequest dataModal);

    @POST("clientDetails")
    Call<ClientDetailsResponse> getClientDetails(@Body ClientDetailsRequest dataModal);


}
