package com.tc.crm.ui.applicationMenu.clientList



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
import com.bumptech.glide.Glide
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.listners.AlertListener
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.EnableDisableRequest
import com.tc.crm.databinding.FragmentClientListBinding
import com.tc.crm.model.clientList.ClientListResponse
import com.tc.crm.model.clientList.ClientsItem
import com.tc.crm.model.clientList.Filters
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.model.filters.SelectedFilter
import com.tc.crm.ui.adapter.ClientListAdapter
import com.tc.crm.ui.customViews.alert.AlertFragment
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.Locale


class ClientListFragment : BaseFragment(),
    ClientListView {

    private var binding: FragmentClientListBinding? = null
    var mPresenter: ClientListPresenter? = null
    var mAdapter = ClientListAdapter()
    var mList: ArrayList<ClientsItem> = ArrayList()
    var filterOptions : Filters? = null

    companion object {
        fun getInstance(): ClientListFragment {
            return ClientListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter =
            ClientListPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_client_list, container, false)

        binding?.rcvClients?.layoutManager = LinearLayoutManager(activity)


        binding?.etSearch?.doOnTextChanged { text, start, before, count ->
            if (!TextUtils.isEmpty(text)) {
                val tempList = java.util.ArrayList<ClientsItem>()

                for (i in 0 until mList.size) {
                    if (mList[i].cName.lowercase(Locale.ROOT)
                            .contains(text.toString().lowercase(Locale.ROOT))
                    ) {
                        tempList.add(mList[i])
                    }
                }
                activity?.let { mAdapter.setData(it, tempList) }
                binding?.rcvClients?.adapter = mAdapter
            } else {
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvClients?.adapter = mAdapter
            }

        }

        binding?.cvFilter?.setOnClickListener {
            if(filterOptions == null){
                showMessage(2,"Filter option currently not available")
            }
            else{
                var frag = ClientFilterFragment()
                frag.mData = filterOptions
                frag.show(childFragmentManager, "")
            }

        }

//        binding?.relNewCountry?.setOnClickListener {
//            var frag = CreateCountryMasterFragment()
//            frag.mData = null
//            frag.show(childFragmentManager, "")
//        }


        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showTitle("Clients")
        binding?.progressView?.let { Glide.with(this).asGif().load(R.raw.ct_loading).into(it) };
        binding?.progressView?.visibility = View.VISIBLE
        binding?.lnrHeader?.visibility = View.GONE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getClientList(req)

    }


    override fun onError(msg: String?) {
        binding?.progressView?.visibility = View.GONE
        hideAlertDialog()
    }

    override fun onClientListResponse(body: ClientListResponse) {
        binding?.progressView?.visibility = View.GONE


        if (body.results?.status == true) {

            binding?.lnrHeader?.visibility = View.VISIBLE
            if (body.clients != null && body.clients.size > 0) {
                filterOptions = body.filters
                mList = body.clients as ArrayList<ClientsItem>
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvClients?.adapter = mAdapter
            } else {
                filterOptions = null
                mList = ArrayList()
                activity?.let { mAdapter.setData(it, mList) }
                binding?.rcvClients?.adapter = mAdapter
            }
        } else {

            if (body.results?.message.equals("AUTHORIZATION_FAILDED") ||
                body.results?.message.equals("AUTHORIZATION_FAILDED")
            ) {
                (activity as HomeActivity).logout()
            }
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
    fun onSelectedFilter(request: SelectedFilter) {
        if(request != null){
            var temList  = mList


            if(request.clientStatus_fId >= 0){
                temList= temList.filter { it.cStatus == request.clientStatus_fId } as ArrayList<ClientsItem>
            }

            if(request.country_fId >= 0){
                temList= temList.filter { it.countryId == request.country_fId } as ArrayList<ClientsItem>
            }

            if(request.staff_fId >= 0){
                temList= temList.filter { it.staffId == request.staff_fId } as ArrayList<ClientsItem>
            }
            if(request.university_fId >= 0){
                temList= temList.filter { it.universityId == request.university_fId } as ArrayList<ClientsItem>
            }

            if(request.source_fId >= 0){
                temList= temList.filter { it.sourceId == request.source_fId } as ArrayList<ClientsItem>
            }

            if(request.clientType_fId != null){
                temList= temList.filter { it.clientType == request.clientType_fId } as ArrayList<ClientsItem>
            }

            if(request.clientStage_fId != null){
                temList= temList.filter { it.stageName == request.clientStage_fId } as ArrayList<ClientsItem>
            }


            activity?.let { mAdapter.setData(it, temList) }
            binding?.rcvClients?.adapter = mAdapter

            if(temList.size==0){
                askConfirmation()
            }
        }


    }
    private fun askConfirmation() {
        val fragment = AlertFragment.getInstance()
        fragment.setAlertTitle("No Records Found!!!")
        fragment.setMessageText("Please change the filter options and try again")
        fragment.setPositiveButtonText("Clear Filter")
        fragment.isCancelable =false
        fragment.setOnClickLlistner(object : AlertListener {
            override fun onPositiveClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
                clearFilter()
                onSelectedFilter(SelectedFilter())

            }

            override fun onNegativeClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
            }
        })
        fragment.show(childFragmentManager, "alertr")
    }

    private fun clearFilter() {

        if ( filterOptions?.universityFilters != null &&  filterOptions?.universityFilters!!.any { it.isSelected }) {
            filterOptions?.universityFilters!!.filter { it.isSelected }[0].isSelected = false

        }
        if ( filterOptions?.sourceFilter != null &&  filterOptions?.sourceFilter!!.any { it.isSelected }) {
            filterOptions?.sourceFilter!!.filter { it.isSelected }[0].isSelected = false
        }
        if ( filterOptions?.countryFilter != null &&  filterOptions?.countryFilter!!.any { it.isSelected }) {
            filterOptions?.countryFilter!!.filter { it.isSelected }[0].isSelected = false
        }
        if ( filterOptions?.teamFilter != null &&  filterOptions?.teamFilter!!.any { it.isSelected }) {
            filterOptions?.teamFilter!!.filter { it.isSelected }[0].isSelected = false
        }
        if ( filterOptions?.clientTypeFilter != null &&  filterOptions?.clientTypeFilter!!.any { it.isSelected }) {
            filterOptions?.clientTypeFilter!!.filter { it.isSelected }[0].isSelected = false
        }
        if ( filterOptions?.clientStageFilter != null &&  filterOptions?.clientStageFilter!!.any { it.isSelected }) {
            filterOptions?.clientStageFilter!!.filter { it.isSelected }[0].isSelected = false
        }

        if ( filterOptions?.clientStatusFilter != null &&  filterOptions?.clientStatusFilter!!.any { it.isSelected }) {
            filterOptions?.clientStatusFilter!!.filter { it.isSelected }[0].isSelected = false
        }


    }


}
