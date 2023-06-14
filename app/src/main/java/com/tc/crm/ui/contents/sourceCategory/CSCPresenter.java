package com.tc.crm.ui.contents.sourceCategory;





import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.sourceCategory.SourceCategoryRequest;
import com.tc.crm.model.userGroups.UserGroupsRequest;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.contents.userGroups.CUGView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CSCPresenter {
    private SCView mView;
    private TCService tcService;

    public CSCPresenter(SCView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void createSourceCategory(SourceCategoryRequest dataModal) {
        tcService
                .getAPI()
                .createSourceCategory(dataModal)
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


    public void updateSourceCategory(SourceCategoryRequest dataModal) {
        tcService
                .getAPI()
                .updateSourceCategory(dataModal)
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
