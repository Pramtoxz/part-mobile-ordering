package ahm.parts.ordering.ui.widget.activity.sales

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.init
import ahm.parts.ordering.helper.initItem
import ahm.parts.ordering.ui.base.BaseActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_sales_activity.*

class ActivitySalesActivity : BaseActivity<ActivitySalesViewModel>() {

    var salesActivities = ArrayList<String>()
    lateinit var salesAdapter: ActivitySalesAdapter

    lateinit var searchView : SearchView
    var strQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_activity)
        setToolbar(getString(R.string.toolbar_sales), true)

        populateData()

        initAdapterProspect()
    }

    /**
     * mengisi daftar prospek
     */
    private fun populateData() {
        salesActivities.clear()
        salesActivities.add("Unit")
        salesActivities.add("Accessories")
        salesActivities.add("Apparel")
    }

    /**
     * menampilkan daftar prospek
     */
    fun initAdapterProspect(){
        salesAdapter = ActivitySalesAdapter(salesActivities, this@ActivitySalesActivity){
            // change position of selected tab

        }

        rvSales.initItem(this@ActivitySalesActivity, salesAdapter)
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
