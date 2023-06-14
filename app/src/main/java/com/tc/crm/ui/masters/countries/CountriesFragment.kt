package com.tc.crm.ui.masters.countries


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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.CommonResult
import com.tc.crm.data.model.EnableDisableRequest
import com.tc.crm.databinding.FragmentCountriesBinding
import com.tc.crm.model.countries.CountriesResponse
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.ui.adapter.CountryMasterAdapter
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.Locale


class CountriesFragment : BaseFragment(),
    CountriesView {

    private var binding: FragmentCountriesBinding? = null
    var mPresenter: CountriesPresenter? = null
    var mAdapter = CountryMasterAdapter()
    var mList: ArrayList<CountryMasterItem> = ArrayList()
    internal var bsCountryInfo: BottomSheetBehavior<RelativeLayout>? = null

    companion object {
        fun getInstance(): CountriesFragment {
            return CountriesFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = CountriesPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_countries, container, false)

        binding?.rcvCountries?.layoutManager = LinearLayoutManager(activity)


        binding?.etSearch?.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                val tempList = java.util.ArrayList<CountryMasterItem>()

                for (i in 0 until mList.size) {
                    if (mList[i].cName.lowercase(Locale.ROOT)
                            .contains(text.toString().lowercase(Locale.ROOT))
                    ) {
                        tempList.add(mList[i])
                    }
                }
                activity?.let { mAdapter.setData(it, tempList) }
                binding?.rcvCountries?.adapter = mAdapter
            } else {
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvCountries?.adapter = mAdapter
            }

        }

            binding?.relNewCountry?.setOnClickListener {
                var frag = CreateCountryMasterFragment()
                frag.mData = null
                frag.show(childFragmentManager,"")
            }

        bsCountryInfo = BottomSheetBehavior.from(binding?.countryInfo?.rlBottomSheet as RelativeLayout)


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
        showTitle("Countries")
        binding?.progressView?.visibility = View.VISIBLE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getCountryMaster(req)

    }


    override fun onError(msg: String?) {
        binding?.progressView?.visibility = View.GONE
        hideAlertDialog()
    }

    override fun onCountriesResponse(body: CountriesResponse) {
        binding?.progressView?.visibility = View.GONE



        if (body.results?.status == true && body.countryMaster != null && body.countryMaster.size > 0) {
            mList = body.countryMaster
            activity?.let { mAdapter.setData(it, mList) }
            binding?.rcvCountries?.adapter = mAdapter
        } else {
            mList = ArrayList()
            activity?.let { mAdapter.setData(it, mList) }
            binding?.rcvCountries?.adapter = mAdapter
        }

    }

    override fun onCommonResult(body: CommonResult) {
        hideAlertDialog()
        if(body.results.status){
            showMessage(3,body.results.message)
            var req = CommonRequest()
            req.userId = PreferenceManager.getInstance().userId
            mPresenter?.getCountryMaster(req)
        }
        else{
            showMessage(1,body.results.message)
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
    fun onCountryUpdateEvent (req : CountryUpdateEvent){
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getCountryMaster(req)
    }


    @Subscribe
    fun onCountryClick(request: CountryMasterItem) {
        if (request.actionName.equals("Enable", ignoreCase = true)) {
            var req= EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Enable"
            req.recId = request.cId
            activity?.let {
                TCApp.showAlertDialog(0,
                    it, "Please Wait", "Processing your request")
            }
            mPresenter?.EnableDisableCountry(req)

        } else if (request.actionName.equals("Disable", ignoreCase = true)) {
            var req= EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Disable"
            req.recId = request.cId
            activity?.let {
                TCApp.showAlertDialog(0,
                    it, "Please Wait", "Processing your request")
            }
            mPresenter?.EnableDisableCountry(req)

        } else if (request.actionName.equals("View", ignoreCase = true)) {
            countryInfoInViews(request)
        } else if (request.actionName.equals("Edit", ignoreCase = true)) {

            var frag = CreateCountryMasterFragment()
            frag.mData = request
            frag.show(childFragmentManager,"")
        }


    }

    @SuppressLint("SetTextI18n")
    private fun countryInfoInViews(request: CountryMasterItem) {

        binding?.countryInfo?.tvCountryName?.text = " : "+ request.cName
        binding?.countryInfo?.tvCountryCode?.text = " : "+ request.cCode
        binding?.countryInfo?.tvPhoneCode?.text = " : +"+ request.phoneCode
        binding?.countryInfo?.tvCurrencyName?.text = " : "+ request.currName
        binding?.countryInfo?.tvCurrencyCode?.text = " : "+ request.currCode
        binding?.countryInfo?.tvCurrencyShortCode?.text = " : "+  request.currShortName
        toggleCountryInfo()

    }


}
