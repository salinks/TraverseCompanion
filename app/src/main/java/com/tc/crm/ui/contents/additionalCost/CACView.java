package com.tc.crm.ui.contents.additionalCost;


import com.tc.crm.data.model.CommonResult;

public interface CACView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

}
