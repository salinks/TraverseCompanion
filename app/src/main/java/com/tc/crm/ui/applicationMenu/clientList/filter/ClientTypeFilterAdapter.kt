package com.tc.crm.ui.applicationMenu.clientList.filter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tc.crm.R
import com.tc.crm.databinding.LayoutClientFilterItemBinding
import com.tc.crm.databinding.LayoutFooterBinding
import com.tc.crm.model.filters.FilterSelection
import com.tc.crm.model.clientList.ClientTypeFilterItem
import org.greenrobot.eventbus.EventBus


class ClientTypeFilterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mContext: Activity
    private var mList: List<ClientTypeFilterItem>? = null


    fun setData(activity: Activity, data: List<ClientTypeFilterItem>?) {
        mList = data
        mContext = activity


    }


    internal inner class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutClientFilterItemBinding? = DataBindingUtil.bind(itemView!!)

    }

    internal inner class FooterHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutFooterBinding? = DataBindingUtil.bind(itemView!!)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {

            VIEW_TYPES.MainView -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_client_filter_item, parent, false)
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
            val mHolder = holder as MainViewHolder
            var mData = mList?.get(position)
            mHolder.binding?.tvFilterName?.text = mData?.value

            if (mList?.get(position)?.isSelected == true) {
                mHolder.binding?.ivSelected?.visibility = View.VISIBLE
            } else {
                mHolder.binding?.ivSelected?.visibility = View.INVISIBLE
            }

            mHolder.binding!!.lnrRoot.setOnClickListener {
                for (i in 0 until mList!!.size) {
                    if (i == position) {
                        mList?.get(i)?.isSelected = mList?.get(i)?.isSelected != true

                    } else {
                        mList?.get(i)?.isSelected = false
                    }
                    notifyItemChanged(i)
                }
                val eve = FilterSelection()
                EventBus.getDefault().post(eve)


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