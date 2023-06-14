package com.tc.crm.ui.masters.countries;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.countries.CountriesResponse;

public interface CountriesView {


    void onError(String msg);
    void onCountriesResponse(CountriesResponse body);

    void onCommonResult(CommonResult body);
}
