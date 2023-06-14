package com.tc.crm.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tc.crm.R
import com.tc.crm.databinding.LayoutCountryMasterItemBinding
import com.tc.crm.databinding.LayoutFooterBinding
import com.tc.crm.databinding.LayoutSourceCategoryItemBinding
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.model.sourceCategory.SourceCategoryItem
import com.tc.crm.utils.AnimationUtil
import org.greenrobot.eventbus.EventBus


class SourceCategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mContext: Activity
    private var mList: List<SourceCategoryItem>? = null


    fun setData(activity: Activity, data: List<SourceCategoryItem>?) {
        mList = data
        mContext = activity


    }


    internal inner class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutSourceCategoryItemBinding? = DataBindingUtil.bind(itemView!!)

    }

    internal inner class FooterHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutFooterBinding? = DataBindingUtil.bind(itemView!!)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {

            VIEW_TYPES.MainView -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_source_category_item, parent, false)
                MainViewHolder(view)
            }

            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_footer, parent, false)
                FooterHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPES.MainView) {
            val molder = holder as MainViewHolder
            var mData = mList?.get(position)


            if (mData?.scStatus == 0) {

                molder.binding?.btnEnable?.visibility = View.VISIBLE
                molder.binding?.btnDisable?.visibility = View.GONE
                molder.binding?.tvStatus?.text = "Disabled"
                molder.binding?.tvStatus?.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.pending
                    )
                )
            } else if (mData?.scStatus == 1) {
                molder.binding?.btnEnable?.visibility = View.GONE
                molder.binding?.btnDisable?.visibility = View.VISIBLE
                molder.binding?.tvStatus?.text = "Enabled"
                molder.binding?.tvStatus?.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.green
                    )
                )
            }

            molder.binding?.tvSourceCategory?.text = mData?.scName
            if (mData?.hasValues == 0) {
                molder.binding?.tvHasValues?.text = "No"
            }
            else{
                molder.binding?.tvHasValues?.text = "Yes"
            }

            if (mData?.inputRequired == 0) {
                molder.binding?.tvInputRequired?.text = "No"
            }
            else{
                molder.binding?.tvInputRequired?.text = "Yes"
            }


            molder.binding?.relRoot?.setOnClickListener() {

                if (molder.binding?.cvActionView?.visibility == View.VISIBLE) {
                    AnimationUtil.animateCollapse(molder.binding.cvActionView)
                } else {
                    AnimationUtil.animateExpand(molder.binding.cvActionView)
                }
            }

            molder.binding?.btnEnable?.setOnClickListener {
                AnimationUtil.animateCollapse(molder.binding.cvActionView)
                mData?.actionName = "Enable"
                EventBus.getDefault().post(mData)
            }
            molder.binding?.btnDisable?.setOnClickListener {
                AnimationUtil.animateCollapse(molder.binding.cvActionView)
                mData?.actionName = "Disable"
                EventBus.getDefault().post(mData)
            }

            molder.binding?.btnEdit?.setOnClickListener {
                AnimationUtil.animateCollapse(molder.binding.cvActionView)
                mData?.actionName = "Edit"
                EventBus.getDefault().post(mData)
            }

            molder.binding?.btnDelete?.setOnClickListener {
                AnimationUtil.animateCollapse(molder.binding.cvActionView)
                mData?.actionName = "Delete"
                EventBus.getDefault().post(mData)
            }


        }
    }


    override fun getItemCount(): Int {
        return mList?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mList!!.size) {
            VIEW_TYPES.Footer
        } else {
            VIEW_TYPES.MainView
        }
    }


    object VIEW_TYPES {
        const val Footer = 0
        const val MainView = 1

    }


}