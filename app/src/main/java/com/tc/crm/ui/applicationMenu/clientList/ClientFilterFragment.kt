package com.tc.crm.ui.applicationMenu.clientList

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tc.crm.R
import com.tc.crm.base.BottomSheetBaseFragment
import com.tc.crm.databinding.FragmentClientFilterBinding
import com.tc.crm.model.clientList.ClientStageFilterItem
import com.tc.crm.model.clientList.ClientStatusFilterItem
import com.tc.crm.model.clientList.ClientTypeFilterItem
import com.tc.crm.model.clientList.CountryFilterItem
import com.tc.crm.model.clientList.Filters
import com.tc.crm.model.clientList.SourceFilterItem
import com.tc.crm.model.clientList.TeamFilterItem
import com.tc.crm.model.clientList.UniversityFiltersItem
import com.tc.crm.model.filters.FilterSelection
import com.tc.crm.model.filters.SelectedFilter
import com.tc.crm.ui.applicationMenu.clientList.filter.ClientStageFilterAdapter
import com.tc.crm.ui.applicationMenu.clientList.filter.ClientStatusFilterAdapter
import com.tc.crm.ui.applicationMenu.clientList.filter.ClientTypeFilterAdapter
import com.tc.crm.ui.applicationMenu.clientList.filter.CountryFilterAdapter
import com.tc.crm.ui.applicationMenu.clientList.filter.SourceFilterAdapter
import com.tc.crm.ui.applicationMenu.clientList.filter.StaffFilterAdapter
import com.tc.crm.ui.applicationMenu.clientList.filter.UniversityFilterAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ClientFilterFragment : BottomSheetBaseFragment(), OnClickListener {

    var mData: Filters? = null
    private var bottomSheetInternal: FrameLayout? = null


    private var sourceFilter: ArrayList<SourceFilterItem> = ArrayList()
    private var countryFilter: ArrayList<CountryFilterItem> = ArrayList()
    private var clientTypeFilter: ArrayList<ClientTypeFilterItem> = ArrayList()
    private var teamFilter: ArrayList<TeamFilterItem> = ArrayList()
    private var clientStageFilter: ArrayList<ClientStageFilterItem> = ArrayList()
    private var universityFilters: ArrayList<UniversityFiltersItem> = ArrayList()
    private var clientStatusFilter: ArrayList<ClientStatusFilterItem> = ArrayList()


    companion object {
        fun getInstance(): ClientFilterFragment {
            return ClientFilterFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    lateinit var binder: FragmentClientFilterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_client_filter,
            container,
            false
        )

        onClickControllers()
        handleClick("University")
        prepareFilterList()







        return binder.root
    }

    private fun prepareFilterList() {
        binder.rcvUniversities.layoutManager = LinearLayoutManager(activity)
        binder.rcvSource.layoutManager = LinearLayoutManager(activity)
        binder.rcvCountry.layoutManager = LinearLayoutManager(activity)
        binder.rcvStaff.layoutManager = LinearLayoutManager(activity)
        binder.rcvClientType.layoutManager = LinearLayoutManager(activity)
        binder.rcvClientStage.layoutManager = LinearLayoutManager(activity)
        binder.rcvClientStatus.layoutManager = LinearLayoutManager(activity)

        if (mData != null) {
            universityFilters = (mData?.universityFilters as ArrayList<UniversityFiltersItem>?)!!
            sourceFilter = (mData?.sourceFilter as ArrayList<SourceFilterItem>?)!!
            countryFilter = (mData?.countryFilter as ArrayList<CountryFilterItem>?)!!
            teamFilter = (mData?.teamFilter as ArrayList<TeamFilterItem>?)!!
            clientTypeFilter = (mData?.clientTypeFilter as ArrayList<ClientTypeFilterItem>?)!!
            clientStageFilter = (mData?.clientStageFilter as ArrayList<ClientStageFilterItem>?)!!
            clientStatusFilter = (mData?.clientStatusFilter as ArrayList<ClientStatusFilterItem>?)!!

            if(clientTypeFilter[0].value != "Unassigned"){
                addEmptyValues()
            }


            val universityFilterAdapter = UniversityFilterAdapter()
            val sourceFilterAdapter = SourceFilterAdapter()
            val countryFilterAdapter = CountryFilterAdapter()
            val teamFilterAdapter = StaffFilterAdapter()
            val clientTypeFilterAdapter = ClientTypeFilterAdapter()
            val clientStageFilterAdapter = ClientStageFilterAdapter()
            val clientStatusFilterAdapter = ClientStatusFilterAdapter()



            activity?.let { universityFilterAdapter.setData(it, universityFilters) }
            activity?.let { sourceFilterAdapter.setData(it, sourceFilter) }
            activity?.let { countryFilterAdapter.setData(it, countryFilter) }
            activity?.let { teamFilterAdapter.setData(it, teamFilter) }
            activity?.let { clientTypeFilterAdapter.setData(it, clientTypeFilter) }
            activity?.let { clientStageFilterAdapter.setData(it, clientStageFilter) }
            activity?.let { clientStatusFilterAdapter.setData(it, clientStatusFilter) }


            binder.rcvSource.adapter = sourceFilterAdapter
            binder.rcvUniversities.adapter = universityFilterAdapter
            binder.rcvCountry.adapter = countryFilterAdapter
            binder.rcvStaff.adapter = teamFilterAdapter
            binder.rcvClientType.adapter = clientTypeFilterAdapter
            binder.rcvClientStage.adapter = clientStageFilterAdapter
            binder.rcvClientStatus.adapter = clientStatusFilterAdapter
            countCheck()
        }
    }

    private fun addEmptyValues() {
        val emUniversity = UniversityFiltersItem();
        val emSourceFilter = SourceFilterItem();
        val emClientTypeFilter = ClientTypeFilterItem();
        val emCountryFilter = CountryFilterItem();
        val emTeamFilter = TeamFilterItem();


        emUniversity.uName = "Unassigned"
        emUniversity.uId = 0

        emSourceFilter.sName = "Unassigned"
        emSourceFilter.sId = 0

        emCountryFilter.cId =0
        emCountryFilter.cName = "Unassigned"

        emTeamFilter.memId =0
        emTeamFilter.name = "Unassigned"

        emClientTypeFilter.id =""
        emClientTypeFilter.value = "Unassigned"


        universityFilters.add(0, emUniversity)
        sourceFilter.add(0, emSourceFilter)
        countryFilter.add(0, emCountryFilter)
        teamFilter.add(0, emTeamFilter)
        clientTypeFilter.add(0, emClientTypeFilter)
    }

    private fun onClickControllers() {
        binder.ivBack.setOnClickListener(this)
        binder.relUniversityFilter.setOnClickListener(this)
        binder.relSourceFilter.setOnClickListener(this)
        binder.relCountryFilter.setOnClickListener(this)
        binder.relStaffFilter.setOnClickListener(this)
        binder.relClientTypeFilter.setOnClickListener(this)
        binder.relClientStageFilter.setOnClickListener(this)
        binder.relClientStatusFilter.setOnClickListener(this)
        binder.cvClearFilter.setOnClickListener(this)
        binder.cvApplyFilter.setOnClickListener(this)
    }


    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val coordinatorLayout = bottomSheet?.parent
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.peekHeight = bottomSheet.height
            coordinatorLayout?.parent?.requestLayout()
            bottomSheetInternal =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        }


    }


    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.ivBack -> {
                dismiss()
            }

            R.id.relUniversityFilter -> {
                handleClick("University")
            }

            R.id.relSourceFilter -> {
                handleClick("Source")
            }

            R.id.relCountryFilter -> {
                handleClick("Country")
            }

            R.id.relStaffFilter -> {
                handleClick("Staff")
            }

            R.id.relClientTypeFilter -> {
                handleClick("ClientType")
            }

            R.id.relClientStageFilter -> {
                handleClick("ClientStage")
            }

            R.id.relClientStatusFilter -> {
                handleClick("ClientStatus")
            }

            R.id.cvClearFilter -> {
                clearFilter()
            }

            R.id.cvApplyFilter -> {
                applyFilter()
            }


        }
    }

    private fun applyFilter() {
        var selection = SelectedFilter()
        if (universityFilters != null && universityFilters.any { it.isSelected }) {
            selection.university_fId = universityFilters.filter { it.isSelected }[0].uId
        }
        if (sourceFilter != null && sourceFilter.any { it.isSelected }) {
            selection.source_fId = sourceFilter.filter { it.isSelected }[0].sId
        }
        if (countryFilter != null && countryFilter.any { it.isSelected }) {
            selection.country_fId = countryFilter.filter { it.isSelected }[0].cId
        }
        if (teamFilter != null && teamFilter.any { it.isSelected }) {
            selection.staff_fId = teamFilter.filter { it.isSelected }[0].memId
        }
        if (clientTypeFilter != null && clientTypeFilter.any { it.isSelected }) {
            selection.clientType_fId = clientTypeFilter.filter { it.isSelected }[0].id
        }
        if (clientStageFilter != null && clientStageFilter.any { it.isSelected }) {
            selection.clientStage_fId = clientStageFilter.filter { it.isSelected }[0].id
        }

        if (clientStatusFilter != null && clientStatusFilter.any { it.isSelected }) {
            selection.clientStatus_fId = clientStatusFilter.filter { it.isSelected }[0].id
        }

        EventBus.getDefault().post(selection)
        dismiss()


    }

    private fun clearFilter() {
        if (universityFilters != null && universityFilters.any { it.isSelected }) {
            universityFilters.filter { it.isSelected }[0].isSelected = false

        }
        if (sourceFilter != null && sourceFilter.any { it.isSelected }) {
            sourceFilter.filter { it.isSelected }[0].isSelected = false
        }
        if (countryFilter != null && countryFilter.any { it.isSelected }) {
            countryFilter.filter { it.isSelected }[0].isSelected = false
        }
        if (teamFilter != null && teamFilter.any { it.isSelected }) {
            teamFilter.filter { it.isSelected }[0].isSelected = false
        }
        if (clientTypeFilter != null && clientTypeFilter.any { it.isSelected }) {
            clientTypeFilter.filter { it.isSelected }[0].isSelected = false
        }
        if (clientStageFilter != null && clientStageFilter.any { it.isSelected }) {
            clientStageFilter.filter { it.isSelected }[0].isSelected = false
        }

        if (clientStatusFilter != null && clientStatusFilter.any { it.isSelected }) {
            clientStatusFilter.filter { it.isSelected }[0].isSelected = false
        }
        countCheck()
        var selection = SelectedFilter()
        EventBus.getDefault().post(selection)
        dismiss()
    }

    private fun handleClick(tab: String) {
        when (tab) {
            "University" -> {
                binder.relUniversityFilter.setBackgroundColor(Color.parseColor("#0c2556"))
                binder.tvUniversityFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binder.relSourceFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relCountryFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relStaffFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientTypeFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStageFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStatusFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.tvSourceFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvCountryFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvStaffFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientTypeFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStageFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStatusFilter.setTextColor(Color.parseColor("#414042"))

                binder.rcvUniversities.visibility = View.VISIBLE
                binder.rcvSource.visibility = View.GONE
                binder.rcvCountry.visibility = View.GONE
                binder.rcvStaff.visibility = View.GONE
                binder.rcvClientType.visibility = View.GONE
                binder.rcvClientStage.visibility = View.GONE
                binder.rcvClientStatus.visibility = View.GONE
            }

            "Source" -> {
                binder.relSourceFilter.setBackgroundColor(Color.parseColor("#0c2556"))
                binder.tvSourceFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binder.relUniversityFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relCountryFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relStaffFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientTypeFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStageFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStatusFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.tvUniversityFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvCountryFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvStaffFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientTypeFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStageFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStatusFilter.setTextColor(Color.parseColor("#414042"))

                binder.rcvSource.visibility = View.VISIBLE
                binder.rcvUniversities.visibility = View.GONE
                binder.rcvCountry.visibility = View.GONE
                binder.rcvStaff.visibility = View.GONE
                binder.rcvClientType.visibility = View.GONE
                binder.rcvClientStage.visibility = View.GONE
                binder.rcvClientStatus.visibility = View.GONE
            }


            "Country" -> {
                binder.relCountryFilter.setBackgroundColor(Color.parseColor("#0c2556"))
                binder.tvCountryFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binder.relUniversityFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relSourceFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relStaffFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientTypeFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStageFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStatusFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.tvUniversityFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvSourceFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvStaffFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientTypeFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStageFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStatusFilter.setTextColor(Color.parseColor("#414042"))

                binder.rcvCountry.visibility = View.VISIBLE
                binder.rcvUniversities.visibility = View.GONE
                binder.rcvSource.visibility = View.GONE
                binder.rcvStaff.visibility = View.GONE
                binder.rcvClientType.visibility = View.GONE
                binder.rcvClientStage.visibility = View.GONE
                binder.rcvClientStatus.visibility = View.GONE
            }

            "Staff" -> {
                binder.relStaffFilter.setBackgroundColor(Color.parseColor("#0c2556"))
                binder.tvStaffFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binder.relUniversityFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relSourceFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relCountryFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientTypeFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStageFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStatusFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.tvUniversityFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvSourceFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvCountryFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientTypeFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStageFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStatusFilter.setTextColor(Color.parseColor("#414042"))

                binder.rcvStaff.visibility = View.VISIBLE
                binder.rcvUniversities.visibility = View.GONE
                binder.rcvSource.visibility = View.GONE
                binder.rcvCountry.visibility = View.GONE
                binder.rcvClientType.visibility = View.GONE
                binder.rcvClientStage.visibility = View.GONE
                binder.rcvClientStatus.visibility = View.GONE
            }

            "ClientType" -> {
                binder.relClientTypeFilter.setBackgroundColor(Color.parseColor("#0c2556"))
                binder.tvClientTypeFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binder.relUniversityFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relSourceFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relCountryFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relStaffFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStageFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStatusFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.tvUniversityFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvSourceFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvCountryFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvStaffFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStageFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStatusFilter.setTextColor(Color.parseColor("#414042"))

                binder.rcvClientType.visibility = View.VISIBLE
                binder.rcvUniversities.visibility = View.GONE
                binder.rcvSource.visibility = View.GONE
                binder.rcvCountry.visibility = View.GONE
                binder.rcvStaff.visibility = View.GONE
                binder.rcvClientStage.visibility = View.GONE
                binder.rcvClientStatus.visibility = View.GONE
            }


            "ClientStage" -> {
                binder.relClientStageFilter.setBackgroundColor(Color.parseColor("#0c2556"))
                binder.tvClientStageFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binder.relUniversityFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relSourceFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relCountryFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relStaffFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientTypeFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStatusFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.tvUniversityFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvSourceFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvCountryFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvStaffFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientTypeFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStatusFilter.setTextColor(Color.parseColor("#414042"))

                binder.rcvClientStage.visibility = View.VISIBLE
                binder.rcvUniversities.visibility = View.GONE
                binder.rcvSource.visibility = View.GONE
                binder.rcvCountry.visibility = View.GONE
                binder.rcvStaff.visibility = View.GONE
                binder.rcvClientType.visibility = View.GONE
                binder.rcvClientStatus.visibility = View.GONE
            }

            "ClientStatus" -> {
                binder.relClientStatusFilter.setBackgroundColor(Color.parseColor("#0c2556"))
                binder.tvClientStatusFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binder.relUniversityFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relSourceFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relCountryFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relStaffFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientTypeFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.relClientStageFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binder.tvUniversityFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvSourceFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvCountryFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvStaffFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientTypeFilter.setTextColor(Color.parseColor("#414042"))
                binder.tvClientStageFilter.setTextColor(Color.parseColor("#414042"))

                binder.rcvClientStatus.visibility = View.VISIBLE
                binder.rcvUniversities.visibility = View.GONE
                binder.rcvSource.visibility = View.GONE
                binder.rcvCountry.visibility = View.GONE
                binder.rcvStaff.visibility = View.GONE
                binder.rcvClientType.visibility = View.GONE
                binder.rcvClientStage.visibility = View.GONE
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
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }


    @Subscribe
    fun onFilterSelection(request: FilterSelection) {
        countCheck()
    }

    private fun countCheck() {
        var filterCount = 0;

        if (universityFilters != null) {
            filterCount += universityFilters.filter { it.isSelected }.size
        }

        if (sourceFilter != null) {
            filterCount += sourceFilter.filter { it.isSelected }.size
        }

        if (countryFilter != null) {
            filterCount += countryFilter.filter { it.isSelected }.size
        }
        if (teamFilter != null) {
            filterCount += teamFilter.filter { it.isSelected }.size
        }


        if (clientTypeFilter != null) {
            filterCount += clientTypeFilter.filter { it.isSelected }.size
        }


        if (clientStageFilter != null) {
            filterCount += clientStageFilter.filter { it.isSelected }.size
        }


        if (clientStatusFilter != null) {
            filterCount += clientStatusFilter.filter { it.isSelected }.size
        }



        if (filterCount > 0) {
            binder.cvFilterCount.visibility = View.VISIBLE
            binder.tvFilterCount.text = filterCount.toString()
        } else {
            binder.cvFilterCount.visibility = View.GONE
            binder.tvFilterCount.text = "0"
        }
    }


}
