package com.tc.crm.ui.contents.countryList;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.countryList.CountryListResponse;

public interface CountryListView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

    void onCountryListResponse(CountryListResponse body);
}
