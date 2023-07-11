package com.tc.crm.ui.applicationMenu.clientDetails


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.tc.crm.R
import com.tc.crm.base.BaseFragment
import com.tc.crm.databinding.FragmentClientDetailsBinding
import com.tc.crm.model.clientDetails.ClientDetailsRequest
import com.tc.crm.model.clientDetails.ClientDetailsResponse
import com.tc.crm.model.clientDetails.ClientInfo
import com.tc.crm.model.filters.SelectedFilter
import com.tc.crm.ui.applicationMenu.clientDetails.history.ClientHistoryFragment
import com.tc.crm.utils.DateUtils
import com.tc.crm.utils.GlobalVariables
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class ClientDetailsFragment : BaseFragment(),
    ClientDetailsView,OnClickListener {
    lateinit var binding: FragmentClientDetailsBinding
    lateinit var clientInfo: ClientInfo
    var mPresenter: ClientDetailsPresenter? = null

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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showTitle("Client Details")

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

    override fun onError(msg: String?) {
        showMessage(1, msg)

    }

    override fun onClientDetailsResponse(body: ClientDetailsResponse) {
        if (body?.results != null && body.results.status) {
            clientInfo = body.clientInfo
            showValuesInVies()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showValuesInVies() {
        if(clientInfo != null){

        binding.tvClientName.text = clientInfo.givenName + " " + clientInfo.surname
        binding.tvSlNUm.text = "SL NO : " + clientInfo.slnumber
        binding.tvCreatedOn.text = DateUtils.oneFormatToAnother(
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


        binding.tvPassengerType.text = ": " + clientInfo.passType
        binding.tvGender.text = ": " + clientInfo.gender
        binding.tvDOB.text = ": "
        if (!TextUtils.isEmpty(clientInfo.dob) && !clientInfo.dob.equals("0000-00-00 00:00:00")) {
            binding.tvDOB.text = ": " + DateUtils.oneFormatToAnother(
                clientInfo.dob,
                "yyyy-MM-dd HH:mm:ss",
                "dd-MM-yyyy"
            )
        }


    }
    }

    override fun onClick(v: View?) {
       when(v!!.id){
           R.id.lnrEditBasicInfo->{

           }

           R.id.cvViewHistory->{
               var fragCH = ClientHistoryFragment()
               fragCH.history = clientInfo.history
               fragCH.show(childFragmentManager,"CLIENT_HISTORY")

           }
       }
    }


}
