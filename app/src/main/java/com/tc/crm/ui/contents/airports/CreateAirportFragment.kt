package com.tc.crm.ui.contents.airports

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BottomSheetBaseFragment
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentCreateAirportBinding
import com.tc.crm.model.airports.AirportItem
import com.tc.crm.model.airports.AirportRequest
import com.tc.crm.model.airports.Terminals
import com.tc.crm.model.countries.CountriesResponse
import com.tc.crm.model.countries.CountryMasterItem
import com.tc.crm.model.countries.CountryUpdateEvent
import com.tc.crm.model.userGroups.UserGroupsRequest
import com.tc.crm.ui.adapter.TerminalAdapter
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus


class CreateAirportFragment : BottomSheetBaseFragment(),
    CAView {

    lateinit var countryId: String
    lateinit var countryName: String
    var mData: AirportItem? = null
    private var bottomSheetInternal: FrameLayout? = null
    var mPresenter: CAPresenter? = null
    var countryList: ArrayList<String>? = null
    var terminalList = ArrayList<Terminals>()
    var countryMaster: ArrayList<CountryMasterItem>? = null
    var mAdapter = TerminalAdapter()

    companion object {
        fun getInstance(): CreateAirportFragment {
            return CreateAirportFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = CAPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            R.style.CustomBottomSheetDialogTheme
        )

    }

    lateinit var binder: FragmentCreateAirportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            com.tc.crm.R.layout.fragment_create_airport,
            container,
            false
        )
        binder.rcvTerminals.layoutManager = LinearLayoutManager(activity)

        binder.ivBack.setOnClickListener()
        {
            dismiss()
        }

        if (mData == null) {
            countryId= ""
            countryName= ""
            binder.btnCreate.text = "Create Airport"
            binder.appTitle.text = "Create Airport"
            var ter = Terminals()
            ter.terminalName = ""
            terminalList.add(ter)
            activity?.let { mAdapter.setData(it, terminalList) }
            binder.rcvTerminals.adapter = mAdapter
        } else {
            binder.btnCreate.text = "Update Airport"
            binder.appTitle.text = "Update Airport"
            countryName = mData?.countryName.toString()
            countryId = mData?.countryId.toString()
            showValuesInViews()
            binder.etAirportName.setText(mData?.airportName)
            binder.etAirportCode.setText(mData?.airportCode)
            val sList: List<String> = mData?.terminals?.split(",")?.toList()!!
             terminalList = ArrayList<Terminals>()
            for (i in 0 until sList.size) {
                var ter = Terminals()
                ter.terminalName = sList.get(i)
                terminalList.add(ter)
            }

            activity?.let { mAdapter.setData(it, terminalList) }
            binder.rcvTerminals.adapter = mAdapter
        }



        binder.btnCreate.setOnClickListener {
            if (validateForm()) {
                prepareData()
            }
        }

        binder.cvCountry.setOnClickListener(View.OnClickListener {
            // Initialize dialog
            val mDialog = Dialog(requireContext())
            mDialog.setContentView(com.tc.crm.R.layout.dialog_searchable_spinner)
            mDialog.window!!.setLayout(650, 800)

            //mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            mDialog.show()
            mDialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );
            val editText: EditText = mDialog.findViewById(com.tc.crm.R.id.edit_text)
            val listView: ListView = mDialog.findViewById(com.tc.crm.R.id.list_view)
            val closeBtn: ImageView = mDialog.findViewById(com.tc.crm.R.id.ivClose)
            var list = ArrayList<String>()
            list = countryList!!;
            val adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_list_item, list)


            // set adapter
            listView.adapter = adapter
            closeBtn.setOnClickListener {
                mDialog!!.dismiss()
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable) {}
            })
            listView.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                    countryNameValue(position)
                    mDialog!!.dismiss()

                }
        })



        return binder.root
    }

    private fun showValuesInViews() {

        binder.tvCountryName.text = countryName


    }

    private fun countryNameValue(position: Int) {
        countryId = countryMaster?.get(position)?.cId!!
        countryName = countryMaster?.get(position)?.cName!!
        showValuesInViews()
    }

    private fun prepareData() {
        var req = AirportRequest()
        req.userId = PreferenceManager.getInstance().userId
        req.airportName = binder.etAirportName.text.toString()
        req.airportCode = binder.etAirportCode.text.toString()
        req.countryId = countryId
        req.terminals =  terminalList.filter{ it -> it.terminalName.isNotEmpty() }.joinToString { it.terminalName }
        if (mData == null) {
            req.aId = null
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Creating new airport"
                )
            }
              mPresenter?.createAirport(req)
        } else {
            req.aId = mData?.aId?.toString()
            activity?.let {
                TCApp.showAlertDialog(
                    0,
                    it, "Please Wait", "Updating airport data"
                )
            }
            mPresenter?.updateAirport(req)
        }
    }

    private fun validateForm(): Boolean {

        if (TextUtils.isEmpty(binder.etAirportName.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etAirportName)
            binder.etAirportName.error = "Enter airport name"
            binder.etAirportName.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binder.etAirportCode.text.toString())) {
            AnimationUtil.TranslateView(context, binder.etAirportCode)
            binder.etAirportCode.error = "Enter airport code"
            binder.etAirportCode.requestFocus()
            return false
        }
        if (countryId == null || TextUtils.isEmpty(countryId)) {
            AnimationUtil.TranslateView(context, binder.cvCountry)
            Toast.makeText(activity, "Select country", Toast.LENGTH_LONG).show()
            return false
        }
        for (i in 0 until terminalList.size) {
           if(TextUtils.isEmpty(terminalList.get(i).terminalName)){
              terminalList.get(i).isError = true
               mAdapter.notifyDataSetChanged()
               return false
           }
        }



        return true
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

    override fun onResume() {
        super.onResume()
        binder.relLoading.visibility = View.VISIBLE
        binder.lnrRoot.visibility = View.GONE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getActiveCountries(req);
    }


    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    override fun onError(msg: String?) {
        hideAlertDialog()
        showMessage(1, msg)
        dismiss()
    }

    override fun onCommonResult(body: CommonResult) {
        hideAlertDialog()
        if (body.results.status) {
            showMessage(3, body.results.message)
        } else {
            showMessage(1, body.results.message)
        }

        EventBus.getDefault().post(CountryUpdateEvent())
        dismiss()
    }

    override fun onCountriesResponse(body: CountriesResponse) {

        if (body.results.status) {
            if (body.countryMaster != null && body.countryMaster.size > 0) {
                binder.relLoading.visibility = View.GONE
                binder.lnrRoot.visibility = View.VISIBLE
                countryMaster = body.countryMaster
                countryList = ArrayList<String>()

                for (i in 0 until body.countryMaster.size) {
                    countryList?.add(body.countryMaster[i].cName)
                }
            } else {
                binder.progressView.visibility = View.GONE
                binder.tvNoData.visibility = View.VISIBLE

                showMessage(3, "Country data not available")
                dismiss()
            }
        } else {
            binder.relLoading.visibility = View.VISIBLE
            binder.tvNoData.visibility = View.VISIBLE
            binder.lnrRoot.visibility = View.GONE
            binder.progressView.visibility = View.GONE
            showMessage(3, body.results.message)
            dismiss()
        }

    }


}
