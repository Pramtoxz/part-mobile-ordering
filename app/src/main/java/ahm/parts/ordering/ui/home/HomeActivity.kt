package ahm.parts.ordering.ui.home

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.AESEDecrypt
import ahm.parts.ordering.helper.ClickPrevention
import ahm.parts.ordering.helper.ToastUtil
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.dialog.BasicDialog
import ahm.parts.ordering.ui.dialog.BasicRenewTokenDialog
import ahm.parts.ordering.ui.home.catalogue.CatalogueFragment
import ahm.parts.ordering.ui.home.dealer.DealerFragment
import ahm.parts.ordering.ui.home.profile.ProfileFragment
import ahm.parts.ordering.ui.home.home.HomeFragment
import ahm.parts.ordering.ui.home.order.order.OrderFragment
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_home.*

/**
 * class ini mengandung interaksi UI dengan layout activity_home.xml
 *
 */

class HomeActivity : BaseActivity<HomeViewModel>(), ClickPrevention {

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_home)

        initNavigation()
    }

    /**
     * fungsi untuk membuat bottom bar
     *
     */
    private fun initNavigation() {
        openPage(HomeFragment.newInstance(this))

        bottomNavigation.enableAnimation(false)
        bottomNavigation.enableShiftingMode(false)
        bottomNavigation.enableItemShiftingMode(false)
        bottomNavigation.setTextSize(10f)
        bottomNavigation.setTypeface(ResourcesCompat.getFont(this,R.font.opensans_semibold))

        viewModel.getUser().observe(this, Observer {
            try {
                user = it
                when(user!!.id_role)  {
                    Constants.ROLE.NONCHANNEL -> {
                        bottomNavigation.currentItem = 1
                        openPage(CatalogueFragment.newInstance())
                    }
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        })

        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->

            var navSelect = true

            when (menuItem.itemId) {
                R.id.menu_wallboard -> {
//                    when (user!!.id_role) {
//                        Constants.ROLE.NONCHANNEL -> {
//                            navSelect = false
//                            snacked(
//                                rootView,
//                                getString(R.string.alert_message_menu_not_access_home)
//                            )
//                        }
//                        else -> {
//                            navSelect = true
//                            openPage(HomeFragment.newInstance(this))
//                        }
//                    }
                    navSelect = true
                    openPage(HomeFragment.newInstance(this))
                }
                R.id.menu_stock -> {
                    navSelect = true
                    openPage(CatalogueFragment.newInstance())
                }
                R.id.menu_sales -> {
                    when (user!!.id_role) {
                        Constants.ROLE.NONCHANNEL -> {
                            navSelect = false
                            snacked(
                                rootView,
                                getString(R.string.alert_message_menu_not_access_tracking_order)
                            )
                        }
                        else -> {
                            navSelect = true
                            openPage(OrderFragment.newInstance())
                        }
                    }

                }
                R.id.menu_catalogue -> {
                    when (user!!.id_role) {
                        Constants.ROLE.NONCHANNEL -> {
                            navSelect = false
                            snacked(
                                rootView,
                                getString(R.string.alert_message_menu_not_access_dealer)
                            )
                        }
                        else -> {
                            navSelect = true
                            openPage(DealerFragment.newInstance(this))
                        }
                    }
                }
                R.id.menu_more -> {
                    navSelect = true
                    openPage(ProfileFragment.newInstance())
                }
                else -> HomeFragment.newInstance(this)
            }

            viewModel.getUser().observe(this, Observer {
                try {
                    if(it?.id_role != Constants.ROLE.NONCHANNEL){
                        btnOrder.setBackgroundResource(
                            when (menuItem.itemId) {
                                R.id.menu_sales -> R.drawable.bg_fill_btn_center_bottombar_focused
                                else -> R.drawable.bg_fill_btn_center_bottombar
                            }
                        )
                    }
                } catch (e: Exception) {
                }
            })

            navSelect
        }

        bottomNavigation.setOnNavigationItemReselectedListener { menuItem ->

        }

        btnOrder.setOnClickListener(this)
    }

    /**
     * fungsi untuk mengganti fragement dihome
     *
     */
    private fun openPage(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun dialogMenuNotAccess(){
        BasicDialog(this, R.style.Dialog)
            .setTitleVisibility(false)
            .setMessage(R.string.alert_message_menu_not_access_tracking_order)
            .setOnlyText()
            .setMessageGravity(Gravity.BOTTOM)
            .setPositiveButton(R.string.btn_oke)
            .setSingleButton()
            .setButtonPositiveClickListener(object : BasicDialog.ButtonPositiveClickListener {
                override fun clicked(dialog: BasicDialog) {
                    dialog.dismiss()
                }
            })
            .show()
    }

    /**
     * fungsi click dihome
     *
     */
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnOrder -> {
                if (user?.id_role != Constants.ROLE.NONCHANNEL) {
                    bottomNavigation.currentItem = 2
                } else {
                    snacked(
                        rootView,
                        getString(R.string.alert_message_menu_not_access_tracking_order)
                    )
                }
            }
        }
        super.onClick(v)
    }
}
