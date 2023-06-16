package com.tc.crm.ui.contents.intakeSections;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.intakeSections.IntakeSectionResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntakeSectionPresenter {
    private IntakeSectionsView mView;
    private TCService tcService;

    public IntakeSectionPresenter(IntakeSectionsView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getUserGroups(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getIntakeSections(dataModal)
                .enqueue(new Callback<IntakeSectionResponse>() {
                    @Override
                    public void onResponse(Call<IntakeSectionResponse> call, Response<IntakeSectionResponse> res) {
                        mView.onIntakeSectionResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<IntakeSectionResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void enableDisableIntakeSection(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableIntakeSection(dataModal)
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
