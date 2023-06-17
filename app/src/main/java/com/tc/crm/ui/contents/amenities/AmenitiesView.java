package com.tc.crm.ui.contents.amenities;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.amenities.AmenitiesResponse;

public interface AmenitiesView {


    void onError(String msg);

    void onCommonResult(CommonResult body);


    void onAmenitiesResponse(AmenitiesResponse body);
}
