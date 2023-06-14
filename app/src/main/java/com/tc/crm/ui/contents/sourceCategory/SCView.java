package com.tc.crm.ui.contents.sourceCategory;


import com.tc.crm.data.model.CommonResult;

public interface SCView {


    void onError(String msg);

    void onCommonResult(CommonResult body);
}
