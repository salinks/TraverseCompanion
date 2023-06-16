package com.tc.crm.ui.contents.airports;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.airports.AirportListResponse;

public interface AirportView {


    void onError(String msg);
    void onCommonResult(CommonResult body);

    void onAirportListResponse(AirportListResponse body);
}
