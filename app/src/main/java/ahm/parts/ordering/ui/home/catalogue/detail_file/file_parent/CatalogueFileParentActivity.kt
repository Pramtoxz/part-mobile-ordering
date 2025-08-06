package ahm.parts.ordering.ui.home.catalogue.detail_file.file_parent

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.catalogue.CatalogueDetailFile
import ahm.parts.ordering.data.model.catalogue.CatalogueList
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.catalogue.adapter.CatalogueFileParentAdapter
import ahm.parts.ordering.ui.home.catalogue.detail_file.file_list.CatalogueFileListActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_buku_pedoman_pemilik.*
import kotlinx.android.synthetic.main.item_catalogue_list.view.*

class CatalogueFileParentActivity : BaseActivity<HomeViewModel>() {

    lateinit var searchView: SearchView

    var strQuery = ""

    lateinit var catalogue: CatalogueList

    lateinit var catalogueAdapter: CatalogueFileParentAdapter
    var listFile = ArrayList<CatalogueDetailFile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buku_pedoman_pemilik)

        initUI()
        observeData()

    }

    private fun initUI() {
        catalogue = extra(Constants.REMOTE.OBJ_DATA).getObject()

        setToolbar(catalogue.name, true)

        srRefresh.setOnRefreshListener {
            srRefresh.isRefreshing = false
        }

        listFile.addAll(catalogue.detail)

        catalogueAdapter =
            CatalogueFileParentAdapter(
                this,
                listFile
            )

        rvList.apply {
            layoutManager = LinearLayoutManager(this@CatalogueFileParentActivity)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = catalogueAdapter
        }
    }

    private fun observeData(){
        viewModel.fileDownloaded.observe(this, Observer {
            loadingDialog.dismiss()
            if (it!=null) AppStorageHelper(this).openFile(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.search).actionView

        searchView = menuItem as SearchView
        searchView.init(getString(R.string.hint_search), { strChange ->
            filter(strChange)
        }, { strSubmit ->
            filter(strSubmit)
        })

        return super.onCreateOptionsMenu(menu)
    }


    private fun filter(text: String) {
        val filteredList = java.util.ArrayList<CatalogueDetailFile>()

        for (item in listFile) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }

        try {
            catalogueAdapter.filterList(filteredList)
        } catch (e: Exception) {
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

}
