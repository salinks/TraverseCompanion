package com.tc.crm.ui.contents.userGroups;





import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.userGroups.UserGroupsRequest;
import com.tc.crm.remote.TCService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CUGPresenter {
    private CUGView mView;
    private TCService tcService;

    public CUGPresenter(CUGView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void createUserGroup(UserGroupsRequest dataModal) {
        tcService
                .getAPI()
                .createUserGroup(dataModal)
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


    public void updateUserGroup(UserGroupsRequest dataModal) {
        tcService
                .getAPI()
                .updateUserGroup(dataModal)
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
