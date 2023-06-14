package com.tc.crm.ui.masters.countries;


import com.tc.crm.data.model.CommonResult;

public interface CCMView {


    void onError(String msg);

    void onCommonResult(CommonResult body);
}
