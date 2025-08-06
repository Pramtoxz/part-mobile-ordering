package ahm.parts.ordering.ui.home.catalogue

import android.os.Bundle

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.catalogue.Catalogue
import ahm.parts.ordering.data.model.catalogue.CatalogueList
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.catalogue.detail_file.file.CatalogueFileActivity
import ahm.parts.ordering.ui.home.catalogue.detail_file.file_parent.CatalogueFileParentActivity
import ahm.parts.ordering.ui.home.catalogue.part_catalogue.PartCatalogueActivity
import android.view.*
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_catalogue_parts.*
import kotlinx.android.synthetic.main.item_catalogue_list.view.*

class  CatalogueFragment : BaseFragment<HomeViewModel>(), View.OnClickListener {

    lateinit var catalogueData : Catalogue

    lateinit var catalogueAdapter : RecyclerAdapter<CatalogueList>
    var catalogues = ArrayList<CatalogueList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalogue_parts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

        observeData()
        viewModel.getCatalogue()
    }

    private fun initUI(){

        swipeRefresh.setOnRefreshListener {
            viewModel.getCatalogue()
        }

        initAdapter()

    }

    private fun observeData(){

        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            showStateApiView(it){
                viewModel.getCatalogue()
            }
        })

        viewModel.catalogue.observe(viewLifecycleOwner, Observer {
            catalogueData = it

            catalogues.clear()
            catalogues.addAll(it.data)

            catalogueAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter(){
        catalogueAdapter = rvCatalogue.setAdapter(activity!!,catalogues,R.layout.item_catalogue_list,{
            val item = catalogues[it]

            tvNameCatalogue text item.name

            ivCatalogue loadImage item.icon

        },{
            val data = this

            if(data.list){
                goTo<CatalogueFileParentActivity> {
                    putExtra(Constants.REMOTE.OBJ_DATA,data.getString())
                }
            }else{
                goTo<CatalogueFileActivity> {
                    putExtra(Constants.REMOTE.OBJ_DATA,data.getString())
                }
            }


        })
    }

    private fun initListener(){
        btnPartCatalogue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btnPartCatalogue -> {
                goTo<PartCatalogueActivity> {
                    putExtra(Constants.REMOTE.OBJ_DATA,catalogueData.partCatalogue.getString())
                    activity!!.fade()
                }
            }
//            btnBukuPedoman -> {
//                goTo<CatalogueFileActivity> {  }
//            }
//            btnPlasticStripe -> {
//                goTo<PlasticStripeActivity> {  }
//            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = CatalogueFragment()
    }
}
