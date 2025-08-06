package ahm.parts.ordering.ui.home.dealer.collection.list

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.collection.Collection1
import ahm.parts.ordering.data.model.home.dealer.collection.CollectionParam
import ahm.parts.ordering.data.model.home.dealer.collection.CollectionTotal
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.OnChangeItem
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.dealer.collection.CollectionViewModel
import ahm.parts.ordering.ui.home.dealer.collection.adapter.CollectionAdapter
import ahm.parts.ordering.ui.home.dealer.competitor.add.multiple.CompetitorAddMultipleActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_collection_list.*
import kotlinx.android.synthetic.main.item_collection_list.*
import kotlinx.android.synthetic.main.item_collection_list.view.*
import kotlin.collections.ArrayList

class CollectionActivityList : BaseActivity<CollectionViewModel>(), ClickPrevention{
    /**
     * variable collection page sebelumnya jika sudah pernah menyeleksi di page ini
     */
    var kodeCollectionSelects = ArrayList<Collection1>()
    var collections = ArrayList<Collection1>()
    var listCollection = CollectionTotal()
    var paramCollection = CollectionParam()

    lateinit var kodecollectionAdapter: CollectionAdapter
    var dealerCollection = extra(Constants.BUNDLE.KODEDEALER)
    var Tglawal = extra(Constants.BUNDLE.START_TIME)
    var Tglakhir = extra(Constants.BUNDLE.END_TIME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_list)
        paramCollection = extra(Constants.BUNDLE.PARAM).getObject()
        initUI()
        initListener()
        observeData()
        viewModel.hitCollectionList(paramCollection)
        viewModel.Collectionttl(paramCollection)
        setViewSelectedCollection(collections)
        setViewCollectionTotalFee(collections)
    }


    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_collection_list), true)
//        kodeCollectionSelects = extra(Constants.BUNDLE.COLLECTION).toList<Collection1>() as ArrayList<Collection1>

//        swipeRefresh.setOnRefreshListener {
//            viewModel.hitCollectionList(paramCollection)
//        }
//        swipeRefresh.setOnRefreshListener {
//            viewModel.Collectionttl(paramCollection)
//        }
//
        initAdapter()
    }

    private fun observeData() {
//        viewModel.apiResponse.observe(this, Observer {
//            showStateApiView(it,400) {
//                viewModel.hitCollectionList(paramCollection)
//            }
//        })

//        viewModel.collection1.observe(this, Observer {
//            listCollection = it
//            it.apply {
//                tvPiutang text StringMasker().addRp(totalHutang1)
////                tvTotalBayarSelect text StringMasker().addRp(totalHutang1)
//            }
//        })

//        viewModel.collection.observe(this, Observer {
//            rvCollectionList.adapter as CollectionAdapter
//            collections.clear()
//            collections.addAll(it)
//            rvCollectionList.adapter?.notifyDataSetChanged()
//        })

    }

    private fun initAdapter() {
        rvCollectionList.setAdapter(this, collections, R.layout.item_collection_list, {
            val item = collections[it]
            tvPiutang.text = StringMasker().addRp( item.totalHutang.toDouble())
            tvAmountTotal.text = StringMasker().addRp( item.bayar.toDouble())
            }
            , {
                val data = this
                goTo<CollectionAddPaymentActivity> {
                    putExtra(Constants.BUNDLE.JSON, data.getString())
                }

            })
        kodecollectionAdapter = CollectionAdapter(collections,this){}
        rvCollectionList.initItem(this,kodecollectionAdapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.COLLECTION_KODE_DEALER && resultCode == Constants.RESULT.COLLECTION_KODE_DEALER){
            viewModel.hitCollectionList(paramCollection)
        }
    }

    fun initListener() {
        rvCollectionList.setOnClickListener(this)
        btnSubmitCol.setOnClickListener(this)
    }

    /**
     * menampilkan jumlah list terpilih
     * @param items list collections
     */
    private fun setViewSelectedCollection(items: List<Collection1>) {
        val adapter = rvCollectionList.adapter as CollectionAdapter
        val count = adapter.getSelectedItemsCollection(items).size
        tvTotalItem.text = count.toString()
    }

    /**
     * memilih jenis service
     * @param position item jenis service terpilih
     */
    private fun selectItemCollection(position: Int) {
        val adapter = rvCollectionList.adapter as CollectionAdapter
        adapter.notifyItemCollectionSelected(collections,collections[position])
        adapter.notifyDataSetChanged()
    }


    /**
     * mendapatkan code transaksi terpilih
     *
     * @param collections list Collection terpilih
     * @return list code transaksi collection terpilih
     */
    private fun getCodeServicesTypeSelected(serviceTypes: List<Collection1>): List<String> {
        val adapter = rvCollectionList.adapter as CollectionAdapter
        return adapter.getCodeServicesTypeSelected(serviceTypes)
    }

    /**
     * menampilkan total bayar collection
     * @param items list collection terpilih
     */
    private fun setViewCollectionTotalFee(items: List<Collection1>) {
        val adapter = rvCollectionList.adapter as CollectionAdapter
        tvTotalBayarSelect.text = StringMasker().addRp(adapter.getCollectionFee(items))
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSubmitCol->{
                goTo<CollectionAddPaymentActivity>(requestCode = Constants.REQUEST.COMPETITOR_ADD) { }
                Toast.makeText(this,"Data Clicked",Toast.LENGTH_SHORT).show()
            }
        }
    }


//    override fun onClick(position:Int, view: View) {
//        when (view.id) {
//            R.id.llRoot->{
//                selectItemCollection(position)
//                val adapter = rvCollectionList.adapter as CollectionAdapter
//                val selectedItems = adapter.getSelectedItemsCollection(collections)
//                setViewSelectedCollection(selectedItems)
//                setViewCollectionTotalFee(selectedItems)
//            }
//        }
//    }
}
