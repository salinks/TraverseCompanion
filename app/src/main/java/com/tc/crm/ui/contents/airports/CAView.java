package com.tc.crm.ui.contents.airports;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.countries.CountriesResponse;

public interface CAView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

    void onCountriesResponse(CountriesResponse body);
}
