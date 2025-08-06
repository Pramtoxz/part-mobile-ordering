package ahm.parts.ordering.ui.home.home.campaignpromo.fragment.part

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.campaignpromo.PartPromo
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.home.campaignpromo.CampaignPromoActivity
import ahm.parts.ordering.ui.home.home.campaignpromo.CampaignPromoViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_part_promo.*
import kotlinx.android.synthetic.main.item_part_promo.view.*

/**
 * class ini mengandung interaksi UI dengan layout fragment_part_promo.xml
 *
 */
class PartPromoFragment : BaseFragment<CampaignPromoViewModel>(), View.OnClickListener {

    lateinit var mainActivity: CampaignPromoActivity

    var paging = 1

    var isLoad = false

    lateinit var partPromoAdapter: RecyclerAdapter<PartPromo>
    var listPartPromo = ArrayList<PartPromo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_part_promo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

        observeData()
        hitApi()
    }

    private fun initUI() {

        srRefresh.setOnRefreshListener {
            hitApi()
        }

        initAdapter()

    }

    private fun hitApi(){
        paging = 1
        viewModel.hitPartPromo(paging,false)
    }

    private fun observeData() {
        /**
         * mendapatkan data dari api
         */
        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            if(it.status == ApiStatus.LOADING){
                isLoad = true
            }
            showStateApiView(it){
                hitApi()
            }
          /*  if(it.status == ApiStatus.LOADING){
                srRefresh.isRefreshing = true
            }*/
        })

        viewModel.partPromoList.observe(viewLifecycleOwner, Observer {

            isLoad = false

            srRefresh.isRefreshing = false
            pbLoadingBottom.hide()

            listPartPromo.clear()
            listPartPromo.addAll(it)

            partPromoAdapter.notifyDataSetChanged()
        })

        viewModel.partPromoListLoadMore.observe(viewLifecycleOwner, Observer {

            isLoad = false

            listPartPromo.addAll(it)

            partPromoAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter() {

        partPromoAdapter =
            rvPartPromo.setAdapter(activity!!, listPartPromo, R.layout.item_part_promo, {
                val item = listPartPromo[it]

                tvNamePart text item.code + " • " + item.name
                tvPartNumber text item.partNumber + " • " + item.itemGroup
                tvHet text StringMasker().addRp(item.het.toDouble())
                tvDiscount text item.discount + "%"

                tvBeginDate text CalendarUtils.setFormatDate(item.beginEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
                tvEndDate text CalendarUtils.setFormatDate(item.endEffdate,"yyyy-MM-dd","MMMM dd, yyyy")

                tvNotePart text "*"+item.note

            }, {
                val data = this
                goTo<PartPromoDetailActivity> {
                    putExtra(Constants.BUNDLE.JSON,data.getString())
                }
            })

      /*  scrollView.scrollBottom {
            if(!isLoad){
                paging += 1
                viewModel.hitPartPromo(paging,true)
            }
        }
*/
    }

    private fun initListener() {
    }

    override fun onClick(view: View?) {
    }

    fun newInstance(activity: CampaignPromoActivity): PartPromoFragment {
        val fragment =
            PartPromoFragment()

        fragment.mainActivity = activity

        val args = Bundle()
        fragment.arguments = args

        return fragment
    }


}
