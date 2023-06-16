package com.tc.crm.ui.contents.intakeSections;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.countries.CountriesResponse;

public interface CISView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

    void onCountriesResponse(CountriesResponse body);
}
