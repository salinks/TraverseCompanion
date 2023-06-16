package com.tc.crm.ui.contents.airports;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.airports.AirportListResponse;
import com.tc.crm.model.countries.CountriesResponse;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.masters.countries.CountriesView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirportPresenter {
    private AirportView mView;
    private TCService tcService;

    public AirportPresenter(AirportView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getCountryMaster(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getAirportList(dataModal)
                .enqueue(new Callback<AirportListResponse>() {
                    @Override
                    public void onResponse(Call<AirportListResponse> call, Response<AirportListResponse> res) {
                        mView.onAirportListResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<AirportListResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void enableDisableAirport(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableAirport(dataModal)
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



    public void deleteAirport(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .deleteAirport(dataModal)
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
