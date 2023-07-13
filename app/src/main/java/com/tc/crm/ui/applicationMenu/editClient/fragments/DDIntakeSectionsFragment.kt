package com.tc.crm.ui.applicationMenu.editClient.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tc.crm.R
import com.tc.crm.base.BottomSheetBaseFragment
import com.tc.crm.databinding.FragmentIntakeSelectionBinding
import com.tc.crm.ui.applicationMenu.editClient.adapters.DDIntakeSectionsAdapter
import com.tc.crm.utils.GlobalVariables.CLIENT_DETAILS


class DDIntakeSectionsFragment : BottomSheetBaseFragment(){


    private var bottomSheetInternal: FrameLayout? = null
    var mAdapter = DDIntakeSectionsAdapter()

    companion object {
        fun getInstance(): DDIntakeSectionsFragment {
            return DDIntakeSectionsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            R.style.CustomBottomSheetDialogTheme
        )

    }

    lateinit var binder: FragmentIntakeSelectionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            com.tc.crm.R.layout.fragment_intake_selection,
            container,
            false
        )
        binder.rcvIS.layoutManager = LinearLayoutManager(activity)
        var mAdapter = DDIntakeSectionsAdapter()
        activity?.let { mAdapter.setData(it,CLIENT_DETAILS.intakeSections,this) }
        binder.rcvIS.adapter = mAdapter

        binder.ivBack.setOnClickListener()
        {
            dismiss()
        }






        return binder.root
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


}
