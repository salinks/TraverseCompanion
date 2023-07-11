package com.tc.crm.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tc.crm.R
import com.tc.crm.base.BaseFragment
import com.tc.crm.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() ,View.OnClickListener {

    private var binding: FragmentHomeBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



        controller()
        showHideBack(false)

        return binding?.root
    }

    private fun controller() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onClick(v: View?) {
        when (v?.id) {

           /* R.id.tvThisMonthCancelledJob -> {
                PAGE_CONTENT = "ThisMonthCancelledJob"
                gotoJobList()
            }*/


        }

    }

    override fun onResume() {
        super.onResume()
        showTitle("Dashboard")
        (activity as? HomeActivity)?.callDashboardAPI()
    }



}