package ahm.parts.ordering.ui.widget.activity.prospect

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.helper.init
import ahm.parts.ordering.helper.initItem
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.widget.activity.prospect.detail.DetailProspectActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_prospect.*

class ProspectActivity : BaseActivity<ProspectViewModel>() {

    var prospectActivities = ArrayList<String>()
    lateinit var prospectAdapter: ProspectAdapter

    lateinit var searchView : SearchView
    var strQuery = ""
    var isLoading = false
    var page : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prospect)
        setToolbar(getString(R.string.sales_lbl_prospek), true)

        populateData()

        initAdapterProspect()
    }

    /**
     * mengisi daftar prospek
     */
    private fun populateData() {
        prospectActivities.clear()
        prospectActivities.add("Unit")
        prospectActivities.add("Accessories")
        prospectActivities.add("Apparel")
    }

    /**
     * menampilkan daftar prospek
     */
    fun initAdapterProspect(){
        prospectAdapter = ProspectAdapter(prospectActivities, this@ProspectActivity){
            // change position of selected tab
            val i = Intent(this@ProspectActivity, DetailProspectActivity::class.java)
            startActivityForResult(i, Constants.INTENT.REQ_INTENT)
        }

        rvProspek.initItem(this@ProspectActivity, prospectAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_activity, menu)
        val menuItem = menu!!.findItem(R.id.search).actionView

        searchView = menuItem as SearchView
        searchView.init(getString(R.string.hint_search_sales), { strChange ->
            if (!strQuery.isEmpty()) {
                if (strChange.isEmpty()) {
                    strQuery = strChange

                    //TODO search
                }
            }
        },{ strSubmit ->
            
        })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
