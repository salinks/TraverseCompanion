package com.tc.crm.ui.contents.airports


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.listners.AlertListener
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.CommonResult
import com.tc.crm.data.model.EnableDisableRequest
import com.tc.crm.databinding.FragmentAirportsBinding
import com.tc.crm.model.airports.AirportItem
import com.tc.crm.model.airports.AirportListResponse
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.ui.adapter.AirportListAdapter
import com.tc.crm.ui.adapter.CountryMasterAdapter
import com.tc.crm.ui.customViews.alert.AlertFragment
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.Locale


class AirportFragment : BaseFragment(),
    AirportView {

    private var binding: FragmentAirportsBinding? = null
    var mPresenter: AirportPresenter? = null
    var mAdapter = AirportListAdapter()
    var mList: ArrayList<AirportItem> = ArrayList()
    internal var bsCountryInfo: BottomSheetBehavior<RelativeLayout>? = null

    companion object {
        fun getInstance(): AirportFragment {
            return AirportFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = AirportPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_airports, container, false)

        binding?.rcvList?.layoutManager = LinearLayoutManager(activity)


        binding?.etSearch?.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                val tempList = ArrayList<AirportItem>()

                for (i in 0 until mList.size) {
                    if (mList[i].airportName.lowercase(Locale.ROOT)
                            .contains(text.toString().lowercase(Locale.ROOT))
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

        binding?.relNewCountry?.setOnClickListener {
            var frag = CreateAirportFragment()
            frag.mData = null
            frag.show(childFragmentManager, "")
        }

        bsCountryInfo =
            BottomSheetBehavior.from(binding?.countryInfo?.rlBottomSheet as RelativeLayout)


        bsCountryInfo?.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED ->
                        binding?.bg?.visibility = View.VISIBLE

                    BottomSheetBehavior.STATE_COLLAPSED ->
                        binding?.bg?.visibility = View.GONE

                    BottomSheetBehavior.STATE_HALF_EXPANDED ->
                        binding?.bg?.visibility = View.VISIBLE

                    BottomSheetBehavior.STATE_HIDDEN ->
                        binding?.bg?.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })




        binding?.countryInfo?.tvClose?.setOnClickListener {
            toggleCountryInfo()
        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun toggleCountryInfo() {
        if (bsCountryInfo?.state != BottomSheetBehavior.STATE_EXPANDED) {

            bsCountryInfo?.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            bsCountryInfo?.setState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showTitle("Airports")
        binding?.progressView?.visibility = View.VISIBLE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getCountryMaster(req)

    }


    override fun onError(msg: String?) {
        binding?.progressView?.visibility = View.GONE
        hideAlertDialog()
    }

    override fun onAirportListResponse(body: AirportListResponse) {
        binding?.progressView?.visibility = View.GONE


        if (body.results?.status == true) {

            if (body.airport != null && body.airport.size > 0) {
                binding?.tvNoData?.visibility = View.GONE
                mList = body.airport
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
            mPresenter?.getCountryMaster(req)
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
    fun onCountryUpdateEvent(req: CountryUpdateEvent) {
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getCountryMaster(req)
    }


    @Subscribe
    fun onCountryClick(request: AirportItem) {
        if (request.actionName.equals("Enable", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Enable"
            req.recId = request.aId.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Processing your request"
                )
            }
            mPresenter?.enableDisableAirport(req)

        } else if (request.actionName.equals("Disable", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Disable"
            req.recId = request.aId.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Processing your request"
                )
            }
            mPresenter?.enableDisableAirport(req)

        } else if (request.actionName.equals("Delete", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Disable"
            req.recId = request.aId.toString()

           askConfirmation(req)

        } else if (request.actionName.equals("Edit", ignoreCase = true)) {

            var frag = CreateAirportFragment()
            frag.mData = request
            frag.show(childFragmentManager, "")
        }


    }

    private fun askConfirmation(req: EnableDisableRequest) {
        val fragment = AlertFragment.getInstance()
        fragment.setAlertTitle(getString(R.string.app_name))
        fragment.setMessageText("Are you sure want to delete this airport ?")
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


                mPresenter?.deleteAirport(req)

            }

            override fun onNegativeClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
            }
        })
        fragment.show(childFragmentManager, "alertr")
    }


}
