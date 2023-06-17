package com.tc.crm.ui.contents.additionalCost


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
import com.tc.crm.data.model.EnableDisableRequest
import com.tc.crm.databinding.FragmentAdditionalCostBinding
import com.tc.crm.model.additionalCost.AdditionalCostResponse
import com.tc.crm.model.additionalCost.AdditionalCostsItem
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.ui.adapter.AdditionalCostAdapter
import com.tc.crm.ui.customViews.alert.AlertFragment
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.Locale


class AdditionalCostFragment : BaseFragment(),
    AdditionalCostView {

    private var binding: FragmentAdditionalCostBinding? = null
    var mPresenter: AdditionalCostPresenter? = null
    var mAdapter = AdditionalCostAdapter()
    var mList: ArrayList<AdditionalCostsItem> = ArrayList()

    companion object {
        fun getInstance(): AdditionalCostFragment {
            return AdditionalCostFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter =
            AdditionalCostPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_additional_cost, container, false)

        binding?.rcvList?.layoutManager = LinearLayoutManager(activity)


        binding?.etSearch?.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                val tempList = java.util.ArrayList<AdditionalCostsItem>()

                for (i in 0 until mList.size) {
                    if (mList[i].cName.lowercase(Locale.ROOT).contains(text.toString().lowercase(Locale.ROOT) )
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

        binding?.relNewAdditionalCost?.setOnClickListener {
            var frag = CreateAdditionalCostFragment()
            frag.mData = null
            frag.show(childFragmentManager, "")
        }





        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showTitle("Additional Cost")
        binding?.progressView?.visibility = View.VISIBLE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getAdditionalCosts(req)

    }


    override fun onError(msg: String?) {
        binding?.progressView?.visibility = View.GONE
        hideAlertDialog()
    }

    override fun onAdditionalCostResponse(body: AdditionalCostResponse) {
        hideAlertDialog()
        binding?.progressView?.visibility = View.GONE

        if (body.results?.status == true) {

            if (body.additionalCosts != null && body.additionalCosts.size > 0) {
                binding?.tvNoData?.visibility = View.GONE
                mList = body.additionalCosts
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvList?.adapter = mAdapter
            } else {
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
            mPresenter?.getAdditionalCosts(req)
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
    fun onUpdateEvent(req: CountryUpdateEvent) {
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getAdditionalCosts(req)
    }


    @Subscribe
    fun onCountryClick(request: AdditionalCostsItem) {
        if (request.actionName.equals("Enable", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Enable"
            req.recId = request.recId.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Processing your request"
                )
            }
            mPresenter?.enableDisableAdditionalCost(req)

        } else if (request.actionName.equals("Disable", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Disable"
            req.recId = request.recId.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Processing your request"
                )
            }
            mPresenter?.enableDisableAdditionalCost(req)

        }else if (request.actionName.equals("Delete", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Delete"
            req.recId = request.recId.toString()
            askConfirmation(req)

        } else if (request.actionName.equals("Edit", ignoreCase = true)) {

            var frag = CreateAdditionalCostFragment()
            frag.mData = request
            frag.show(childFragmentManager, "")
        }


    }


    private fun askConfirmation(req: EnableDisableRequest) {
        val fragment = AlertFragment.getInstance()
        fragment.setAlertTitle(getString(R.string.app_name))
        fragment.setMessageText("Are you sure want to delete this Additional Cost item ?")
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


                mPresenter?.deleteAdditionalCost(req)

            }

            override fun onNegativeClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
            }
        })
        fragment.show(childFragmentManager, "alertr")
    }



}
