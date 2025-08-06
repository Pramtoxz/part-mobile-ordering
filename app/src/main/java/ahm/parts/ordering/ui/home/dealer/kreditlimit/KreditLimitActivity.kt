package ahm.parts.ordering.ui.home.dealer.kreditlimit

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.hide
import ahm.parts.ordering.helper.initItemHorizontal
import ahm.parts.ordering.helper.setAdapterHorizontal
import ahm.parts.ordering.helper.show
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KreditLimitCategoryAdapter
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.CheckKreditJatuhTempoFragment
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.CheckKreditLimitFragment
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.single.CheckKreditJatuhTempoSingleChoiceFragment
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.single.CheckKreditLimitSingleChoiceFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_kredit_limit.*

class KreditLimitActivity : BaseActivity<KreditLimitViewModel>() {

    lateinit var categoryAdapter: KreditLimitCategoryAdapter

    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kredit_limit)

        initUI()

    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_kredit_limit), true)

        viewModel.getUser().observe(this, Observer {
            try {
                user = it!!
                if(user.id_role == Constants.ROLE.DEALER){
                    openPage(CheckKreditLimitSingleChoiceFragment.newInstance())
                }else{
                    openPage(CheckKreditLimitFragment.newInstance())
                }
            } catch (e: Exception) {
            }
        })


        initAdapter()
    }

    private fun initAdapter() {

        val categorys = ArrayList<String>()
        categorys.add(getString(R.string.lbl_tab_check_kredit_limit))
        categorys.add(getString(R.string.lbl_tab_check_jatuh_tempo))

        categoryAdapter = KreditLimitCategoryAdapter(categorys,this){
            when(it){
                0 -> {
                    if(user.id_role == Constants.ROLE.DEALER){
                        openPage(CheckKreditLimitSingleChoiceFragment.newInstance())
                    }else{
                        openPage(CheckKreditLimitFragment.newInstance())
                    }
                }
                1 -> {
                    if(user.id_role == Constants.ROLE.DEALER){
                        openPage(CheckKreditJatuhTempoSingleChoiceFragment.newInstance())
                    }else{
                        openPage(CheckKreditJatuhTempoFragment.newInstance())
                    }
                }
            }
        }

        rvCategory.initItemHorizontal(this,categoryAdapter)

    }

    private fun openPage(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }


}
