package ahm.parts.ordering.ui.home.dealer.competitor.list

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.competitor.Competitor
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.dealer.competitor.CompetitorViewModel
import ahm.parts.ordering.ui.home.dealer.competitor.add.multiple.CompetitorAddMultipleActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_competitor.*
import kotlinx.android.synthetic.main.item_competitor.view.*

class CompetitorActivity : BaseActivity<CompetitorViewModel>(), View.OnClickListener {

    var competitors = ArrayList<Competitor>()
    lateinit var competitorAdapter: RecyclerAdapter<Competitor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competitor)

        initUI()
        initListener()

        observeData()
        viewModel.hitCompetitor()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_competitor), true)

        swipeRefresh.setOnRefreshListener {
            viewModel.hitCompetitor()
        }

        initAdapter()
    }

    private fun observeData() {
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it) {
                viewModel.hitCompetitor()
            }
        })

        viewModel.competitors.observe(this, Observer {
            competitors.clear()
            competitors.addAll(it)

            competitorAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter() {
        competitorAdapter =
            rvCompetitor.setAdapter(this, competitors, R.layout.item_competitor, {
                val item = competitors[it]

                tvCodeDealer text item.codeDealer + " • " + item.idRole
                tvNameCompetitor text item.titleActivityCompetitor
                tvItemGroup text item.product
                tvDate text CalendarUtils.setFormatDate(
                    item.beginEffdate,
                    "yyyy-MM-dd",
                    "MMMM dd, yyyy"
                ) + " - " + CalendarUtils.setFormatDate(
                    item.endEffdate,
                    "yyyy-MM-dd",
                    "MMMM dd, yyyy"
                )
                tvNote text item.description

                ivCompetitor.loadImage(
                    item.photo[0].photo,
                    skGroup
                )

            }, {
                val data = this
                goTo<CompetitorDetailSliderActivity> {
                    putExtra(Constants.BUNDLE.JSON, data.getString())
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.COMPETITOR_ADD && resultCode == Constants.RESULT.COMPETITOR_ADD){
            viewModel.hitCompetitor()
        }
    }

    private fun initListener() {
        btnAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnAdd -> {
                goTo<CompetitorAddMultipleActivity>(requestCode = Constants.REQUEST.COMPETITOR_ADD) { }
            }
        }
    }

}
