package ahm.parts.ordering.ui.home.profile

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.ClickPrevention
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.helper.loadImageCircle
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.dialog.AlertDialog
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.profile.changepassword.ChangePasswordActivity
import ahm.parts.ordering.ui.previewimage.PreviewImageActivity
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.Exception

/**
 * class ini mengandung interaksi UI dengan layout fragment_profile.xml
 *
 */

class ProfileFragment : BaseFragment<HomeViewModel>(), ClickPrevention {

    lateinit var userProfile: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        initListener()
    }

    /**
     * fungsi menghandle api
     *
     */
    private fun observeData() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            try {
                userProfile = user!!
                tvName text user!!.name
                tvJob text user.role
                tvCodeUser text user.codeUser
                tvPhoneNumber text user.phoneNumber
                tvEmail text user.email

                ivUser loadImageCircle user.photo
            } catch (e: Exception) {
                e.printStackTrace()
            }

        })
    }

    /**
     * fungsi click
     *
     */
    fun initListener() {
        ivUser.setOnClickListener(this)
        btnLogout.setOnClickListener(this)
        vChangePassword.setOnClickListener(this)
//        btnProfile.setOnClickListener(this)
//        btnInbox.setOnClickListener(this)
//        btnLogout.setOnClickListener(this)
//        llMyLeaderboard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogout -> {
                context?.let {
                    AlertDialog(it)
                        .onPositiveListener { view, dialog ->
                            dialog.dismiss()
                            loadingDialog.show(R.string.dialog_lbl_loading)
                            viewModel.logout()
                        }
                        .show(R.string.dialog_title_logout, R.string.dialog_content_logout)
                }
            }
            R.id.ivUser -> {
                val intent = Intent(activity!!, PreviewImageActivity::class.java)

                intent.putExtra(Constants.BUNDLE.PHOTO, userProfile.photo)
                val pair = Pair.create<View, String>(ivUser, "imagePreview")
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, pair)
                startActivity(intent, options.toBundle())

            }
            R.id.vChangePassword -> {
                goTo<ChangePasswordActivity> {  }
            }
//
//            R.id.btnInbox -> {
//                val i = Intent(activity!!, InboxActivity::class.java)
//                startActivityForResult(i, Constants.INTENT.RES_INTENT)
//            }
//            R.id.llMyLeaderboard ->{
//                val i = Intent(activity!!, LeaderboardActivity::class.java)
//                startActivityForResult(i, Constants.INTENT.RES_INTENT)
//            }
        }
        super<BaseFragment>.onClick(v)
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
