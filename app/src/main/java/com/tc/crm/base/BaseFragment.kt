package com.tc.crm.base;


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.tc.crm.ui.home.HomeActivity


abstract class BaseFragment : Fragment(), MvpView {

    var title: String? = ""
    internal var progressDialog: AlertDialog? = null
    var toolbarVisibility: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //setupToolbarMargin(view)
    }


    override fun showLoading(isCancelable: Boolean) {
        (activity as BaseActivity).showLoading(isCancelable)
    }

    override fun hideLoading() {
        (activity as BaseActivity).hideLoading()
    }


    override fun showProgressBarLoading(isCancelable: Boolean) {
        /*((BaseActivity) getActivity()).showProgressBarLoading(isCancelable);*/
    }


    override fun hideProgressLoading() {
        /* ((BaseActivity) getActivity()).hideProgressLoading();*/
    }

    override fun onResume() {


        super.onResume()
    }


    fun showHideBack(show: Boolean) {
        if (activity is HomeActivity) {
            (activity as? HomeActivity)?.showHideBackButton(show)
        }
    }
    fun showTitle(showTitle: String) {
        if (activity is HomeActivity) {
            (activity as? HomeActivity)?.showTitle(showTitle)
        }
    }

    fun pushFragment(fragment: Fragment) {
/*
        if (activity is HomeActivity) {
            (activity as HomeActivity).pushFragments(fragment)
        }*/

        /* else if (activity is ManageBookingActivity) {
             (activity as ManageBookingActivity).pushFragments( fragment)
         }else if (activity is FlightsActivity) {
             (activity as FlightsActivity).pushFragments( fragment)
         }else if (activity is CheckoutActivity) {
             (activity as CheckoutActivity).pushFragments( fragment)
         }*/
    }


    override fun onResponseError(code: String) {
        //  ShowMessage(DefValues.ERROR,getString(R.string.exception_message),code)
    }


    fun showMessage(type: Int?, Message: String?) {
        (activity as? BaseActivity)?.showMessage(type, Message)

    }





    override fun onBackPressed() {

//        if (activity is HomeActivity) {
//            (activity as HomeActivity).onBackPressed()
//        }


    }
}