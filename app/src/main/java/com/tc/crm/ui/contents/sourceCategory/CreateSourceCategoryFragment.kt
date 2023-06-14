package com.tc.crm.ui.contents.sourceCategory

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
import com.tc.crm.databinding.FragmentCreateSourceCategoryBinding
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.model.sourceCategory.SourceCategoryItem
import com.tc.crm.model.sourceCategory.SourceCategoryRequest
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus

class CreateSourceCategoryFragment : BottomSheetBaseFragment(),
    SCView {

    var mData: SourceCategoryItem? = null
    private var bottomSheetInternal: FrameLayout? = null
    var mPresenter: CSCPresenter? = null

    companion object {
        fun getInstance(): CreateSourceCategoryFragment {
            return CreateSourceCategoryFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = CSCPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    lateinit var binder: FragmentCreateSourceCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_source_category,
            container,
            false
        )


        binder.ivBack.setOnClickListener()
        {
            dismiss()
        }

        if (mData == null) {
            binder.btnCreate.text = "Create Source Category"
            binder.appTitle.text = "Create Source Category"
        } else {
            binder.btnCreate.text = "Update Source Category"
            binder.appTitle.text = "Update Source Category"
            binder.etUSourceCategoryName.setText(mData?.scName)

            binder.cbHasValues.isChecked = mData?.hasValues == 1
            binder.cbInputRequired.isChecked = mData?.inputRequired == 1
        }

        binder.cbHasValues.setOnClickListener {
            if (binder.cbHasValues.isChecked) {
                binder.cbInputRequired.isChecked = false
            }
        }


        binder.cbInputRequired.setOnClickListener {
            if (binder.cbInputRequired.isChecked) {
                binder.cbHasValues.isChecked = false
            }
        }


        binder.btnCreate.setOnClickListener {
            if (validateForm()) {
                prepareData()
            }
        }



        return binder.root
    }

    private fun prepareData() {
        var req = SourceCategoryRequest()
        req.userId = PreferenceManager.getInstance().userId
        req.scName = binder.etUSourceCategoryName.text.toString()
        if(binder.cbHasValues.isChecked){
            req.hasValues ="1"
        }
        else{
            req.hasValues ="0"
        }
        if(binder.cbInputRequired.isChecked){
            req.inputRequired ="1"
        }
        else{
            req.inputRequired ="0"
        }

        if (mData == null) {
            req.scId = null
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Creating new source category"
                )
            }
            mPresenter?.createSourceCategory(req)
        } else {
            req.scId = mData?.scId?.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Updating user source category"
                )
            }
            mPresenter?.updateSourceCategory(req)
        }
    }

    private fun validateForm(): Boolean {

        if (TextUtils.isEmpty(binder.etUSourceCategoryName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etUSourceCategoryName)
            binder.etUSourceCategoryName.error = "Enter source category name"
            binder.etUSourceCategoryName.requestFocus()
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
