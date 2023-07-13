package com.tc.crm.ui.applicationMenu.clientDetails;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.clientDetails.ClientDetailsResponse;

public interface ClientDetailsView {


    void onError(String msg);


    void onClientDetailsResponse(ClientDetailsResponse body);

    void onCommonResult(CommonResult body);

}
