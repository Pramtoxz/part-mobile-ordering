package ahm.parts.ordering.ui.home.home.partnumber.tipemotor

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllTypeMotor
import ahm.parts.ordering.helper.getString
import ahm.parts.ordering.helper.onChangeText
import ahm.parts.ordering.helper.onSearch
import ahm.parts.ordering.helper.setAdapter
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_tipe_motor_search.*
import kotlinx.android.synthetic.main.item_content_kelompok_barang_search.view.*
import kotlinx.android.synthetic.main.item_header_kelompok_barang_search.view.*
import java.util.*
import kotlin.collections.ArrayList

class TipeMotorSearchActivity : BaseActivity<TipeMotorSearchViewModel>(), View.OnClickListener {

    lateinit var filterMotorAdapter : RecyclerAdapter<String>

    var allTypeMotors = ArrayList<AllTypeMotor>()
    var favoriteMotors = ArrayList<AllTypeMotor>()

    var strSearch = ""

    var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipe_motor_search)

        initUI()
        initListener()

        observeData()
        viewModel.hitTypeMotor(strSearch)
    }

    private fun initUI() {

        initAdapter()

        srRefresh.setOnRefreshListener {
            viewModel.hitTypeMotor(strSearch)
        }

        etSearch.onChangeText {
            strSearch = it

            timer = onSearch(timer){
                viewModel.hitTypeMotor(strSearch)
            }
        }

    }

    private fun observeData(){

        viewModel.apiResponse.observe(this, Observer {
            if(it.status == ApiStatus.LOADING){
                srRefresh.isRefreshing = true
            }
        })

        viewModel.typeMotorSearchs.observe(this, Observer {
            srRefresh.isRefreshing = false

            allTypeMotors.clear()
            favoriteMotors.clear()

            allTypeMotors.addAll(it.list)
            favoriteMotors.addAll(it.favorit)

            filterMotorAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter() {

        val listData = ArrayList<String>()
        listData.add(getString(R.string.lbl_pencarian_favorit))
        listData.add(getString(R.string.lbl_semua_tipe_motor))

        filterMotorAdapter = rvFavorit.setAdapter(this, listData, R.layout.item_header_kelompok_barang_search, {
            val item = listData[it]

            tvName.text(item)

            if(it == 0){
                rvContent.setAdapter(
                    this@TipeMotorSearchActivity,
                    favoriteMotors,
                    R.layout.item_content_kelompok_barang_search,
                    {
                        val item = favoriteMotors[it]

                        tvNameSearch text item.name
                    },
                    {

                        val intent = Intent()
                        intent.putExtra(Constants.BUNDLE.JSON,this.getString())
                        setResult(Constants.RESULT.PART_NUMBER_MOTOR_SEARCH,intent)
                        finish()

                    })
            }else{
                rvContent.setAdapter(
                    this@TipeMotorSearchActivity,
                    allTypeMotors,
                    R.layout.item_content_kelompok_barang_search,
                    {
                        val item = allTypeMotors[it]

                        tvNameSearch text item.name
                    },
                    {

                        val intent = Intent()
                        intent.putExtra(Constants.BUNDLE.JSON,this.getString())
                        setResult(Constants.RESULT.PART_NUMBER_MOTOR_SEARCH,intent)
                        finish()

                    })
            }



        }, {

        })
    }

    private fun initListener() {
        tvBatalkan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            tvBatalkan -> onBackPressed()
        }
    }
}
