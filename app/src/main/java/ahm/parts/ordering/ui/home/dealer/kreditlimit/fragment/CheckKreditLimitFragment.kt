package ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitViewModel
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KreditLimitKodeDealerSelectedAdapter
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.kreditlimit.KreditLimitDetailActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.kreditlimit.KreditLimitListDetailActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_check_kredit_limit.*
import org.json.JSONArray
import org.json.JSONObject

class CheckKreditLimitFragment : BaseFragment<KreditLimitViewModel>(), View.OnClickListener {

    lateinit var kodeDealerAdapter : KreditLimitKodeDealerSelectedAdapter
    var kodeDealerSelected = ArrayList<AllDealer>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_kredit_limit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {

        initAdapter()
    }

    private fun observeData() {

        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == ApiStatus.LOADING) {
                loadingDialog.show(R.string.dialog_lbl_loading)
            } else {
                loadingDialog.dismiss()
                if(it.status != ApiStatus.SUCCESS){
                    snacked(rootView, it.message)
                }
            }
        })

        viewModel.kreditLimit.observe(viewLifecycleOwner, Observer {
            Log.e("itKreditLimit","jalan")
            goTo<KreditLimitDetailActivity> {
                putExtra(Constants.BUNDLE.JSON, it.getString())
            }
        })
    }

    private fun initAdapter(){
        kodeDealerAdapter = KreditLimitKodeDealerSelectedAdapter(kodeDealerSelected,activity!!){}
        rvKodeDealerSelected.initItemGrid(activity!!,kodeDealerAdapter,2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST.KODE_DEALER && resultCode == Constants.RESULT.KODE_DEALER) {
            kodeDealerSelected.clear()
            kodeDealerSelected.addAll(data!!.extra(Constants.BUNDLE.JSON).toList<AllDealer>() as ArrayList<AllDealer>)

            validateSearchHint()

            kodeDealerAdapter.notifyDataSetChanged()
           /* kodeDealerSelected = data!!.extra(Constants.BUNDLE.JSON).getObject()

            etKodeDealer text kodeDealerSelected!!.code
            etKodeDealerName text kodeDealerSelected!!.name
*/
        }
    }


    private fun validateSearchHint(){
        if(kodeDealerSelected.isNotEmpty()){
            etKodeDealer.hint = getString(R.string.hint_add_cari_kode_dealer)
        }else{
            etKodeDealer.hint = getString(R.string.hint_cari_kode_dealer)
        }
    }

    private fun initListener() {
        etKodeDealer.setOnClickListener(this)
        btnCheckCredit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            etKodeDealer -> {
                goTo<KodeDealerKreditLimit>(requestCode = Constants.REQUEST.KODE_DEALER) {
                    putExtra(Constants.BUNDLE.JSON,kodeDealerSelected.getString())
                }
            }
            btnCheckCredit -> {
                if (kodeDealerSelected.isNotEmpty()) {
                    goTo<KreditLimitListDetailActivity> {
                        putExtra(Constants.BUNDLE.KODEDEALER,createJSON(kodeDealerSelected))
                    }
                    /*viewModel.hitCheckKreditLimit("${kodeDealerSelected!!.id}")*/
                } else {
                    snacked(rootView, activity!!.resources.getString(R.string.lbl_form_required))
                }
            }
        }
    }

    fun createJSON(list : ArrayList<AllDealer>): String {
        val jsonArray = JSONArray()


        for (i in list.indices) {
            val item = list[i]

            val jsonObject = JSONObject()

            jsonObject.put("id", item.id.toInt())

            jsonArray.put(jsonObject)
        }
        val jsonSend = jsonArray.toString()
        Log.e("jsonSend", "" + jsonSend)
        return jsonSend
        //viewModel.hitSendMySocialActivity(jsonSend)
    }


    companion object {
        @JvmStatic
        fun newInstance() = CheckKreditLimitFragment()
    }
//    fun newInstance(position: Int): CheckKreditLimitFragment {
//        val fragment =
//            CheckKreditLimitFragment()
//
// //       fragment.positionFlag = position
//
//        val args = Bundle()
//        fragment.arguments = args
//
//        return fragment
//    }


}
