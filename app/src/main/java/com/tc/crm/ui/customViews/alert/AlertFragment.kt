package com.tc.crm.ui.customViews.alert


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tc.crm.R
import com.tc.crm.base.BottomSheetBaseFragment
import com.tc.crm.data.listners.AlertListener
import com.tc.crm.databinding.AlertBottomSheetBinding


class AlertFragment : BottomSheetBaseFragment() {
    var binder: AlertBottomSheetBinding? = null
    private var mTitle: String? = ""
    private var mMessage: String? = ""
    private var mpositiveButtonText: String? = ""
    private var mnegativeBUttonText: String? = ""
    private var listenr: AlertListener? = null
    var showNegative: Boolean? = false
    var isDismiss: Boolean? = false
    var showImage: Boolean? = false
    var resource : Int? = null
    private var bottomSheetInternal: FrameLayout? = null

    companion object {


        fun getInstance(): AlertFragment {
            return AlertFragment()

        }

    }

    fun setAlertTitle(value: String) {
        mTitle = value
    }

    fun setMessageText(value: String) {
        mMessage = value
    }

    fun setPositiveButtonText(value: String) {
        mpositiveButtonText = value
    }

    fun setNegativeButtonText(value: String) {
        mnegativeBUttonText = value
    }

    fun setOnClickLlistner(value: AlertListener) {
        listenr = value
    }
    fun setDismiss(value:Boolean){
        isDismiss = value
    }
    fun showImageView(showImage:Boolean,resource : Int){
        this.showImage = showImage
        this.resource = resource
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.alert_bottom_sheet, container, false)
        return binder?.root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet);
            val coordinatorLayout = bottomSheet?.parent
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.peekHeight = bottomSheet?.height!!
            coordinatorLayout?.parent?.requestLayout()
            bottomSheetInternal = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        }
        binder?.tvTitle?.text = "${mTitle}"
        binder?.tvmessage?.text = "${mMessage}"
        binder?.btDone?.text = "${mpositiveButtonText}"
        binder?.btnOk?.text = "${mpositiveButtonText}"
        binder?.btCancel?.text = "${mnegativeBUttonText}"
        binder?.btCancel?.setOnClickListener {
            listenr?.onNegativeClick(this)
        }
        if(showImage==true){
            binder?.ivImage?.visibility = View.VISIBLE
            activity?.let { binder?.ivImage?.let { it1 -> Glide.with(it).asGif().load(resource).into(it1) } }
        }
        if(isDismiss==false){
            isCancelable = (false)
        }else{
            isCancelable = true
        }
        if (TextUtils.isEmpty(mnegativeBUttonText)) {
            binder?.lnrButtons?.visibility = View.GONE
            binder?.btnOk?.visibility = View.VISIBLE
        } else {
            binder?.lnrButtons?.visibility = View.VISIBLE
            binder?.btnOk?.visibility = View.GONE
        }
        if(TextUtils.isEmpty(mpositiveButtonText) && TextUtils.isEmpty(mnegativeBUttonText) ){
            binder?.btnOk?.visibility = View.GONE
            binder?.lnrButtons?.visibility = View.GONE
        }
        binder?.btDone?.setOnClickListener {
            listenr?.onPositiveClick(this)
        }
        binder?.btnOk?.setOnClickListener {
            listenr?.onPositiveClick(this)
        }
    }
}
