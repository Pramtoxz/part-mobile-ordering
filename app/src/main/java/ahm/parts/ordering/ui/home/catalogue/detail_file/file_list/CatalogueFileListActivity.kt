package ahm.parts.ordering.ui.home.catalogue.detail_file.file_list

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.catalogue.CatalogueDetailFile
import ahm.parts.ordering.data.model.catalogue.CatalogueDetailFileList
import ahm.parts.ordering.data.model.catalogue.CatalogueList
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.catalogue.adapter.CatalogueFileListAdapter
import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_buku_pedoman_pemilik.*
import kotlinx.android.synthetic.main.item_buku_pedoman_pemilik.view.*

class CatalogueFileListActivity : BaseActivity<HomeViewModel>() {

    lateinit var searchView: SearchView

    var strQuery = ""

    lateinit var catalogue: CatalogueDetailFile

    lateinit var catalogueAdapter: CatalogueFileListAdapter
    var listFile = ArrayList<CatalogueDetailFileList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buku_pedoman_pemilik)

        initUI()
        observeData()

    }

    private fun initUI() {
        catalogue = extra(Constants.BUNDLE.JSON).getObject()

        setToolbar(catalogue.name, true)

        srRefresh.setOnRefreshListener {
            srRefresh.isRefreshing = false
        }

        listFile.addAll(catalogue.detail)

        catalogueAdapter =
            CatalogueFileListAdapter(
                this,
                listFile
            )

        rvList.apply {
            layoutManager = LinearLayoutManager(this@CatalogueFileListActivity)
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
        val filteredList = java.util.ArrayList<CatalogueDetailFileList>()

        for (item in listFile) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.size == 0) {
            snacked(rootView,"Data Tidak Ditemukan")
        } else {

        }

        try {
            catalogueAdapter.filterList(filteredList)
        } catch (e: Exception) {
        }
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.search -> {
//
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

}
