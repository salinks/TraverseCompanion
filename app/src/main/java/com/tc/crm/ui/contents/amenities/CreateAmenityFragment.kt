package com.tc.crm.ui.contents.amenities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BottomSheetBaseFragment
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentCreateAmenityBinding
import com.tc.crm.databinding.FragmentCreateIntakeSectionBinding
import com.tc.crm.model.airports.Terminals
import com.tc.crm.model.amenities.AmenitiesItem
import com.tc.crm.model.amenities.AmenitiesRequest
import com.tc.crm.model.countries.CountriesResponse
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.model.intakeSections.IntakeSectionRequest
import com.tc.crm.model.intakeSections.IntakeSectionsItem
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus


class CreateAmenityFragment : BottomSheetBaseFragment(),
    CAView {

    lateinit var countryId: String
    lateinit var countryName: String
    var mData: AmenitiesItem? = null
    private var bottomSheetInternal: FrameLayout? = null
    var mPresenter: CAPresenter? = null

    companion object {
        fun getInstance(): CreateAmenityFragment {
            return CreateAmenityFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = CAPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            R.style.CustomBottomSheetDialogTheme
        )

    }

    lateinit var binder: FragmentCreateAmenityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            com.tc.crm.R.layout.fragment_create_amenity,
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
            binder.btnCreate.text = "Create Amenity"
            binder.appTitle.text = "Create Amenity"
            var ter = Terminals()
            ter.terminalName = ""

        } else {
            binder.btnCreate.text = "Update Amenity"
            binder.appTitle.text = "Update Amenity"
            binder.etAmenityName.setText(mData?.amenityName)


        }



        binder.btnCreate.setOnClickListener {
            if (validateForm()) {
                prepareData()
            }
        }





        return binder.root
    }





    private fun prepareData() {
        var req = AmenitiesRequest()
        req.userId = PreferenceManager.getInstance().userId
        req.amenityName = binder.etAmenityName.text.toString()


        if (mData == null) {
            req.aId = null
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Creating new section"
                )
            }
              mPresenter?.createAmenity(req)
        } else {
            req.aId = mData?.aId?.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Updating Section"
                )
            }
            mPresenter?.updateAmenity(req)
        }
    }

    private fun validateForm(): Boolean {

        if (TextUtils.isEmpty(binder.etAmenityName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etAmenityName)
            binder.etAmenityName.error = "Enter Amenity Name"
            binder.etAmenityName.requestFocus()
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
