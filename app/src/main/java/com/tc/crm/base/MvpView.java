package com.tc.crm.base;



public interface MvpView {

    void showLoading(boolean isCancelable);

    void hideLoading();

    void onResponseError(String error);

    void showProgressBarLoading(boolean isCancelable);

    void hideProgressLoading();

    void onBackPressed();



}
