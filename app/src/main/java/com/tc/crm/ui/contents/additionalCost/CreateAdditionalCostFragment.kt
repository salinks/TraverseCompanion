package com.tc.crm.ui.contents.additionalCost

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BottomSheetBaseFragment
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentCreateAdditionalCostBinding
import com.tc.crm.databinding.FragmentCreateAmenityBinding
import com.tc.crm.model.additionalCost.AdditionalCostRequest
import com.tc.crm.model.additionalCost.AdditionalCostsItem
import com.tc.crm.model.airports.Terminals
import com.tc.crm.model.amenities.AmenitiesItem
import com.tc.crm.model.amenities.AmenitiesRequest
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus


class CreateAdditionalCostFragment : BottomSheetBaseFragment(),
    CACView {

    lateinit var countryId: String
    lateinit var countryName: String
    var mData: AdditionalCostsItem? = null
    private var bottomSheetInternal: FrameLayout? = null
    var mPresenter: CACPresenter? = null

    companion object {
        fun getInstance(): CreateAdditionalCostFragment {
            return CreateAdditionalCostFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = CACPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            R.style.CustomBottomSheetDialogTheme
        )

    }

    lateinit var binder: FragmentCreateAdditionalCostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            com.tc.crm.R.layout.fragment_create_additional_cost,
            container,
            false
        )

        binder.ivBack.setOnClickListener()
        {
            dismiss()
        }

        if (mData == null) {
            countryId= ""
            countryName= ""
            binder.btnCreate.text = "Create Additional Cost"
            binder.appTitle.text = "Create Additional Cost"
            var ter = Terminals()
            ter.terminalName = ""

        } else {
            binder.btnCreate.text = "Update Additional Cost"
            binder.appTitle.text = "Update Additional Cost"
            binder.etAdditionalCostName.setText(mData?.cName)


        }



        binder.btnCreate.setOnClickListener {
            if (validateForm()) {
                prepareData()
            }
        }





        return binder.root
    }





    private fun prepareData() {
        var req = AdditionalCostRequest()
        req.userId = PreferenceManager.getInstance().userId
        req.cName = binder.etAdditionalCostName.text.toString()


        if (mData == null) {
            req.recId = null
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Creating Additional Cost"
                )
            }
              mPresenter?.createAdditionalCost(req)
        } else {
            req.recId = mData?.recId?.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Updating Additional Cost"
                )
            }
            mPresenter?.updateAdditionalCost(req)
        }
    }

    private fun validateForm(): Boolean {

        if (TextUtils.isEmpty(binder.etAdditionalCostName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etAdditionalCostName)
            binder.etAdditionalCostName.error = "Enter Additional Cost Name"
            binder.etAdditionalCostName.requestFocus()
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
        showMessage(1, msg)
        dismiss()
    }

    override fun onCommonResult(body: CommonResult) {
        hideAlertDialog()
        if (body.results.status) {
            showMessage(3, body.results.message)
        } else {
            showMessage(1, body.results.message)
        }

        EventBus.getDefault().post(CountryUpdateEvent())
        dismiss()
    }



}
