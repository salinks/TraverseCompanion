package com.tc.crm.ui.contents.amenities;


import com.tc.crm.data.model.CommonResult;

public interface CAView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

}
