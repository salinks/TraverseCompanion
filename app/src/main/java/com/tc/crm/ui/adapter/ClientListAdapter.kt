package com.tc.crm.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tc.crm.R
import com.tc.crm.databinding.LayoutClientListItemBinding
import com.tc.crm.databinding.LayoutFooterBinding
import com.tc.crm.model.clientList.ClientsItem
import com.tc.crm.ui.applicationMenu.clientDetails.ClientDetailsFragment
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.DateUtils
import com.tc.crm.utils.GlobalVariables


class ClientListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mContext: Activity
    private var mList: List<ClientsItem>? = null


    fun setData(activity: Activity, data: List<ClientsItem>?) {
        mList = data
        mContext = activity


    }


    internal inner class MainViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutClientListItemBinding? = DataBindingUtil.bind(itemView!!)

    }

    internal inner class FooterHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val binding: LayoutFooterBinding? = DataBindingUtil.bind(itemView!!)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {

            VIEW_TYPES.MainView -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_client_list_item, parent, false)
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
            val mHolder = holder as MainViewHolder
            var mData = mList?.get(position)

            if (mData?.cStatus == 0) {
                mHolder.binding!!.tvClientStatus.text = mContext.getString(R.string.sts_open)
                mHolder.binding!!.tvClientStatus.setTextColor(Color.parseColor("#007bff"))
                mHolder.binding.cvStatus.setCardBackgroundColor(Color.parseColor("#007bff"))
            } else if (mData?.cStatus == 1) {
                mHolder.binding!!.tvClientStatus.text = mContext.getString(R.string.sts_pending)
                mHolder.binding.tvClientStatus.setTextColor(Color.parseColor("#ffc107"))
                mHolder.binding.cvStatus.setCardBackgroundColor(Color.parseColor("#ffc107"))
            } else if (mData?.cStatus == 2) {
                mHolder.binding!!.tvClientStatus.text = mContext.getString(R.string.sts_in_progress)
                mHolder.binding.tvClientStatus.setTextColor(Color.parseColor("#0c2556"))
                mHolder.binding.cvStatus.setCardBackgroundColor(Color.parseColor("#0c2556"))
            } else if (mData?.cStatus == 3) {
                mHolder.binding!!.tvClientStatus.text = mContext.getString(R.string.sts_rejected)
                mHolder.binding.tvClientStatus.setTextColor(Color.parseColor("#dc3545"))
                mHolder.binding.cvStatus.setCardBackgroundColor(Color.parseColor("#dc3545"))
            } else if (mData?.cStatus == 4) {
                mHolder.binding!!.tvClientStatus.text = mContext.getString(R.string.sts_closed)
                mHolder.binding.tvClientStatus.setTextColor(Color.parseColor("#28a745"))
                mHolder.binding.cvStatus.setCardBackgroundColor(Color.parseColor("#28a745"))
            } else if (mData?.cStatus == 5) {
                mHolder.binding!!.tvClientStatus.text = mContext.getString(R.string.sts_cancelled_advance)
                mHolder.binding.tvClientStatus.setTextColor(Color.parseColor("#a13b0a"))
                mHolder.binding.cvStatus.setCardBackgroundColor(Color.parseColor("#a13b0a"))
            }
            else if (mData?.cStatus == 6) {
                mHolder.binding!!.tvClientStatus.text = mContext.getString(R.string.sts_cancelled_full_pay)
                mHolder.binding.tvClientStatus.setTextColor(Color.parseColor("#a13b0a"))
                mHolder.binding.cvStatus.setCardBackgroundColor(Color.parseColor("#a13b0a"))
            }

            mHolder.binding!!.tvClientName.text = mData?.givenName + ' ' + mData?.surname
            mHolder.binding.tvClientReference.text = mData?.slnumber
            mHolder.binding.tvClientType.text = mData?.clientType
            mHolder.binding.tvClientStage.text = mData?.stageName
            mHolder.binding.tvStaffName.text = mData?.staffName
            mHolder.binding.tvCreatedOn.text = DateUtils.oneFormatToAnother(
                mData?.createdOn,
                "yyyy-MM-dd HH:mm:ss",
                "dd-MM-yyyy hh:mm a"
            )

            if(!TextUtils.isEmpty(mData?.nfd) && !mData?.nfd.equals("0000-00-00 00:00:00") ){
                mHolder.binding.tvNFD.text = DateUtils.oneFormatToAnother(
                    mData?.nfd,
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MM-yyyy hh:mm a"
                )
            }

            mHolder.binding.cvRoot.setOnClickListener {

                if(mHolder.binding.lnrDetails.visibility == View.VISIBLE){
                    AnimationUtil.animateCollapse(mHolder.binding.lnrDetails)
                }
                else{
                    AnimationUtil.animateExpand(mHolder.binding.lnrDetails)
                }

            }

            mHolder.binding.cvViewClient.setOnClickListener {

              GlobalVariables.ClientID = mData?.clientId?.toString()
                (mContext as HomeActivity).pushFragments(ClientDetailsFragment())

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