package com.tc.crm.base;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tc.crm.R;


public abstract class BottomSheetBaseFragment extends BottomSheetDialogFragment implements MvpView {

    public BottomSheetBaseFragment() {
        //  LocaleUtils.updateConfig(getActivity().getApplication(),getActivity().getBaseContext().getResources().getConfiguration());
    }

    private String title = "";
    AlertDialog progressDialog;
    public boolean showToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Override
    public void showLoading(boolean isCancelable) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showProgressBarLoading(boolean isCancelable) {
        /*((BaseActivity) getActivity()).showProgressBarLoading(isCancelable);*/
    }

    public void showToolbar(boolean status)
    {

    }

    @Override
    public void hideProgressLoading() {
       /* ((BaseActivity) getActivity()).hideProgressLoading();*/
    }

    @Override
    public void onResume() {
        /*LocaleUtils.setLocale();
        getActivity().getWindow().getDecorView().setLayoutDirection(Locale.getDefault().getLanguage().toLowerCase().equalsIgnoreCase("ar")
                ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);*/
        super.onResume();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean getToolbarVisibility() {
        return showToolbar;
    }

    public void inject(Fragment fragment) {
    }

    @Override
    public void onResponseError(String code) {
      //  ShowMessage(DefValues.ERROR, getString(R.string.exception_message),code);
    }

    public void ShowMessage(int type, String Message, String code) {
        if(getActivity() instanceof BaseActivity){
        //    ((BaseActivity)getActivity()).ShowMessage(type,Message,code);
        }

    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

/*    public void pushFragmentWithoutAnimation(Fragment previousFragment, Fragment fragment, View view, String name){
        if(getActivity() instanceof HotelsActivity){
            ((HotelsActivity) getActivity()).pushFragmentwithoutAnimation(previousFragment,fragment,view,name);
        }else if(getActivity() instanceof ToursActivity){
            ((ToursActivity) getActivity()).pushFragmentwithoutAnimation(previousFragment,fragment,view,name);
        }else if(getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).pushFragmentwithoutAnimation(previousFragment,fragment,view,name);
        }
    }*/

    public void pushFragment(Fragment previousFragment, Fragment fragment) {
        /*if (getActivity() instanceof ManageBookingActivity) {
            ((ManageBookingActivity) getActivity()).pushFragments(fragment);
        }*/

      /*  if (getActivity() instanceof HotelsActivity) {
            ((HotelsActivity) getActivity()).pushFragments(previousFragment, fragment);
        } else if (getActivity() instanceof PackageActivity) {
            ((PackageActivity) getActivity()).pushFragments(fragment);
        } else if (getActivity() instanceof DashboardActivity) {
            ((DashboardActivity) getActivity()).pushFragments(fragment);
        } else if (getActivity() instanceof CheckoutActivity) {
            ((CheckoutActivity) getActivity()).pushFragments(fragment);
        } else  else if(getActivity() instanceof ToursActivity) {
            ((ToursActivity) getActivity()).pushFragments(previousFragment, fragment);
        }else if(getActivity() instanceof CampaignActivity){
            ((CampaignActivity) getActivity()).pushFragments(fragment);
        }else if(getActivity() instanceof FlightsActivity){
            ((FlightsActivity) getActivity()).pushFragments(fragment);
        }else if(getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).pushFragments(previousFragment,fragment);
        }else{
            ((HomeActivity) getActivity()).pushFragments(fragment);
        }*/
    }




    public void ShowHideDrawer()
    {
       /* if (getActivity() instanceof HomeActivity) {
            ((HomeActivity)getActivity()).ShowHideDrawer();
        }*/

        /*if (getActivity() instanceof ContainerActivity) {
            ((ContainerActivity)getActivity()).ShowHideDrawer();
        }
        else
        else if (getActivity() instanceof DashboardActivity) {
            ((DashboardActivity)getActivity()).ShowHideDrawer();
        } else if (getActivity() instanceof PassengersDetailsActivitySaudia) {
            ((PassengersDetailsActivitySaudia)getActivity()).ShowHideDrawer();
        }else if (getActivity() instanceof AboutActivity) {
            ((AboutActivity)getActivity()).ShowHideDrawer();
        }else if (getActivity() instanceof ManageBookingActivity) {
            ((ManageBookingActivity)getActivity()).ShowHideDrawer();
        }else if (getActivity() instanceof CheckoutActivity) {
            ((CheckoutActivity)getActivity()).ShowHideDrawer();
        }*/
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
            Log.d("STATE LOSSS", "Exception", e);
        }
    }


}
