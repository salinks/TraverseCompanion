package com.tc.crm.ui.contents.emailConfig;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.data.model.CommonResult;
import com.tc.crm.data.model.EnableDisableRequest;
import com.tc.crm.model.additionalCost.AdditionalCostResponse;
import com.tc.crm.model.emailConfig.EmailConfigurationRequest;
import com.tc.crm.model.emailConfig.EmailConfigurationResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailConfigPresenter {
    private EmailConfigView mView;
    private TCService tcService;

    public EmailConfigPresenter(EmailConfigView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getEmailConfiguration(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getEmailConfiguration(dataModal)
                .enqueue(new Callback<EmailConfigurationResponse>() {
                    @Override
                    public void onResponse(Call<EmailConfigurationResponse> call, Response<EmailConfigurationResponse> res) {
                        mView.onEmailConfigurationResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<EmailConfigurationResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void updateEmailConfiguration(EmailConfigurationRequest dataModal) {
        tcService
                .getAPI()
                .updateEmailConfiguration(dataModal)
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
