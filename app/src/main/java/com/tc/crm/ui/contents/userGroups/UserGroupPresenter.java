package com.tc.crm.ui.contents.userGroups;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.userGroups.UserGroupsResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGroupPresenter {
    private UserGroupsView mView;
    private TCService tcService;

    public UserGroupPresenter(UserGroupsView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getUserGroups(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getUserGroups(dataModal)
                .enqueue(new Callback<UserGroupsResponse>() {
                    @Override
                    public void onResponse(Call<UserGroupsResponse> call, Response<UserGroupsResponse> res) {
                        mView.onUserGroupsResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<UserGroupsResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void enableDisableUserGroup(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .enableDisableUserGroup(dataModal)
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


    public void deleteUserGroup(EnableDisableRequest dataModal) {
        tcService
                .getAPI()
                .deleteUserGroup(dataModal)
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
