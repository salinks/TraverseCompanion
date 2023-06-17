package com.tc.crm.ui.contents.activityLogs


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.listners.AlertListener
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentActivityLogsBinding
import com.tc.crm.model.activityLogs.ActivityLogsItem
import com.tc.crm.model.activityLogs.ActivityLogsResponse
import com.tc.crm.ui.adapter.ActivityLogsAdapter
import com.tc.crm.ui.customViews.alert.AlertFragment
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.Locale


class ActivityLogsFragment : BaseFragment(),
    ActivityLogsView {

    private var binding: FragmentActivityLogsBinding? = null
    var mPresenter: ActivityLogsPresenter? = null
    var mAdapter = ActivityLogsAdapter()
    var mList: ArrayList<ActivityLogsItem> = ArrayList()

    companion object {
        fun getInstance(): ActivityLogsFragment {
            return ActivityLogsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter =
            ActivityLogsPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_activity_logs, container, false)

        binding?.rcvList?.layoutManager = LinearLayoutManager(activity)


        binding?.etSearch?.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                val tempList = java.util.ArrayList<ActivityLogsItem>()

                for (i in 0 until mList.size) {
                    if (mList[i].actionTag.lowercase(Locale.ROOT).contains(text.toString().lowercase(Locale.ROOT) )
                    ) {
                        tempList.add(mList[i])
                    }
                }
                activity?.let { mAdapter.setData(it, tempList) }
                binding?.rcvList?.adapter = mAdapter
            } else {
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvList?.adapter = mAdapter
            }

        }

        binding!!.relClear.setOnClickListener {
            var req = CommonRequest()
            req.userId = PreferenceManager.getInstance().userId
            askConfirmation(req)
        }




        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showTitle("Activity Logs")
        binding?.progressView?.visibility = View.VISIBLE
        binding?.relClear?.visibility = View.GONE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getActivityLogs(req)

    }


    override fun onError(msg: String?) {
        binding?.progressView?.visibility = View.GONE
        hideAlertDialog()
    }

    override fun onActivityLogsResponse(body: ActivityLogsResponse) {
        hideAlertDialog()
        binding?.progressView?.visibility = View.GONE

        if (body.results?.status == true) {

            if (body.activityLogs != null && body.activityLogs.size > 0) {
                binding?.relClear?.visibility = View.VISIBLE
                binding?.tvNoData?.visibility = View.GONE
                mList = body.activityLogs
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvList?.adapter = mAdapter
            } else {
                 binding?.relClear?.visibility = View.GONE
                binding?.tvNoData?.visibility = View.VISIBLE
                mList = ArrayList()
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvList?.adapter = mAdapter
            }
        } else {

            if (body.results?.message.equals("AUTHORIZATION_FAILDED") ||
                body.results?.message.equals("AUTHORIZATION_FAILDED")
            ) {
                (activity as HomeActivity).logout()
            }
        }

    }

    override fun onCommonResult(body: CommonResult) {
        hideAlertDialog()
        if (body.results.status) {
            showMessage(3, body.results.message)
            var req = CommonRequest()
            req.userId = PreferenceManager.getInstance().userId
            mPresenter?.getActivityLogs(req)
        } else {
            showMessage(1, body.results.message)
        }

    }


    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        EventBus.getDefault().removeStickyEvent(this)
        EventBus.getDefault().removeAllStickyEvents()
    }



    @Subscribe
    fun onCountryClick(request: ActivityLogsItem) {
        if (request.actionName.equals("View", ignoreCase = true)) {
        showInfo(request)
        }


    }

    private fun showInfo(request: ActivityLogsItem) {
        var desc = request.actionDesc.replace("<br>","\n")
        val fragment = AlertFragment.getInstance()
        fragment.setAlertTitle("Information")
        fragment.setMessageText(desc)
        fragment.setPositiveButtonText("OK")
        fragment.setOnClickLlistner(object : AlertListener {
            override fun onPositiveClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
             }

            override fun onNegativeClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
            }
        })
        fragment.show(childFragmentManager, "alertr")
    }


    private fun askConfirmation(req: CommonRequest) {
        val fragment = AlertFragment.getInstance()
        fragment.setAlertTitle(getString(R.string.app_name))
        fragment.setMessageText("Clear all activity logs?")
        fragment.setPositiveButtonText("Delete")
        fragment.setNegativeButtonText("Cancel")
        fragment.setOnClickLlistner(object : AlertListener {
            override fun onPositiveClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()

                activity?.let {
                    TCApp.showAlertDialog(
                        0,
                        it,
                        "Please Wait",
                        ""
                    )
                }


                mPresenter?.clearActivityLogs(req)

            }

            override fun onNegativeClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
            }
        })
        fragment.show(childFragmentManager, "alertr")
    }



}
