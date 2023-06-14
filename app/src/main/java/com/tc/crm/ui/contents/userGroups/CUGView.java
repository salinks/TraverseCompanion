package com.tc.crm.ui.contents.userGroups;


import com.tc.crm.data.model.CommonResult;

public interface CUGView {


    void onError(String msg);

    void onCommonResult(CommonResult body);
}
