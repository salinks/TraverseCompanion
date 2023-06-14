package com.tc.crm.ui.masters.countries

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BottomSheetBaseFragment
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentCreateCountryMasterBinding
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.model.countries.CountryRequest
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus

class CreateCountryMasterFragment : BottomSheetBaseFragment(),
    CCMView {

    var mData: CountryMasterItem? = null
    private var bottomSheetInternal: FrameLayout? = null
    var mPresenter: CCMPresenter? = null

    companion object {
        fun getInstance(): CreateCountryMasterFragment {
            return CreateCountryMasterFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = CCMPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    lateinit var binder: FragmentCreateCountryMasterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_country_master,
            container,
            false
        )


        binder.ivBack.setOnClickListener()
        {
            dismiss()
        }

        if (mData == null) {
            binder.btnCreate.text = "Create Country"
            binder.appTitle.text = "Create Country"
        } else {
            binder.btnCreate.text = "Update Country"
            binder.appTitle.text = "Update Country"
            binder.etCountryName.setText(mData?.cName)
            binder.etCountryCode.setText(mData?.cCode)
            binder.etPhoneCode.setText(mData?.phoneCode)
            binder.etCurrencyName.setText(mData?.currName)
            binder.etCurrencyCode.setText(mData?.currCode)
            binder.etCurrencyShortName.setText(mData?.currShortName)
        }



        binder.btnCreate.setOnClickListener {
            if(validateForm()){
                prepareData()
            }
        }



        return binder.root
    }

    private fun prepareData() {
        var req = CountryRequest()
        req.userId = PreferenceManager.getInstance().userId
        req.countryName = binder.etCountryName.text.toString()
        req.countryCode = binder.etCountryCode.text.toString()
        req.phoneCode = binder.etPhoneCode.text.toString()
        req.currencyName = binder.etCurrencyName.text.toString()
        req.currencyCode = binder.etCurrencyCode.text.toString()
        req.currShortName = binder.etCurrencyShortName.text.toString()
        if (mData == null) {
          req.countryId = null
            activity?.let {
                TCApp.showAlertDialog(0,
                    it, "Please Wait", "Creating new country")
            }
            mPresenter?.createCountryMaster(req)
        }
        else{
            req.countryId = mData?.cId
            activity?.let {
                TCApp.showAlertDialog(0,
                    it, "Please Wait", "Updating country data")
            }
            mPresenter?.updateCountryMaster(req)
        }
    }

    private fun validateForm(): Boolean {

        if (TextUtils.isEmpty(binder.etCountryName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etCountryName)
            binder.etCountryName.error = "Enter country name"
            binder.etCountryName.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binder.etCountryCode.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etCountryCode)
            binder.etCountryCode.error = "Enter country code"
            binder.etCountryCode.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binder.etPhoneCode.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etPhoneCode)
            binder.etPhoneCode.error = "Enter phone code"
            binder.etPhoneCode.requestFocus()
            return false
        }
        if (TextUtils.isEmpty(binder.etCurrencyName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etCurrencyName)
            binder.etCurrencyName.error = "Enter currency name"
            binder.etCurrencyName.requestFocus()
            return false
        }
        if (TextUtils.isEmpty(binder.etCurrencyCode.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etCurrencyCode)
            binder.etCurrencyCode.error = "Enter currency code"
            binder.etCurrencyCode.requestFocus()
            return false
        }
        if (TextUtils.isEmpty(binder.etCurrencyShortName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etCurrencyShortName)
            binder.etCurrencyShortName.error = "Enter currency short code"
            binder.etCurrencyShortName.requestFocus()
            return false
        }


return true
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val coordinatorLayout = bottomSheet?.parent
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.peekHeight = bottomSheet.height
            coordinatorLayout?.parent?.requestLayout()
            bottomSheetInternal =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    override fun onError(msg: String?) {
        hideAlertDialog()
        showMessage(1,msg)
        dismiss()
    }

    override fun onCommonResult(body: CommonResult) {
        hideAlertDialog()
        if(body.results.status){
            showMessage(3,body.results.message)
        }
        else{
            showMessage(1,body.results.message)
        }

        EventBus.getDefault().post(CountryUpdateEvent())
        dismiss()
    }


}
