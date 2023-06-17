package com.tc.crm.ui.contents.activityLogs;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.activityLogs.ActivityLogsResponse;
import com.tc.crm.model.amenities.AmenitiesResponse;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.contents.amenities.AmenitiesView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogsPresenter {
    private ActivityLogsView mView;
    private TCService tcService;

    public ActivityLogsPresenter(ActivityLogsView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getActivityLogs(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getActivityLogs(dataModal)
                .enqueue(new Callback<ActivityLogsResponse>() {
                    @Override
                    public void onResponse(Call<ActivityLogsResponse> call, Response<ActivityLogsResponse> res) {
                        mView.onActivityLogsResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<ActivityLogsResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void clearActivityLogs(CommonRequest dataModal) {
        tcService
                .getAPI()
                .clearActivityLogs(dataModal)
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
