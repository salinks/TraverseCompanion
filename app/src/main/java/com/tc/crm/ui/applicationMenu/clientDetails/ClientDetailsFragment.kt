package com.tc.crm.ui.applicationMenu.clientDetails


import android.R.id.message
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tc.crm.R
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.TCApp.Companion.showAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.listners.AlertListener
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentClientDetailsBinding
import com.tc.crm.model.clientDetails.ClientDetailsRequest
import com.tc.crm.model.clientDetails.ClientDetailsResponse
import com.tc.crm.model.clientDetails.ClientInfo
import com.tc.crm.model.clientDetails.req.UploadImageRequest
import com.tc.crm.model.filters.SelectedFilter
import com.tc.crm.ui.applicationMenu.clientDetails.history.ClientHistoryFragment
import com.tc.crm.ui.applicationMenu.editClient.EditClientFragment
import com.tc.crm.ui.customViews.alert.AlertFragment
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.DateUtils
import com.tc.crm.utils.GlobalVariables
import com.tc.crm.utils.GlobalVariables.CLIENT_DETAILS
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.net.URLEncoder


class ClientDetailsFragment : BaseFragment(),
    ClientDetailsView, OnClickListener {
    lateinit var binding: FragmentClientDetailsBinding
    lateinit var clientInfo: ClientInfo
    var mPresenter: ClientDetailsPresenter? = null
    var picFor = ""

    companion object {
        fun getInstance(): ClientDetailsFragment {
            return ClientDetailsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = ClientDetailsPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_client_details, container, false)

        // binding?.rcvClients?.layoutManager = LinearLayoutManager(activity)
        onClickControllers()

        return binding?.root
    }

    private fun onClickControllers() {
        binding.lnrEditBasicInfo.setOnClickListener(this)
        binding.cvViewHistory.setOnClickListener(this)
        binding.cvLinkIdCopy.setOnClickListener(this)
        binding.cvGenerateLink.setOnClickListener(this)
        binding.cvClientChangeImage.setOnClickListener(this)
        binding.lnrEditDatabase.setOnClickListener(this)
        binding.lnrEditClientType.setOnClickListener(this)
        binding.lnrEditLeadHead.setOnClickListener(this)
        binding.lnrCallAbroadPhone.setOnClickListener(this)
        binding.lnrCallParent.setOnClickListener(this)
        binding.lnrCallClient.setOnClickListener(this)
        binding.lnrEditContact.setOnClickListener(this)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showTitle("Client Details")
        binding.lnrProgress.visibility = View.VISIBLE
        binding.nsv.visibility = View.GONE
        binding.progressView?.let { Glide.with(this).asGif().load(R.raw.ct_loading).into(it) };
        var req = ClientDetailsRequest()
        req.userId = PreferenceManager.getInstance().userId
        req.clientId = GlobalVariables.ClientID
        mPresenter?.getClientDetails(req)

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
        if (request != null) {

        }


    }

    @Subscribe
    fun getSImage(selectedImage: Uri) {
        var recId = "0"
        if (clientInfo.documents != null) {
            recId = clientInfo.documents.recId.toString()
        }

        if (selectedImage != null) {
            if (selectedImage != null) {
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                if (selectedImage != null) {
                    val cursor: Cursor = activity?.contentResolver?.query(
                        selectedImage,
                        filePathColumn, null, null, null
                    )!!
                    if (cursor != null) {
                        cursor.moveToFirst()
                        val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                        val picturePath: String = cursor.getString(columnIndex)
                        cursor.close()
                        showAlertDialog(0, requireActivity(), "Please Wait", "")
                        var req = UploadImageRequest();
                        req.userId = PreferenceManager.getInstance().userId
                        req.imageFile = File(picturePath)
                        req.clientId = clientInfo.clientId
                        req.uploadFor = picFor
                        req.recId = recId
                        mPresenter?.ChangeLogo(req)
                    }
                }

            }
        }
    }

    override fun onError(msg: String?) {
        showMessage(1, msg)

    }

    override fun onClientDetailsResponse(body: ClientDetailsResponse) {
        hideAlertDialog()
        if (body.results != null && body.results.status) {
            CLIENT_DETAILS = body
            binding.lnrProgress.visibility = View.GONE
            binding.nsv.visibility = View.VISIBLE
            clientInfo = body.clientInfo
            showValuesInVies()
        } else {
            showMessage(1, body.results.message)

            Handler().postDelayed({ onBackPressed() }, 3000)
        }

    }

    override fun onCommonResult(body: CommonResult?) {

        if (body?.results?.status == true) {
            showMessage(3, body.results.message)
            var req = ClientDetailsRequest()
            req.userId = PreferenceManager.getInstance().userId
            req.clientId = GlobalVariables.ClientID
            mPresenter?.getClientDetails(req)
        } else {
            showMessage(1, body?.results?.message)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showValuesInVies() {
        if (clientInfo != null) {

            binding.tvClientName.text = clientInfo.givenName + " " + clientInfo.surname
            binding.tvSlNUm.text = "SL NO : " + clientInfo.slnumber
            binding.tvCreatedOn.text = ": " + DateUtils.oneFormatToAnother(
                clientInfo.createdOn,
                "yyyy-MM-dd HH:mm:ss",
                "dd-MM-yyyy hh:mm a"
            )

            var imageName = ""
            if (clientInfo.documents != null && !TextUtils.isEmpty(clientInfo.documents.clientPic)) {
                imageName = clientInfo.documents.clientPic
            } else {
                imageName = "defaultpic.png"
            }
            Glide.with(this)
                .load(clientInfo.imagePath + imageName)
                .into(binding.ivClientPic)


            if (clientInfo.canEdit
                && clientInfo.payment != null
                && clientInfo.refundRecord == null
                && clientInfo.payment.paymentType == 1
                && (clientInfo.payment.paymentStatus == 1 || clientInfo.payment.paymentStatus == 3)
            ) {
                binding.cvCancelClient.visibility = View.VISIBLE
            } else {
                binding.cvCancelClient.visibility = View.GONE
            }

            if (clientInfo.cStatus == 4
                && clientInfo.refundRecord == null
            ) {
                binding.cvDownLoadVoucher.visibility = View.VISIBLE
                binding.cvSendVoucher.visibility = View.VISIBLE
            } else {
                binding.cvDownLoadVoucher.visibility = View.GONE
                binding.cvSendVoucher.visibility = View.GONE
            }


            if (clientInfo.isSuperAdmin) {
                binding.cvViewHistory.visibility = View.VISIBLE
            } else {
                binding.cvViewHistory.visibility = View.GONE
            }

            if (clientInfo.canEdit
                && TextUtils.isEmpty(clientInfo.linkId)
                && (clientInfo.cStatus == 0 || clientInfo.cStatus == 1)
            ) {
                binding.cvGenerateLink.visibility = View.VISIBLE
            } else {
                binding.cvGenerateLink.visibility = View.GONE
            }


            var sec = "Unassigned"
            var cCode = ""
            if (!TextUtils.isEmpty(clientInfo.cCode)) {
                cCode = "," + clientInfo.cCode
            }
            if (!TextUtils.isEmpty(clientInfo.sectionName)) {
                sec = clientInfo.sectionName + cCode
            }

            binding.tvDataBaseName.text = ":  $sec"
            binding.tvLeadHead.text = ":  " + if (!TextUtils.isEmpty(clientInfo.staffName)) {
                clientInfo.staffName
            } else {
                "Unassigned"
            }
            binding.tvClientType.text = ":  " + if (!TextUtils.isEmpty(clientInfo.clientType)) {
                clientInfo.clientType
            } else {
                "Unassigned"
            }
            binding.tvClientStage.text = ":  " + clientInfo.stageName
            binding.tvPassengerType.text = ":  " + clientInfo.passType
            binding.tvGender.text = ":  " + clientInfo.gender
            binding.tvDOB.text = ":  "
            if (!TextUtils.isEmpty(clientInfo.dob) && !clientInfo.dob.equals("0000-00-00 00:00:00")) {
                binding.tvDOB.text = ":  " + DateUtils.oneFormatToAnother(
                    clientInfo.dob,
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MM-yyyy"
                )
            }

            binding.tvFollowUpDate.text = ":  "
            if (!TextUtils.isEmpty(clientInfo.nfd) && !clientInfo.nfd.equals("0000-00-00 00:00:00")) {
                binding.tvFollowUpDate.text = ":  " + DateUtils.oneFormatToAnother(
                    clientInfo.nfd,
                    "yyyy-MM-dd HH:mm:ss",
                    "dd-MM-yyyy  hh:mm a"
                )
            }

            when (clientInfo.cStatus) {
                0 -> {
                    binding.tvClientStatus.text = getString(R.string.sts_open)
                    binding.tvClientStatus.setTextColor(Color.parseColor("#007bff"))
                }

                1 -> {
                    binding.tvClientStatus.text = getString(R.string.sts_pending)
                    binding.tvClientStatus.setTextColor(Color.parseColor("#ffc107"))
                }

                2 -> {
                    binding.tvClientStatus.text = getString(R.string.sts_in_progress)
                    binding.tvClientStatus.setTextColor(Color.parseColor("#0c2556"))
                }

                3 -> {
                    binding.tvClientStatus.text = getString(R.string.sts_rejected)
                    binding.tvClientStatus.setTextColor(Color.parseColor("#dc3545"))
                }

                4 -> {
                    binding.tvClientStatus.text = getString(R.string.sts_closed)
                    binding.tvClientStatus.setTextColor(Color.parseColor("#28a745"))
                }

                5 -> {
                    binding.tvClientStatus.text = getString(R.string.sts_cancelled_advance)
                    binding.tvClientStatus.setTextColor(Color.parseColor("#a13b0a"))
                }

                6 -> {
                    binding.tvClientStatus.text = getString(R.string.sts_cancelled_full_pay)
                    binding.tvClientStatus.setTextColor(Color.parseColor("#a13b0a"))
                }
            }


            if ((clientInfo.cStatus == 0 || clientInfo.cStatus == 1)
                && clientInfo.stageName.equals("Lead")
                && !TextUtils.isEmpty(clientInfo.linkId)
            ) {
                binding.relLinkId.visibility = View.VISIBLE
                binding.tvLinkId.text = clientInfo.baseURL + "register/" + clientInfo.linkId
            } else {
                binding.relLinkId.visibility = View.GONE
            }





            if (clientInfo.canEdit) {
                binding.lnrEditDatabase.visibility = View.VISIBLE
            } else {
                binding.lnrEditDatabase.visibility = View.INVISIBLE
            }


            if (clientInfo.cStatus == 0 && (clientInfo.isSuperAdmin
                        || clientInfo.staffId.equals(PreferenceManager.getInstance().userId))
            ) {
                binding.lnrEditLeadHead.visibility = View.VISIBLE
            } else {
                binding.lnrEditLeadHead.visibility = View.INVISIBLE
            }


            if (clientInfo.canEdit && (clientInfo.cStatus == 0
                        || clientInfo.cStatus == 1
                        || clientInfo.cStatus == 2)
            ) {
                binding.lnrEditClientType.visibility = View.VISIBLE
            } else {
                binding.lnrEditClientType.visibility = View.INVISIBLE
            }

            var parentPhone = ""
            if (!TextUtils.isEmpty(clientInfo.pPhoneNumber)) {
                parentPhone = "+" + clientInfo.pPhoneCode + clientInfo.pPhoneNumber
            }
            var abroadContact = ""
            if (!TextUtils.isEmpty(clientInfo.aPhoneNumber)) {
                abroadContact = "+" + clientInfo.cmPhoneCode + clientInfo.aPhoneNumber
            }
            var addressLine = ""
            if (!TextUtils.isEmpty(clientInfo.addressOne)) {
                addressLine =
                     clientInfo.addressOne + ", " + clientInfo.addressTwo + ", " + clientInfo.cityState + ", Pin Code - " + clientInfo.pinCode
            }
            binding.tvEmail.text = ":  " + clientInfo.emailAddress
            binding.tvPhone.text = ":  +" + clientInfo.phoneCode + clientInfo.phoneNumber
            binding.tvParentPhone.text = ":  $parentPhone"
            binding.tvAbroadContact.text = ":  $abroadContact"
            binding.tvAddress.text = ":  $addressLine"

            if (!TextUtils.isEmpty(clientInfo.phoneNumber)
                && (clientInfo.isSuperAdmin
                        || clientInfo.staffId.equals(PreferenceManager.getInstance().userId))
            ) {
                binding.lnrCallClient.visibility = View.VISIBLE
            } else {
                binding.lnrCallClient.visibility = View.INVISIBLE
            }

            if (!TextUtils.isEmpty(clientInfo.pPhoneNumber)
                && (clientInfo.isSuperAdmin
                        || clientInfo.staffId.equals(PreferenceManager.getInstance().userId))
            ) {
                binding.lnrCallParent.visibility = View.VISIBLE
            } else {
                binding.lnrCallParent.visibility = View.INVISIBLE
            }


            if (!TextUtils.isEmpty(clientInfo.aPhoneNumber)
                && (clientInfo.isSuperAdmin
                        || clientInfo.staffId.equals(PreferenceManager.getInstance().userId))
            ) {
                binding.lnrCallAbroadPhone.visibility = View.VISIBLE
            } else {
                binding.lnrCallAbroadPhone.visibility = View.INVISIBLE
            }


//======================================
        }
    }

    private fun showCountryEmptyMessage() {


        val fragment = AlertFragment.getInstance()
        fragment.setAlertTitle(getString(R.string.app_name))
        fragment.setMessageText("You cannot perform this action. Please update country")
        fragment.setPositiveButtonText("Update Country")
        fragment.setNegativeButtonText("Cancel")
        fragment.setOnClickLlistner(object : AlertListener {

            override fun onPositiveClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
                GlobalVariables.ViewName = "COUNTRY_SECTION"
                pushFragment(EditClientFragment())
            }

            override fun onNegativeClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
            }
        })
        fragment.show(childFragmentManager, "alertr")
    }

    private fun linkUrlToWhatsappConfirmation() {
        val fragment = AlertFragment.getInstance()
        fragment.setAlertTitle(getString(R.string.app_name))
        fragment.setMessageText("Client register form url copied to clipboard. Do you want to send it through whatsapp?")
        fragment.setPositiveButtonText("Send")
        fragment.setNegativeButtonText("Cancel")
        fragment.setOnClickLlistner(object : AlertListener {
            @SuppressLint("QueryPermissionsNeeded")
            override fun onPositiveClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
                val packageManager = context!!.packageManager
                val i = Intent(Intent.ACTION_VIEW)
                var mesg = "Greetings from *Traverse Companion* " +
                        "\n" +
                        "\n" +
                        "Kindly fill out the attached form." +
                        "\n" +
                        "\n" +

                        binding.tvLinkId.text.toString().trim() +
                        "\n" +
                        "\n" +
                        "Regards"

                try {
                    val url =
                        "https://api.whatsapp.com/send?phone=" + clientInfo.phoneCode + clientInfo.phoneNumber + "&text=" + URLEncoder.encode(
                            mesg,
                            "UTF-8"
                        )
                    i.setPackage("com.whatsapp")
                    i.data = Uri.parse(url)
                    if (i.resolveActivity(packageManager) != null) {
                        context!!.startActivity(i)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onNegativeClick(alertFragment: Fragment) {
                (alertFragment as AlertFragment).dismiss()
            }
        })
        fragment.show(childFragmentManager, "alertr")
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lnrEditBasicInfo -> {
                GlobalVariables.ViewName = "BASIC_INFO"
                pushFragment(EditClientFragment())
            }

            R.id.cvViewHistory -> {
                var fragCH = ClientHistoryFragment()
                fragCH.history = clientInfo.history
                fragCH.show(childFragmentManager, "CLIENT_HISTORY")

            }

            R.id.cvLinkIdCopy -> {
                val clipboardManager =
                    activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData =
                    ClipData.newPlainText("text", binding.tvLinkId.text.toString().trim())
                clipboardManager.setPrimaryClip(clipData)
                linkUrlToWhatsappConfirmation()

            }

            R.id.cvGenerateLink -> {
                var req = ClientDetailsRequest()
                req.userId = PreferenceManager.getInstance().userId
                req.clientId = GlobalVariables.ClientID
                mPresenter?.generateLink(req)
                activity?.let {
                    showAlertDialog(
                        0,
                        it,
                        "Please Wait",
                        ""
                    )
                }
            }

            R.id.cvClientChangeImage -> {
                picFor = "profile"
                (activity as HomeActivity).pickProfileImage()
            }

            R.id.lnrEditDatabase -> {
                if (TextUtils.isEmpty(clientInfo.countryId) || clientInfo.countryId == "0") {
                    showCountryEmptyMessage()
                } else {
                    GlobalVariables.ViewName = "INTAKE_SECTION"
                    pushFragment(EditClientFragment())
                }
            }

            R.id.lnrEditClientType -> {
                GlobalVariables.ViewName = "CLIENT_TYPE"
                pushFragment(EditClientFragment())
            }

            R.id.lnrEditLeadHead -> {
                GlobalVariables.ViewName = "STAFF"
                pushFragment(EditClientFragment())
            }
            R.id.lnrEditContact -> {
                GlobalVariables.ViewName = "CONTACT_INFO"
                pushFragment(EditClientFragment())
            }

            R.id.lnrCallAbroadPhone -> {
                (activity as HomeActivity).makeCall("+"+clientInfo.cmPhoneCode+clientInfo.aPhoneNumber)
            }

            R.id.lnrCallParent -> {
                (activity as HomeActivity).makeCall("+"+clientInfo.pPhoneCode+clientInfo.pPhoneNumber)
            }

            R.id.lnrCallClient -> {
                (activity as HomeActivity).makeCall("+"+clientInfo.phoneCode+clientInfo.phoneNumber)
            }


        }
    }


}
