package com.tc.crm.ui.contents.sourceCategory;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.sourceCategory.SourceCategoryResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceCategoryPresenter {
    private SourceCategoryView mView;
    private TCService tcService;

    public SourceCategoryPresenter(SourceCategoryView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getSourceCategory(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getSourceCategory(dataModal)
                .enqueue(new Callback<SourceCategoryResponse>() {
                    @Override
                    public void onResponse(Call<SourceCategoryResponse> call, Response<SourceCategoryResponse> res) {
                        mView.onSourceCategoryResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<SourceCategoryResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void enableDisableSourceCategory(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableSourceCategory(dataModal)
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


    public void deleteSourceCategory(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .deleteSourceCategory(dataModal)
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
