package com.tc.crm.ui.contents.emailConfig


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tc.crm.R
import com.tc.crm.TCApp
import com.tc.crm.TCApp.Companion.hideAlertDialog
import com.tc.crm.base.BaseFragment
import com.tc.crm.data.model.CommonRequest
import com.tc.crm.data.model.CommonResult
import com.tc.crm.databinding.FragmentEmailConfigBinding
import com.tc.crm.model.emailConfig.EmailConfiguration
import com.tc.crm.model.emailConfig.EmailConfigurationRequest
import com.tc.crm.model.emailConfig.EmailConfigurationResponse
import com.tc.crm.ui.home.HomeActivity
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.PreferenceManager


class EmailConfigurationFragment : BaseFragment(),
    EmailConfigView {

    private var binding: FragmentEmailConfigBinding? = null
    var mPresenter: EmailConfigPresenter? = null
    var emailConfiguration: EmailConfiguration? = null

    companion object {
        fun getInstance(): EmailConfigurationFragment {
            return EmailConfigurationFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter =
            EmailConfigPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_email_config, container, false)



        binding!!.relEdit.setOnClickListener {
            binding!!.lnrEdit.visibility = View.VISIBLE
            binding!!.relEdit.visibility = View.GONE
            binding!!.cvInfo.visibility = View.GONE
            binding!!.relUpdate.visibility = View.VISIBLE
        }

        binding!!.relUpdate.setOnClickListener {
            if (validateForm()) {
                prepareData()
            }
        }





        return binding?.root
    }

    private fun prepareData() {
        var request = EmailConfigurationRequest()
        request.emailHost = binding!!.etHost.text.toString()
        request.portNumber = binding!!.etPort.text.toString()
        request.emailAddress = binding!!.etEmailAddress.text.toString()
        request.emailPassword = binding!!.etPassword.text.toString()
        request.fromName = binding!!.etFromName.text.toString()
        request.bccAddress = binding!!.etBCCAddress.text.toString()
        request.userId = PreferenceManager.getInstance().userId

        activity?.let {
            TCApp.showAlertDialog(
                0,
                it, "Please Wait", "Processing your request"
            )
        }
        mPresenter?.updateEmailConfiguration(request)
    }

    private fun validateForm(): Boolean {

        if (TextUtils.isEmpty(binding!!.etHost.text.toString())) {
            AnimationUtil.TranslateView(context, binding!!.etHost)
            binding!!.etHost.error = "Enter email host"
            binding!!.etHost.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binding!!.etPort.text.toString())) {
            AnimationUtil.TranslateView(context, binding!!.etPort)
            binding!!.etPort.error = "Enter port address"
            binding!!.etPort.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binding!!.etEmailAddress.text.toString())) {
            AnimationUtil.TranslateView(context, binding!!.etEmailAddress)
            binding!!.etEmailAddress.error = "Enter email address"
            binding!!.etEmailAddress.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binding!!.etPassword.text.toString())) {
            AnimationUtil.TranslateView(context, binding!!.etPassword)
            binding!!.etPassword.error = "Enter email password"
            binding!!.etPassword.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binding!!.etFromName.text.toString())) {
            AnimationUtil.TranslateView(context, binding!!.etFromName)
            binding!!.etFromName.error = "Enter email from name"
            binding!!.etFromName.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(binding!!.etBCCAddress.text.toString())) {
            AnimationUtil.TranslateView(context, binding!!.etBCCAddress)
            binding!!.etBCCAddress.error = "Enter BCC Address"
            binding!!.etBCCAddress.requestFocus()
            return false
        }

        return true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        showHideBack(true)
        showTitle("Email Configuration")
        binding!!.progressView.visibility = View.VISIBLE
        binding!!.lnrMain.visibility = View.GONE
        binding!!.relEdit.visibility = View.GONE
        binding!!.relUpdate.visibility = View.GONE
        var req = CommonRequest()
        req.userId = PreferenceManager.getInstance().userId
        mPresenter?.getEmailConfiguration(req)

    }


    override fun onError(msg: String?) {

        hideAlertDialog()
    }


    override fun onCommonResult(body: CommonResult) {
        hideAlertDialog()
        if (body.results.status) {
            showMessage(3, body.results.message)
            var req = CommonRequest()
            req.userId = PreferenceManager.getInstance().userId
            mPresenter?.getEmailConfiguration(req)
        } else {
            showMessage(1, body.results.message)
        }

    }

    override fun onEmailConfigurationResponse(body: EmailConfigurationResponse) {
        hideAlertDialog()
        if (body.results?.status == true) {
            binding!!.progressView.visibility = View.GONE
            if (body.emailConfiguration != null) {

                binding!!.lnrEdit.visibility = View.GONE
                binding!!.relEdit.visibility = View.VISIBLE
                binding!!.cvInfo.visibility = View.VISIBLE
                binding!!.relUpdate.visibility = View.GONE

                binding!!.lnrMain.visibility = View.VISIBLE
                binding!!.relEdit.visibility = View.VISIBLE
                emailConfiguration = body.emailConfiguration
                showValuesInViews()
            } else {
                showMessage(1, body.results.message)
                onBackPressed()
            }
        } else {

            if (body.results?.message.equals("AUTHORIZATION_FAILDED") ||
                body.results?.message.equals("AUTHORIZATION_FAILDED")
            ) {
                (activity as HomeActivity).logout()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showValuesInViews() {
        if (emailConfiguration != null) {


            binding!!.etHost.setText(emailConfiguration?.emailHost)
            binding!!.tvHost.text = ": " + emailConfiguration?.emailHost

            binding!!.etPort.setText(emailConfiguration?.portNumber)
            binding!!.tvPort.text = ": " + emailConfiguration?.portNumber

            binding!!.etEmailAddress.setText(emailConfiguration?.emailAddress)
            binding!!.tvEmailAddress.text = ": " + emailConfiguration?.emailAddress

            binding!!.etPassword.setText(emailConfiguration?.emailPassword)
            binding!!.tvPassword.text = ": " + emailConfiguration?.emailPassword

            binding!!.etFromName.setText(emailConfiguration?.fromName)
            binding!!.tvFromName.text = ": " + emailConfiguration?.fromName

            binding!!.etBCCAddress.setText(emailConfiguration?.bccAddress)
            binding!!.tvBCCAddress.text = ": " + emailConfiguration?.bccAddress

        }

    }


}
