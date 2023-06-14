package com.tc.crm.ui.masters.countries;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.countries.CountriesResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesPresenter {
    private CountriesView mView;
    private TCService tcService;

    public CountriesPresenter(CountriesView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getCountryMaster(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getCountryMasters(dataModal)
                .enqueue(new Callback<CountriesResponse>() {
                    @Override
                    public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> res) {
                        mView.onCountriesResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<CountriesResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void EnableDisableCountry(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableCountryMaster(dataModal)
                .enqueue(new Callback<CommonResult>() {
                    @Override
                    public void onResponse(Call<CommonResult> call, Response<CommonResult> res) {
                        mView.onCommonResult(res.body());
                    }

                    @Override
                    public void onFailure(Call<CommonResult> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }






}
