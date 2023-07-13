package com.tc.crm.ui.applicationMenu.editClient;


import com.tc.crm.data.model.CommonResult;

public interface EditClientView {


    void onError(String msg);


    void onCommonResult(CommonResult body);

}
