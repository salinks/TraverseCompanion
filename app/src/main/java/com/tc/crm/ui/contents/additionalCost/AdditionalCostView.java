package com.tc.crm.ui.contents.additionalCost;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.additionalCost.AdditionalCostResponse;

public interface AdditionalCostView {


    void onError(String msg);

    void onCommonResult(CommonResult body);


    void onAdditionalCostResponse(AdditionalCostResponse body);
}
