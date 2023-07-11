package com.tc.crm.ui.applicationMenu.clientDetails;


import com.tc.crm.model.clientDetails.ClientDetailsResponse;

public interface ClientDetailsView {


    void onError(String msg);


    void onClientDetailsResponse(ClientDetailsResponse body);
}
