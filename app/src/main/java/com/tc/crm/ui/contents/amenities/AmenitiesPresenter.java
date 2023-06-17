package com.tc.crm.ui.contents.amenities;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.amenities.AmenitiesResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmenitiesPresenter {
    private AmenitiesView mView;
    private TCService tcService;

    public AmenitiesPresenter(AmenitiesView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getAmenities(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getAmenities(dataModal)
                .enqueue(new Callback<AmenitiesResponse>() {
                    @Override
                    public void onResponse(Call<AmenitiesResponse> call, Response<AmenitiesResponse> res) {
                        mView.onAmenitiesResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<AmenitiesResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void enableDisableAmenity(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableAmenity(dataModal)
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




    public void deleteAmenity(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .deleteAmenity(dataModal)
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
