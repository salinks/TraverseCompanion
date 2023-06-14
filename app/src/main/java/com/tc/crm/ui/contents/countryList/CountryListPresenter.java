package com.tc.crm.ui.contents.countryList;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.countryList.CountryListResponse;
import com.tc.crm.model.sourceCategory.SourceCategoryResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryListPresenter {
    private CountryListView mView;
    private TCService tcService;

    public CountryListPresenter(CountryListView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getCountryList(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getCountryList(dataModal)
                .enqueue(new Callback<CountryListResponse>() {
                    @Override
                    public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> res) {
                        mView.onCountryListResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<CountryListResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void enableDisableCountryList(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableCountryList(dataModal)
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
