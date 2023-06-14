package com.tc.crm.ui.contents.userGroups

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
import com.tc.crm.databinding.FragmentCreateUserGroupBinding
import com.tc.crm.model.countries.CountryRequest
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.model.userGroups.UserGroupsItem
import com.tc.crm.model.userGroups.UserGroupsRequest
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus

class CreateCountryMasterFragment : BottomSheetBaseFragment(),
    CUGView {

    var mData: UserGroupsItem? = null
    private var bottomSheetInternal: FrameLayout? = null
    var mPresenter: CUGPresenter? = null

    companion object {
        fun getInstance(): CreateCountryMasterFragment {
            return CreateCountryMasterFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = CUGPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    lateinit var binder: FragmentCreateUserGroupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_user_group,
            container,
            false
        )


        binder.ivBack.setOnClickListener()
        {
            dismiss()
        }

        if (mData == null) {
            binder.btnCreate.text = "Create User Group"
            binder.appTitle.text = "Create User Group"
        } else {
            binder.btnCreate.text = "Update User Group"
            binder.appTitle.text = "Update User Group"
            binder.etUserGroupName.setText(mData?.desName)
        }



        binder.btnCreate.setOnClickListener {
            if (validateForm()) {
                prepareData()
            }
        }



        return binder.root
    }

    private fun prepareData() {
        var req = UserGroupsRequest()
        req.userId = PreferenceManager.getInstance().userId
        req.desName = binder.etUserGroupName.text.toString()

        if (mData == null) {
            req.desId = null
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Creating new user group"
                )
            }
            mPresenter?.createUserGroup(req)
        } else {
            req.desId = mData?.desId?.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Updating user group data"
                )
            }
            mPresenter?.updateUserGroup(req)
        }
    }

    private fun validateForm(): Boolean {

        if (TextUtils.isEmpty(binder.etUserGroupName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etUserGroupName)
            binder.etUserGroupName.error = "Enter user group name"
            binder.etUserGroupName.requestFocus()
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
