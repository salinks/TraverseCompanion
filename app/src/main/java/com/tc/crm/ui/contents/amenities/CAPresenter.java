package com.tc.crm.ui.contents.amenities;





import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.amenities.AmenitiesRequest;
import com.tc.crm.model.countries.CountriesResponse;
import com.tc.crm.model.intakeSections.IntakeSectionRequest;
import com.tc.crm.remote.TCService;

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





    public void createAmenity(AmenitiesRequest dataModal) {
        tcService
                .getAPI()
                .createAmenity(dataModal)
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


    public void updateAmenity(AmenitiesRequest dataModal) {
        tcService
                .getAPI()
                .updateAmenity(dataModal)
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
