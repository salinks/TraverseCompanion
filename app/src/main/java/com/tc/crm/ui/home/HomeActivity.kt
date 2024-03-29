package com.tc.crm.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tc.crm.R
import com.tc.crm.base.BaseActivity
import com.tc.crm.databinding.ActivityHomeBinding
import com.tc.crm.model.fcm.RegisterFCMRequest
import com.tc.crm.model.home.dashboard.DashboardRequest
import com.tc.crm.model.home.dashboard.DashboardResponse
import com.tc.crm.ui.applicationMenu.clientDetails.ClientDetailsFragment
import com.tc.crm.ui.applicationMenu.clientList.ClientListFragment
import com.tc.crm.ui.applicationMenu.editClient.EditClientFragment
import com.tc.crm.ui.contents.activityLogs.ActivityLogsFragment
import com.tc.crm.ui.contents.additionalCost.AdditionalCostFragment
import com.tc.crm.ui.contents.airports.AirportFragment
import com.tc.crm.ui.contents.amenities.AmenitiesFragment
import com.tc.crm.ui.contents.countryList.CountryListFragment
import com.tc.crm.ui.contents.emailConfig.EmailConfigurationFragment
import com.tc.crm.ui.contents.intakeSections.IntakeSectionsFragment
import com.tc.crm.ui.contents.sourceCategory.SourceCategoryFragment
import com.tc.crm.ui.contents.userGroups.UserGroupsFragment
import com.tc.crm.ui.masters.countries.CountriesFragment
import com.tc.crm.utils.AnimationUtil
import com.tc.crm.utils.DefValues
import com.tc.crm.utils.GlobalVariables
import com.tc.crm.utils.PreferenceManager
import org.greenrobot.eventbus.EventBus

class HomeActivity : BaseActivity(), HomeView, OnClickListener {

    lateinit var binder: ActivityHomeBinding
    var homePresenter: HomePresenter? = null
    private var backPressedToExitOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = DataBindingUtil.setContentView(this, R.layout.activity_home)

        homePresenter = HomePresenter(this)
        controller()
        pushFragments(HomeFragment())
    }


    fun showHideDrawer() {
        if (binder.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binder.drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            binder.drawerLayout.openDrawer(GravityCompat.END)
        }
    }


    fun pushFragments(fragment: Fragment) {
        val supFragMan = supportFragmentManager

        supFragMan.beginTransaction()
            .replace(R.id.content_frame, fragment, fragment.javaClass.name)
            .addToBackStack(fragment.javaClass.name)
            .commitAllowingStateLoss()

    }

    override fun onDashboardResponse(dashboardResponse: DashboardResponse?) {
        if (dashboardResponse?.results?.status == true) {
            GlobalVariables.dashboardResponse = dashboardResponse
            GlobalVariables.APP_MENU = dashboardResponse.appMenu
            GlobalVariables.SYSTEM_MENU = dashboardResponse.systemMenu
            handleMenuItems()
        }

    }


    private fun handleMenuItems() {
        if (GlobalVariables.APP_MENU == null) {
            return
        }

        if (GlobalVariables.APP_MENU.clientMenu) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.reClients).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.reClients).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.payment) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.reApproval).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.reApproval).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.callList) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.reCallList).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.reCallList).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.complaint) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relComplaints).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relComplaints).visibility =
                View.GONE
        }


        if (GlobalVariables.APP_MENU.refund) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relRefund).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relRefund).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.team) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relTeam).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relTeam).visibility =
                View.GONE
        }


        if (GlobalVariables.APP_MENU.masters) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relMasters).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relMasters).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.countries) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relCountries).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relCountries).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.source) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relSource).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relSource).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.driver) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relDrivers).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relDrivers).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.lettings) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relLettings).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relLettings).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.universities) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relUniversities).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relUniversities).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.campus) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relCampuses).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relCampuses).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.advancedSearch) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.tvAdvancedSearch).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.tvAdvancedSearch).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.lettingsSearch) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relLettingsSearch).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relLettingsSearch).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.driverSearch) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relDriverSearch).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relDriverSearch).visibility =
                View.GONE
        }


        if (GlobalVariables.SYSTEM_MENU.systemMenus) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.tvSystemSettings).visibility =
                View.VISIBLE
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relContents).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.tvSystemSettings).visibility =
                View.GONE
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relContents).visibility =
                View.GONE
        }

        if (GlobalVariables.SYSTEM_MENU.userGroups) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relUserGroups).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relUserGroups).visibility =
                View.GONE
        }


        if (GlobalVariables.SYSTEM_MENU.sourceCategory) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relSourceCategory).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relSourceCategory).visibility =
                View.GONE
        }


        if (GlobalVariables.SYSTEM_MENU.countryMaster) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relCountryData).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relCountryData).visibility =
                View.GONE
        }


        if (GlobalVariables.SYSTEM_MENU.airports) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relAirports).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relAirports).visibility =
                View.GONE
        }


        if (GlobalVariables.SYSTEM_MENU.dataSection) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relDataSection).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relDataSection).visibility =
                View.GONE
        }


        if (GlobalVariables.SYSTEM_MENU.amenities) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relAmenities).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relAmenities).visibility =
                View.GONE
        }


        if (GlobalVariables.APP_MENU.additionalCost) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relAdditionalCost).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relAdditionalCost).visibility =
                View.GONE
        }

        if (GlobalVariables.APP_MENU.activityLog) {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relActivityLog).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0).findViewById<View>(R.id.relActivityLog).visibility =
                View.GONE
        }


        if (GlobalVariables.SYSTEM_MENU.emailConfig) {
            binder.navView.getHeaderView(0)
                .findViewById<View>(R.id.relEmailConfiguration).visibility =
                View.VISIBLE
        } else {
            binder.navView.getHeaderView(0)
                .findViewById<View>(R.id.relEmailConfiguration).visibility =
                View.GONE
        }


    }

    fun showHideBackButton(show: Boolean) {
        if (show) {
            binder.layoutBarMain.ivBack.visibility = View.VISIBLE
            binder.layoutBarMain.ivBurgerMenu.visibility = View.GONE
        } else {
            binder.layoutBarMain.ivBack.visibility = View.GONE
            binder.layoutBarMain.ivBurgerMenu.visibility = View.VISIBLE
        }

    }

    fun showHideHeader(show: Boolean) {
        if (show) {
            binder.layoutBarMain.relHeader.visibility = View.VISIBLE
        } else {
            binder.layoutBarMain.relHeader.visibility = View.GONE
        }

    }

    fun showTitle(mTitle: String) {
        binder.layoutBarMain.appTitle.text = mTitle

    }

    private fun controller() {
        binder.layoutBarMain.ivBurgerMenu.setOnClickListener(this)
        binder.layoutBarMain.ivBack.setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.reClients).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.reApproval).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.reCallList).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relComplaints)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relRefund).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relTeam).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relMasters).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relCountries)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relSource).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relDrivers).setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relLettings)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relUniversities)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relCampuses)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relLettingsSearch)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relDriverSearch)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relContents)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relUserGroups)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relSourceCategory)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relCountryData)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relAirports)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relDataSection)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relAmenities)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relAdditionalCost)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relEmailConfiguration)
            .setOnClickListener(this)
        binder.navView.getHeaderView(0).findViewById<View>(R.id.relActivityLog)
            .setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBurgerMenu -> {
                showHideDrawer()
            }

            R.id.ivBack -> {
                onBackPressed()
            }

            R.id.reClients -> {
                showHideDrawer()
                pushFragments(ClientListFragment())

            }

            R.id.reApproval -> {

            }

            R.id.reCallList -> {

            }

            R.id.relComplaints -> {

            }

            R.id.relRefund -> {

            }

            R.id.relTeam -> {

            }

            R.id.relMasters -> {
                handleMasterItems()
            }

            R.id.relCountries -> {
                showHideDrawer()
                pushFragments(CountriesFragment())
            }

            R.id.relSource -> {

            }

            R.id.relDrivers -> {

            }

            R.id.relLettings -> {

            }

            R.id.relUniversities -> {

            }

            R.id.relCampuses -> {

            }

            R.id.relLettingsSearch -> {

            }

            R.id.relDriverSearch -> {

            }

            R.id.relContents -> {
                handleSystemMenuItems()
            }

            R.id.relUserGroups -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(UserGroupsFragment())
            }

            R.id.relSourceCategory -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(SourceCategoryFragment())
            }

            R.id.relCountryData -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(CountryListFragment())
            }

            R.id.relAirports -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(AirportFragment())
            }

            R.id.relDataSection -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(IntakeSectionsFragment())

            }

            R.id.relAmenities -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(AmenitiesFragment())

            }

            R.id.relAdditionalCost -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(AdditionalCostFragment())

            }

            R.id.relEmailConfiguration -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(EmailConfigurationFragment())

            }
            R.id.relActivityLog -> {
                handleSystemMenuItems()
                showHideDrawer()
                pushFragments(ActivityLogsFragment())

            }




        }
    }

    private fun handleSystemMenuItems() {
        if (binder.navView.getHeaderView(0)
                .findViewById<View>(R.id.lnrSettingsItems).visibility == View.VISIBLE
        ) {
            AnimationUtil.animateCollapse(
                binder.navView.getHeaderView(0).findViewById<View>(R.id.lnrSettingsItems)
            )
            binder.navView.getHeaderView(0).findViewById<View>(R.id.ivContentsArrow).rotation = 0F
        } else {
            AnimationUtil.animateExpand(
                binder.navView.getHeaderView(0).findViewById<View>(R.id.lnrSettingsItems)
            )
            binder.navView.getHeaderView(0).findViewById<View>(R.id.ivContentsArrow).rotation = 180F
        }
    }

    private fun handleMasterItems() {
        if (binder.navView.getHeaderView(0)
                .findViewById<View>(R.id.lnrMasterItems).visibility == View.VISIBLE
        ) {
            AnimationUtil.animateCollapse(
                binder.navView.getHeaderView(0).findViewById<View>(R.id.lnrMasterItems)
            )
            binder.navView.getHeaderView(0).findViewById<View>(R.id.ivMastersArrow).rotation = 0F
        } else {
            AnimationUtil.animateExpand(
                binder.navView.getHeaderView(0).findViewById<View>(R.id.lnrMasterItems)
            )
            binder.navView.getHeaderView(0).findViewById<View>(R.id.ivMastersArrow).rotation = 180F
        }
    }


    override fun onError(text: String?) {

    }

    fun callDashboardAPI() {
        val request = DashboardRequest()
        request.userId = PreferenceManager.getInstance().userId
        homePresenter?.getDashboard(request)
    }

    private fun notifyBackPress() {
        if (backPressedToExitOnce) {
            ActivityCompat.finishAffinity(this)
            return
        }
        backPressedToExitOnce = true
        showMessage(DefValues.WARNING, "Press again to exit")
        Handler().postDelayed({ backPressedToExitOnce = false }, 2000)
    }

    override fun onBackPressed() {
        if (binder.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binder.drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            val f = supportFragmentManager.findFragmentById(R.id.content_frame)
            if (f is HomeFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as HomeFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    notifyBackPress()
                }
            } else if (f is CountriesFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as CountriesFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is UserGroupsFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as UserGroupsFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is SourceCategoryFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as SourceCategoryFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is CountryListFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as CountryListFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is AirportFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as AirportFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is IntakeSectionsFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as IntakeSectionsFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is AmenitiesFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as AmenitiesFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is AdditionalCostFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as AdditionalCostFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is EmailConfigurationFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as EmailConfigurationFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is ActivityLogsFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as ActivityLogsFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            }else if (f is ClientListFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as ClientListFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(HomeFragment())
                }
            } else if (f is ClientDetailsFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as ClientDetailsFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(ClientListFragment())
                }
            }else if (f is EditClientFragment) {
                val fragment1 =
                    supportFragmentManager.findFragmentById(R.id.content_frame) as EditClientFragment?
                if (fragment1 != null && fragment1.isVisible) {
                    pushFragments(ClientDetailsFragment())
                }
            }else {
                super.onBackPressed()
            }
        }
    }

    fun logout() {

    }
    var PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var CALL_PERMISSIONS = arrayOf(
        Manifest.permission.CALL_PHONE
    )
    fun pickProfileImage() {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            && hasPermission(Manifest.permission.CAMERA)
        ) {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(galleryIntent)
        } else {
            permReqLauncherGallery.launch(
                PERMISSIONS
            )
        }

    }

    var phoneNUmberToCall=""
    fun makeCall(phoneNUmber: String) {
        if (hasPermission(Manifest.permission.CALL_PHONE)) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNUmber"))
            startActivity(intent)
        } else {
            phoneNUmberToCall =phoneNUmber
            permReqLauncherCall.launch(CALL_PERMISSIONS)
        }
    }


    private val permReqLauncherCall =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNUmberToCall"))
                startActivity(intent)
            }
        }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                EventBus.getDefault().post(result.data?.data)


            }
        }

    private val permReqLauncherGallery =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                val galleryIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                resultLauncher.launch(galleryIntent)
            }
        }

}