
package com.tc.crm.base;



public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

}
