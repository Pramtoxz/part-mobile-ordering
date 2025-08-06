package ahm.parts.ordering.ui.home.home.campaignpromo.fragment.brosur

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.campaignpromo.BrosurePromo
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
import kotlinx.android.synthetic.main.fragment_brosur_promo.*
import kotlinx.android.synthetic.main.item_brosur_promo.view.*

/**
 * class ini mengandung interaksi UI dengan layout fragment_brosur_promo.xml
 *
 */
class BrosurPromoFragment : BaseFragment<CampaignPromoViewModel>(), View.OnClickListener {

    lateinit var mainActivity : CampaignPromoActivity

    var paging = 1

    lateinit var brosurAdapter : RecyclerAdapter<BrosurePromo>
    var brosurPromos = ArrayList<BrosurePromo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_brosur_promo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        observeData()
        viewModel.hitBrosure(paging,false)
    }

    private fun initUI() {

        srRefresh.setOnRefreshListener {
            paging = 1
            viewModel.hitBrosure(paging,false)
        }

        initAdapter()

    }

    private fun observeData(){

        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            showStateApiView(it){
                paging = 1
                viewModel.hitBrosure(paging,false)
            }
        })

        viewModel.brosureList.observe(viewLifecycleOwner, Observer {

            srRefresh.isRefreshing = false

            brosurPromos.clear()
            brosurPromos.addAll(it)

            brosurAdapter.notifyDataSetChanged()
        })

        viewModel.brosureListLoadMore.observe(viewLifecycleOwner, Observer {

            brosurPromos.addAll(it)

            brosurAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){

        brosurAdapter = rvBrosur.setAdapter(activity!!,brosurPromos,R.layout.item_brosur_promo,{
            val item = brosurPromos[it]

            tvCode text item.code
            tvTitleBrosure text item.title
            tvNoteBrosure text item.note
            tvDateBrosure text CalendarUtils.setFormatDate(item.promoStart,"yyyy-MM-dd","MMMM dd, yyyy") + " - " + CalendarUtils.setFormatDate(item.promoEnd,"yyyy-MM-dd","MMMM dd, yyyy")

            ivImageBrosur.loadImage(item.photo,skGroupBrosur)

        },{
            val data = this
            goTo<BrosureDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,data.getString())
            }
        })

       /* scrollView.scrollBottom {
            paging += 1
            viewModel.hitBrosure(paging,true)
        }*/

    }

    fun newInstance(activity: CampaignPromoActivity): BrosurPromoFragment {
        val fragment =
            BrosurPromoFragment()

        fragment.mainActivity = activity

        val args = Bundle()
        fragment.arguments = args

        return fragment
    }


}
