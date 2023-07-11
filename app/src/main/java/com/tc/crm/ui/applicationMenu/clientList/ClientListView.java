package com.tc.crm.ui.applicationMenu.clientList;



import com.tc.crm.model.clientList.ClientListResponse;

public interface ClientListView {


    void onError(String msg);

    void onClientListResponse(ClientListResponse body);
}
