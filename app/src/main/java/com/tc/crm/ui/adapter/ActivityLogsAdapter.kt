package com.tc.crm.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tc.crm.R
import com.tc.crm.databinding.LayoutActivityLogBinding
import com.tc.crm.databinding.LayoutFooterBinding
import com.tc.crm.model.activityLogs.ActivityLogsItem
import com.tc.crm.utils.DateUtils
import org.greenrobot.eventbus.EventBus


class ActivityLogsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mContext: Activity
    private var mList: List<ActivityLogsItem>? = null


    fun setData(activity: Activity, data: List<ActivityLogsItem>?) {
        mList = data
        mContext = activity


    }


    internal inner class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutActivityLogBinding? = DataBindingUtil.bind(itemView!!)

    }

    internal inner class FooterHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutFooterBinding? = DataBindingUtil.bind(itemView!!)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {

            VIEW_TYPES.MainView -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_activity_log, parent, false)
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




            molder.binding?.tvTagName?.text = mData?.actionTag
            molder.binding?.tvActionBy?.text = mData?.actionBy
            molder.binding?.tvDate?.text = DateUtils.oneFormatToAnother(
                mData?.actionDate,
                "yyyy-MM-dd HH:mm:ss",
                "dd-MM-yyyy hh:mm a"
            )



            molder.binding?.relRoot?.setOnClickListener {
                mData?.actionName = "View"
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