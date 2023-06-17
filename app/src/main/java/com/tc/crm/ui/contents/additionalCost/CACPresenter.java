package com.tc.crm.ui.contents.additionalCost;





import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.additionalCost.AdditionalCostRequest;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.contents.amenities.CAView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CACPresenter {
    private CACView mView;
    private TCService tcService;

    public CACPresenter(CACView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }





    public void createAdditionalCost(AdditionalCostRequest dataModal) {
        tcService
                .getAPI()
                .createAdditionalCost(dataModal)
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


    public void updateAdditionalCost(AdditionalCostRequest dataModal) {
        tcService
                .getAPI()
                .updateAdditionalCost(dataModal)
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
