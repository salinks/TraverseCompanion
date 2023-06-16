package com.tc.crm.ui.adapter

import android.app.Activity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tc.crm.R
import com.tc.crm.databinding.LayoutAirportItemBinding
import com.tc.crm.databinding.LayoutCountryMasterItemBinding
import com.tc.crm.databinding.LayoutFooterBinding
import com.tc.crm.databinding.LayoutTerminalFooterBinding
import com.tc.crm.databinding.LayoutTerminalItemBinding
import com.tc.crm.model.airports.AirportItem
import com.tc.crm.model.airports.Terminals
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.utils.AnimationUtil
import org.greenrobot.eventbus.EventBus
import java.util.Locale


class TerminalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mContext: Activity
    private var mList: ArrayList<Terminals>? = null


    fun setData(activity: Activity, data: ArrayList<Terminals>?) {
        mList = data
        mContext = activity


    }


    internal inner class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutTerminalItemBinding? = DataBindingUtil.bind(itemView!!)

    }

    internal inner class FooterHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutTerminalFooterBinding? = DataBindingUtil.bind(itemView!!)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {

            VIEW_TYPES.MainView -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_terminal_item, parent, false)
                MainViewHolder(view)
            }

            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_terminal_footer, parent, false)
                FooterHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPES.MainView) {
            val mHolder = holder as MainViewHolder
            var mData = mList?.get(position)


            if (position == 0) {
                mHolder.binding?.cvDelete?.visibility = View.GONE
            }

            mHolder.binding?.etTerminal?.setText(mData?.terminalName)

            mHolder.binding?.cvDelete?.setOnClickListener {
                mList?.remove(mData)
                notifyDataSetChanged()


            }

            mHolder.binding?.etTerminal?.doOnTextChanged { text, start, before, count ->

                mData?.terminalName = text.toString()
            }

            if (mData?.isError == true) {

                AnimationUtil.TranslateView(mContext, mHolder.binding?.etTerminal)
                mHolder.binding!!.etTerminal.error = "Enter terminal name"
                mHolder.binding.etTerminal.requestFocus()
                mData.isError = false
            }


        } else if (getItemViewType(position) == VIEW_TYPES.Footer) {
            val mHolder = holder as FooterHolder
            mHolder.binding?.cvAdd?.setOnClickListener {
                var ter = Terminals()
                ter.terminalName = ""
                mList?.add(ter)
                notifyDataSetChanged()
            }
        }
    }


    override fun getItemCount(): Int {
        return (mList?.size!!) + 1
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