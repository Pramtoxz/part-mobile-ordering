package ahm.parts.ordering.ui.home.home.campaignpromo

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.hide
import ahm.parts.ordering.helper.setAdapterHorizontal
import ahm.parts.ordering.helper.show
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.brosur.BrosurPromoFragment
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.part.PartPromoFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_campaign_promo.*
import kotlinx.android.synthetic.main.item_campaign_promo_tab.view.*

/**
 * class ini mengandung interaksi UI dengan layout activity_campaign_promo.xml
 *
 */
class CampaignPromoActivity : BaseActivity<HomeViewModel>() {

    var selectedCategory = 0
    lateinit var categoryAdapter: RecyclerAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_promo)

        initUI()

    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_campaign_promo), true)

        openPage(BrosurPromoFragment().newInstance(this))

        initAdapter()
    }

    private fun initAdapter() {

        val listCategory = ArrayList<String>()
        listCategory.add(getString(R.string.lbl_tab_brosur_promo))
        listCategory.add(getString(R.string.lbl_tab_part_promo))

        categoryAdapter =
            rvCategory.setAdapterHorizontal(this, listCategory, R.layout.item_campaign_promo_tab, {
                val item = listCategory[it]

                tvNameTab text item

                if (it == 0) {
                    spFirst.show()
                } else {
                    spFirst.hide()
                }

                if (selectedCategory == it) {
                    bgTab.setBackgroundResource(R.drawable.bg_tab_campaign_selected)
                } else {
                    bgTab.setBackgroundResource(R.drawable.bg_tab_campaign_un_selected)
                }

            }, {
                if (selectedCategory != it) {
                    selectedCategory = it
                    categoryAdapter.notifyDataSetChanged()

                    if (it == 0) {
                        openPage(BrosurPromoFragment().newInstance(this@CampaignPromoActivity))
                    } else {
                        openPage(PartPromoFragment().newInstance(this@CampaignPromoActivity))
                    }

                }
            })
    }

    private fun openPage(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }


}
