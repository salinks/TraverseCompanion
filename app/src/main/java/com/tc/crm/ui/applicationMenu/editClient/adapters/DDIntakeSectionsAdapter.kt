package com.tc.crm.ui.applicationMenu.editClient.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tc.crm.R
import com.tc.crm.databinding.LayoutFooterBinding
import com.tc.crm.databinding.LayoutSelctionViewBinding
import com.tc.crm.model.clientDetails.IntakeSectionsItem
import com.tc.crm.ui.applicationMenu.editClient.fragments.DDIntakeSectionsFragment
import com.tc.crm.utils.GlobalVariables.CLIENT_DETAILS
import org.greenrobot.eventbus.EventBus


class DDIntakeSectionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mContext: Activity
    lateinit var mFragment: DDIntakeSectionsFragment
    private var mList: List<IntakeSectionsItem>? = null


    fun setData(activity: Activity, data: List<IntakeSectionsItem>?, mFrag : DDIntakeSectionsFragment) {
        mList = data
        mContext = activity
        mFragment = mFrag


    }


    internal inner class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutSelctionViewBinding? = DataBindingUtil.bind(itemView!!)

    }

    internal inner class FooterHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutFooterBinding? = DataBindingUtil.bind(itemView!!)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {

            VIEW_TYPES.MainView -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_selction_view, parent, false)
                MainViewHolder(view)
            }

            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_footer, parent, false)
                FooterHolder(view)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPES.MainView) {
            val molder = holder as MainViewHolder
            var mData = mList?.get(position)


            molder.binding?.tvName?.text =mData?.sectionName+", "+ mData?.cCode

            if(CLIENT_DETAILS.clientInfo.iSectionId.equals(mData?.yId.toString())){
                molder.binding?.ivSelected?.visibility = View.VISIBLE
            }
            else{
                molder.binding?.ivSelected?.visibility = View.INVISIBLE
            }

            molder.binding?.lnrRoot?.setOnClickListener {
                EventBus.getDefault().post(mData)
                mFragment.dismiss()
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