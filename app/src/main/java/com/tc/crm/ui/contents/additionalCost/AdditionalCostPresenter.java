package com.tc.crm.ui.contents.additionalCost;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.additionalCost.AdditionalCostResponse;
import com.tc.crm.model.amenities.AmenitiesResponse;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.contents.amenities.AmenitiesView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdditionalCostPresenter {
    private AdditionalCostView mView;
    private TCService tcService;

    public AdditionalCostPresenter(AdditionalCostView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getAdditionalCosts(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getAdditionalCosts(dataModal)
                .enqueue(new Callback<AdditionalCostResponse>() {
                    @Override
                    public void onResponse(Call<AdditionalCostResponse> call, Response<AdditionalCostResponse> res) {
                        mView.onAdditionalCostResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<AdditionalCostResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void enableDisableAdditionalCost(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableAdditionalCost(dataModal)
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




    public void deleteAdditionalCost(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .deleteAdditionalCost(dataModal)
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
