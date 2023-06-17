package com.tc.crm.ui.contents.emailConfig;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.emailConfig.EmailConfigurationResponse;

public interface EmailConfigView {


    void onError(String msg);

    void onCommonResult(CommonResult body);


    void onEmailConfigurationResponse(EmailConfigurationResponse body);
}
