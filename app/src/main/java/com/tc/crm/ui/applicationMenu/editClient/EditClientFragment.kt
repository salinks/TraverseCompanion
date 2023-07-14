package com.tc.crm.ui.applicationMenu.editClient


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ozcanalasalvar.library.utils.DateUtils
import com.ozcanalasalvar.library.view.datePicker.DatePicker
import com.ozcanalasalvar.library.view.popup.DatePickerPopup
import com.tc.crm.R
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.TCApp.Companion.showAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentEditClientBinding
import com.tc.crm.juzdevz.spinner.SpinnerTextFormatter
import com.tc.crm.model.clientDetails.CountryItem
import com.tc.crm.model.clientDetails.CountryMasterItem
import com.tc.crm.model.clientDetails.IntakeSectionsItem
import com.tc.crm.model.clientDetails.StaticDataModel
import com.tc.crm.model.clientDetails.TravelConsultantsItem
import com.tc.crm.model.clientDetails.req.BasicInfoUpdateRequest
import com.tc.crm.model.clientDetails.req.ContactUpdateRequest
import com.tc.crm.model.clientDetails.req.CountryUploadRequest
import com.tc.crm.model.clientDetails.req.IntakeSectionUpdateRequest
import com.tc.crm.model.clientDetails.req.UpdateClientTypeRequest
import com.tc.crm.model.clientDetails.req.UpdateStaffRequest
import com.tc.crm.ui.applicationMenu.editClient.fragments.DDIntakeSectionsFragment
import com.tc.crm.utils.GlobalVariables.CLIENT_DETAILS
import com.tc.crm.utils.GlobalVariables.ViewName
import com.tc.crm.utils.PreferenceManager
import com.tc.crm.utils.TCUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class EditClientFragment : BaseFragment(),
    EditClientView, OnClickListener {
    lateinit var binding: FragmentEditClientBinding

    var mPresenter: EditClientPresenter? = null
    private var datePickerPopup: DatePickerPopup? = null
    var iSectionId = "0"
    var dob = "0000-00-00 00:00:00"

    companion object {
        fun getInstance(): EditClientFragment {
            return EditClientFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter =
            EditClientPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_client, container, false)

        onClickControllers()



        return binding.root
    }


    private fun onClickControllers() {
        binding.btnUpdateCountry.setOnClickListener(this)
        binding.btnUpdateCountryCancel.setOnClickListener(this)
        binding.btnUpdateSection.setOnClickListener(this)
        binding.btnUpdateSectionCancel.setOnClickListener(this)
        binding.cvSelectSection.setOnClickListener(this)
        binding.btnUpdateClientTypeCancel.setOnClickListener(this)
        binding.btnUpdateClientType.setOnClickListener(this)
        binding.btnUpdateStaffCancel.setOnClickListener(this)
        binding.btnUpdateStaff.setOnClickListener(this)
        binding.cvDOB.setOnClickListener(this)
        binding.btnUpdateBasicInfoCancel.setOnClickListener(this)
        binding.btnUpdateBasicInfo.setOnClickListener(this)
        binding.btnUpdateContactInfoCancel.setOnClickListener(this)
        binding.btnUpdateContactInfo.setOnClickListener(this)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showViewsByCondition()


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

    @SuppressLint("SetTextI18n")
    @Subscribe
    fun onIntakeSectionsItemSelected(mData: IntakeSectionsItem) {
        iSectionId = mData.yId.toString()
        binding.tvSectionName.text = mData.sectionName + " , " + mData.cCode
    }


    private fun showViewsByCondition() {


        when (ViewName) {


            "COUNTRY_SECTION" -> {
                setOldCountryData()
                showTitle("Update Country")
                binding.lnrCountryUpdate.visibility = View.VISIBLE
            }

            "INTAKE_SECTION" -> {
                setOldSectionData()
                showTitle("Update Intake Section Info")
                binding.lnrIntakeSectionUpdate.visibility = View.VISIBLE
            }

            "CLIENT_TYPE" -> {
                setOldClientTypeData()
                showTitle("Update Client Type")
                binding.lnrClientTypeUpdate.visibility = View.VISIBLE
            }

            "STAFF" -> {
                setOldStaffData()
                showTitle("Update Lead Head")
                binding.lnrStaffUpdate.visibility = View.VISIBLE
            }

            "BASIC_INFO" -> {
                setOldBasicInfoData()
                showTitle("Update Basic Info")
                binding.lnrBasicInfoUpdate.visibility = View.VISIBLE
            }

            "CONTACT_INFO" -> {
                setOldContactInfoData()
                showTitle("Edit Contact Info")
                binding.lnrContactInfoUpdate.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setOldSectionData() {
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.iSectionId)
            && CLIENT_DETAILS.clientInfo.iSectionId != "0"
        ) {
            iSectionId = CLIENT_DETAILS.clientInfo.iSectionId
            binding.tvSectionName.text =
                CLIENT_DETAILS.clientInfo.sectionName + " , " + CLIENT_DETAILS.clientInfo.cCode
        }
    }

    private fun setOldCountryData() {


        val textFormatter =
            SpinnerTextFormatter<CountryMasterItem> { person -> SpannableString(person.cName) }

        binding.sCountry.setSpinnerTextFormatter(textFormatter)
        binding.sCountry.setSelectedTextFormatter(textFormatter)

        binding.sCountry.attachDataSource(CLIENT_DETAILS.countryMaster)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.countryId) && CLIENT_DETAILS.clientInfo.countryId != "0") {
            var ind =
                CLIENT_DETAILS.countryMaster.indexOf(CLIENT_DETAILS.countryMaster.filter { it.cId == CLIENT_DETAILS.clientInfo.countryId.toInt() }[0])
            binding.sCountry.selectedIndex = ind
        }
    }

    private fun setOldClientTypeData() {


        val textFormatter =
            SpinnerTextFormatter<StaticDataModel> { mModel -> SpannableString(mModel.value) }

        binding.spnClientType.setSpinnerTextFormatter(textFormatter)
        binding.spnClientType.setSelectedTextFormatter(textFormatter)

        binding.spnClientType.attachDataSource(CLIENT_DETAILS.clientTypes)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.clientType) && CLIENT_DETAILS.clientInfo.clientType != "") {
            var ind =
                CLIENT_DETAILS.clientTypes.indexOf(CLIENT_DETAILS.clientTypes.filter {
                    it.id.equals(
                        CLIENT_DETAILS.clientInfo.clientType
                    )
                }[0])
            binding.spnClientType.selectedIndex = ind
        }
    }

    private fun setOldStaffData() {


        val textFormatter =
            SpinnerTextFormatter<TravelConsultantsItem> { mModel -> SpannableString(mModel.name) }

        binding.spnStaffType.setSpinnerTextFormatter(textFormatter)
        binding.spnStaffType.setSelectedTextFormatter(textFormatter)

        binding.spnStaffType.attachDataSource(CLIENT_DETAILS.travelConsultants)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.staffId) && CLIENT_DETAILS.clientInfo.staffId != "0") {
            var ind =
                CLIENT_DETAILS.travelConsultants.indexOf(CLIENT_DETAILS.travelConsultants.filter {
                    it.memId.equals(
                        CLIENT_DETAILS.clientInfo.staffId
                    )
                }[0])
            binding.spnStaffType.selectedIndex = ind
        }
    }

    private fun setOldBasicInfoData() {

        val textFormatter =
            SpinnerTextFormatter<StaticDataModel> { mModel -> SpannableString(mModel.value) }


        binding.spnGender.setSpinnerTextFormatter(textFormatter)
        binding.spnGender.setSelectedTextFormatter(textFormatter)


        binding.spnGender.attachDataSource(CLIENT_DETAILS.genderList)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.gender) && CLIENT_DETAILS.clientInfo.gender != "") {
            var ind =
                CLIENT_DETAILS.genderList.indexOf(CLIENT_DETAILS.genderList.filter {
                    it.id.equals(
                        CLIENT_DETAILS.clientInfo.gender
                    )
                }[0])
            binding.spnGender.selectedIndex = ind
        }


        val textFormatter2 =
            SpinnerTextFormatter<StaticDataModel> { mModel -> SpannableString(mModel.value) }
        binding.spnPassengerType.setSpinnerTextFormatter(textFormatter2)
        binding.spnPassengerType.setSelectedTextFormatter(textFormatter2)
        binding.spnPassengerType.attachDataSource(CLIENT_DETAILS.passengerTypes)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.passType) && CLIENT_DETAILS.clientInfo.passType != "") {
            var ind =
                CLIENT_DETAILS.passengerTypes.indexOf(CLIENT_DETAILS.passengerTypes.filter {
                    it.id.equals(CLIENT_DETAILS.clientInfo.passType)
                }[0])
            binding.spnPassengerType.selectedIndex = ind
        }

        val textFormatter1 =
            SpinnerTextFormatter<CountryMasterItem> { person -> SpannableString(person.cName) }

        binding.spnDestCountry.setSpinnerTextFormatter(textFormatter1)
        binding.spnDestCountry.setSelectedTextFormatter(textFormatter1)

        binding.spnDestCountry.attachDataSource(CLIENT_DETAILS.countryMaster)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.countryId) && CLIENT_DETAILS.clientInfo.countryId != "0") {
            var ind =
                CLIENT_DETAILS.countryMaster.indexOf(CLIENT_DETAILS.countryMaster.filter { it.cId == CLIENT_DETAILS.clientInfo.countryId.toInt() }[0])
            binding.spnDestCountry.selectedIndex = ind
        }

        binding.etGivenName.setText(CLIENT_DETAILS.clientInfo.givenName)
        binding.etSurname.setText(CLIENT_DETAILS.clientInfo.surname)

        dob = CLIENT_DETAILS.clientInfo.dob
        setDOBInViews()
        var fmtM = SimpleDateFormat("MM", Locale.ENGLISH)
        var fmtD = SimpleDateFormat("dd", Locale.ENGLISH)
        var fmtY = SimpleDateFormat("YYYY", Locale.ENGLISH)


        val date = Date()
        val c1: Calendar = Calendar.getInstance()
        c1.add(Calendar.YEAR, -10)
        val resultDate1: Date = c1.time
        val fm = fmtM.format(resultDate1).toInt()
        val fd = fmtD.format(resultDate1).toInt()
        val fy = fmtY.format(resultDate1).toInt()


        c1.add(Calendar.YEAR, -50)
        val resultDate: Date = c1.time
        val tm = fmtM.format(resultDate).toInt()
        val td = fmtD.format(resultDate).toInt()
        val ty = fmtY.format(resultDate).toInt()

        var currDate = DateUtils.getTimeMiles(fy, fm, fd)
        if (!TextUtils.isEmpty(dob) && dob != "0000-00-00 00:00:00") {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val mDate = formatter.parse(dob)
            val cm = fmtM.format(mDate).toInt()
            val cd = fmtD.format(mDate).toInt()
            val cy = fmtY.format(mDate).toInt()
            currDate = DateUtils.getTimeMiles(cy, cm, cd)
        }


        datePickerPopup = DatePickerPopup.Builder()
            .from(activity)
            .offset(3)
            .darkModeEnabled(false)
            .pickerMode(DatePicker.DAY_ON_FIRST)
            .textSize(19)
            .startDate(DateUtils.getTimeMiles(ty, tm, td))
            .currentDate(currDate)
            .endDate(DateUtils.getTimeMiles(fy, fm, fd))
            .listener { dp, date, day, month, year ->
                dob = "$year-${month + 1}-$day  00:00:00"
                setDOBInViews()
            }
            .build()

    }


    private fun setOldContactInfoData() {
        binding.etEmailAddress.setText(CLIENT_DETAILS.clientInfo.emailAddress)
        binding.etClientPhoneNumber.setText(CLIENT_DETAILS.clientInfo.phoneNumber)
        binding.etParentPhoneNumber.setText(CLIENT_DETAILS.clientInfo.pPhoneNumber)
        binding.etAbroadPhoneNumber.setText(CLIENT_DETAILS.clientInfo.aPhoneNumber)
        binding.etAddressLineOne.setText(CLIENT_DETAILS.clientInfo.addressOne)
        binding.etAddressLineTwo.setText(CLIENT_DETAILS.clientInfo.addressTwo)
        binding.etCity.setText(CLIENT_DETAILS.clientInfo.cityState)
        binding.etPincode.setText(CLIENT_DETAILS.clientInfo.pinCode)

        val textFormatter =
            SpinnerTextFormatter<CountryItem> { mModel -> SpannableString("+" + mModel.phonecode) }


        binding.spnClientPhoneCode.setSpinnerTextFormatter(textFormatter)
        binding.spnClientPhoneCode.setSelectedTextFormatter(textFormatter)

        binding.spnParentPhoneCode.setSpinnerTextFormatter(textFormatter)
        binding.spnParentPhoneCode.setSelectedTextFormatter(textFormatter)


        binding.spnClientPhoneCode.attachDataSource(CLIENT_DETAILS.country)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.phoneCode) && CLIENT_DETAILS.clientInfo.phoneCode != "") {
            var ind =
                CLIENT_DETAILS.country.indexOf(CLIENT_DETAILS.country.filter {
                    it.phonecode.equals(
                        CLIENT_DETAILS.clientInfo.phoneCode
                    )
                }[0])
            binding.spnClientPhoneCode.selectedIndex = ind
        }

        binding.spnParentPhoneCode.attachDataSource(CLIENT_DETAILS.country)
        if (!TextUtils.isEmpty(CLIENT_DETAILS.clientInfo.pPhoneCode) && CLIENT_DETAILS.clientInfo.pPhoneCode != "") {
            var ind =
                CLIENT_DETAILS.country.indexOf(CLIENT_DETAILS.country.filter {
                    it.phonecode.equals(
                        CLIENT_DETAILS.clientInfo.pPhoneCode
                    )
                }[0])
            binding.spnParentPhoneCode.selectedIndex = ind
        }


    }


    private fun setDOBInViews() {
        if (!TextUtils.isEmpty(dob) && dob != "0000-00-00 00:00:00") {
            binding.tvDOB.text = com.tc.crm.utils.DateUtils.oneFormatToAnother(
                dob,
                "yyyy-MM-dd HH:mm:ss",
                "dd-MM-yyyy"
            )
        }

    }


    override fun onError(msg: String?) {
        showMessage(1, msg)

    }

    override fun onCommonResult(body: CommonResult) {
        hideAlertDialog()
        if (body.results.status) {
            showMessage(3, body.results.message)
            onBackPressed()
        } else {
            showMessage(1, body.results.message)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnUpdateCountryCancel -> {
                onBackPressed()
            }

            R.id.btnUpdateSectionCancel -> {
                onBackPressed()
            }

            R.id.btnUpdateClientTypeCancel -> {
                onBackPressed()
            }

            R.id.btnUpdateStaffCancel -> {
                onBackPressed()
            }

            R.id.btnUpdateBasicInfoCancel -> {
                onBackPressed()
            }

            R.id.btnUpdateContactInfoCancel -> {
                onBackPressed()
            }

            R.id.btnUpdateCountry -> {
                prepareCountryUploadRequestData()
            }

            R.id.btnUpdateSection -> {
                prepareSectionUpdate()
            }

            R.id.cvSelectSection -> {
                if (CLIENT_DETAILS.intakeSections != null
                    || CLIENT_DETAILS.intakeSections.size >= 0
                ) {
                    var frag = DDIntakeSectionsFragment()
                    frag.show(childFragmentManager, "")
                } else {
                    showMessage(
                        2,
                        "No active sections available for selected country, Please contact database administrator"
                    )
                }


            }

            R.id.btnUpdateClientType -> {
                prepareClientTypeUpdate()
            }

            R.id.btnUpdateStaff -> {
                prepareStaffUpdate()
            }

            R.id.btnUpdateBasicInfo -> {
                if (validateBasicInfoForm()) {
                    prepareBasicInfoUpdate()
                }
            }

            R.id.cvDOB -> {
                datePickerPopup?.show()
            }

            R.id.btnUpdateContactInfo -> {
               prepareContactInfoUpdate()
            }


        }

    }

    private fun prepareContactInfoUpdate() {
        if (TextUtils.isEmpty(binding.etClientPhoneNumber.text.toString().trim()) || binding.etClientPhoneNumber.text.toString().length <8) {
            showMessage(2, "Please enter client a phone number")
           return
        }

        if (!TextUtils.isEmpty(binding.etEmailAddress.text.toString().trim())){
            if(!TCUtils.isEmailValid(binding.etEmailAddress.text.toString())){
                showMessage(2, "Please enter a valid email address")
                return
            }
        }

        if (!TextUtils.isEmpty(binding.etPincode.text.toString().trim())){
            if(binding.etPincode.text.toString().length <6){
                showMessage(2, "Please enter a valid Pin Code")
                return
            }
        }


        val pPhoneCode = binding.spnParentPhoneCode.selectedItem as CountryItem
        val cPhoneCode = binding.spnClientPhoneCode.selectedItem as CountryItem

        var req = ContactUpdateRequest()
        req.clientId = CLIENT_DETAILS.clientInfo.clientId
        req.userId = PreferenceManager.getInstance().userId
        req.emailAddress = binding.etEmailAddress.text.toString()
        req.cPhoneNumber = binding.etClientPhoneNumber.text.toString()
        req.aPhoneNumber = binding.etAbroadPhoneNumber.text.toString()
        req.pPhoneNumber = binding.etParentPhoneNumber.text.toString()
        req.cPhoneCode = cPhoneCode.phonecode
        req.pPhoneCode = pPhoneCode.phonecode
        req.addressOne = binding.etAddressLineOne.text.toString()
        req.addressTwo =binding.etAddressLineTwo.text.toString()
        req.cityState =binding.etCity.text.toString()
        req.pinCode =binding.etPincode.text.toString()

        showAlertDialog(0, requireActivity(), "Please Wait", "")
        mPresenter?.updateContactInfo(req)

    }


    private fun validateBasicInfoForm(): Boolean {
        if (TextUtils.isEmpty(binding.etGivenName.text.toString().trim())) {
            showMessage(2, "Please enter client given name")
            return false
        }

        if (TextUtils.isEmpty(binding.etSurname.text.toString().trim())) {
            showMessage(2, "Please enter client surname")
            return false
        }

        if (TextUtils.isEmpty(dob) || dob == "0000-00-00 00:00:00") {
            showMessage(2, "Please select date of birth")
            return false
        }

        return true
    }


    private fun prepareBasicInfoUpdate() {
        val passType = binding.spnPassengerType.selectedItem as StaticDataModel
        val gender = binding.spnGender.selectedItem as StaticDataModel
        val country = binding.spnDestCountry.selectedItem as CountryMasterItem
        var req = BasicInfoUpdateRequest()
        req.clientId = CLIENT_DETAILS.clientInfo.clientId
        req.userId = PreferenceManager.getInstance().userId
        req.dob = dob
        req.givenName = binding.etGivenName.text.toString().trim()
        req.surName = binding.etSurname.text.toString().trim()
        req.countryId = country.cId.toString()
        req.gender = gender.id
        req.passengerType = passType.id
        showAlertDialog(0, requireActivity(), "Please Wait", "")
        mPresenter?.updateBasicInfo(req)
    }

    private fun prepareStaffUpdate() {
        val clientType = binding.spnStaffType.selectedItem as TravelConsultantsItem
        var req = UpdateStaffRequest()
        req.clientId = CLIENT_DETAILS.clientInfo.clientId
        req.userId = PreferenceManager.getInstance().userId
        req.staffId = clientType.memId.toString()
        showAlertDialog(0, requireActivity(), "Please Wait", "")
        mPresenter?.updateStaff(req)
    }

    private fun prepareClientTypeUpdate() {
        val clientType = binding.spnClientType.selectedItem as StaticDataModel
        var req = UpdateClientTypeRequest()
        req.clientId = CLIENT_DETAILS.clientInfo.clientId
        req.userId = PreferenceManager.getInstance().userId
        req.clientType = clientType.id
        showAlertDialog(0, requireActivity(), "Please Wait", "")
        mPresenter?.updateClientType(req)
    }

    private fun prepareSectionUpdate() {

        if (TextUtils.isEmpty(iSectionId) || iSectionId.equals("0")) {
            showMessage(2, "Please select any intake section")
        } else {
            var req = IntakeSectionUpdateRequest()
            req.clientId = CLIENT_DETAILS.clientInfo.clientId
            req.userId = PreferenceManager.getInstance().userId
            req.sectionId = iSectionId
            showAlertDialog(0, requireActivity(), "Please Wait", "")
            mPresenter?.updateClientIntakeSection(req)
        }

    }

    private fun prepareCountryUploadRequestData() {
        val country = binding.sCountry.selectedItem as CountryMasterItem
        var req = CountryUploadRequest()
        req.clientId = CLIENT_DETAILS.clientInfo.clientId
        req.userId = PreferenceManager.getInstance().userId
        req.countryId = country.cId.toString()
        showAlertDialog(0, requireActivity(), "Please Wait", "")
        mPresenter?.updateClientCountry(req)
    }


}
