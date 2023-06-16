package com.tc.crm.ui.contents.airports;





import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.airports.AirportRequest;
import com.tc.crm.model.countries.CountriesResponse;
import com.tc.crm.model.userGroups.UserGroupsRequest;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.contents.userGroups.CUGView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CAPresenter {
    private CAView mView;
    private TCService tcService;

    public CAPresenter(CAView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }


    public void getActiveCountries(CommonRequest dataModal) {
        tcService
                .getAPI()
                .activeCountryMaster(dataModal)
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


    public void createAirport(AirportRequest dataModal) {
        tcService
                .getAPI()
                .createAirport(dataModal)
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


    public void updateAirport(AirportRequest dataModal) {
        tcService
                .getAPI()
                .updateAirport(dataModal)
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
