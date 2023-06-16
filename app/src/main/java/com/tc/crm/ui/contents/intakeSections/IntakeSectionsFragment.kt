package com.tc.crm.ui.contents.intakeSections


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.CommonResult
import com.tc.crm.data.model.EnableDisableRequest
import com.tc.crm.databinding.FragmentIntakeSectionsBinding
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.model.intakeSections.IntakeSectionResponse
import com.tc.crm.model.intakeSections.IntakeSectionsItem
import com.tc.crm.ui.adapter.IntakeSectionAdapter
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.Locale


class IntakeSectionsFragment : BaseFragment(),
    IntakeSectionsView {

    private var binding: FragmentIntakeSectionsBinding? = null
    var mPresenter: IntakeSectionPresenter? = null
    var mAdapter = IntakeSectionAdapter()
    var mList: ArrayList<IntakeSectionsItem> = ArrayList()

    companion object {
        fun getInstance(): IntakeSectionsFragment {
            return IntakeSectionsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter =
            IntakeSectionPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_intake_sections, container, false)

        binding?.rcvList?.layoutManager = LinearLayoutManager(activity)


        binding?.etSearch?.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                val tempList = java.util.ArrayList<IntakeSectionsItem>()

                for (i in 0 until mList.size) {
                    if (mList[i].sectionName.lowercase(Locale.ROOT).contains(text.toString().lowercase(Locale.ROOT) )
                                || mList[i].year.lowercase(Locale.ROOT).contains(text.toString().lowercase(Locale.ROOT) )
                                || mList[i].countryName.lowercase(Locale.ROOT).contains(text.toString().lowercase(Locale.ROOT) )
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

        binding?.relNewSection?.setOnClickListener {
            var frag = CreateIntakeSectionFragment()
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
        showTitle("Intake Sections")
        binding?.progressView?.visibility = View.VISIBLE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getUserGroups(req)

    }


    override fun onError(msg: String?) {
        binding?.progressView?.visibility = View.GONE
        hideAlertDialog()
    }

    override fun onIntakeSectionResponse(body: IntakeSectionResponse) {
        hideAlertDialog()
        binding?.progressView?.visibility = View.GONE

        if (body.results?.status == true) {

            if (body.intakeSections != null && body.intakeSections.size > 0) {
                binding?.tvNoData?.visibility = View.GONE
                mList = body.intakeSections
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
            mPresenter?.getUserGroups(req)
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
        mPresenter?.getUserGroups(req)
    }


    @Subscribe
    fun onCountryClick(request: IntakeSectionsItem) {
        if (request.actionName.equals("Enable", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Enable"
            req.recId = request.yId.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Processing your request"
                )
            }
            mPresenter?.enableDisableIntakeSection(req)

        } else if (request.actionName.equals("Disable", ignoreCase = true)) {
            var req = EnableDisableRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.actionName = "Disable"
            req.recId = request.yId.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Processing your request"
                )
            }
            mPresenter?.enableDisableIntakeSection(req)

        } else if (request.actionName.equals("Edit", ignoreCase = true)) {

            var frag = CreateIntakeSectionFragment()
            frag.mData = request
            frag.show(childFragmentManager, "")
        }


    }




}
