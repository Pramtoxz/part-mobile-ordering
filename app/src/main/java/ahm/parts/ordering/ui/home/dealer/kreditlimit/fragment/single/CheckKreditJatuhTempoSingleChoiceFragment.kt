package ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.single

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitViewModel
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KreditLimitKodeDealerSelectedAdapter
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo.KreditJatuhTempoDetailActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo.KreditJatuhTempoListDetailActivity
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

class CheckKreditJatuhTempoSingleChoiceFragment : BaseFragment<KreditLimitViewModel>(), View.OnClickListener {

    var kodeDealerSelected = AllDealer()

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
        btnCheckCredit text activity!!.resources.getString(R.string.btn_check_jatuh_tempo)

        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            try {
                kodeDealerSelected = AllDealer(id = it!!.dealerId,name = it!!.dealerName,code = it!!.dealerCode)
                etKodeDealer text kodeDealerSelected!!.name + " ~ " + kodeDealerSelected!!.code
            } catch (e: Exception) {
            }
        })


        tvHeader text getString(R.string.lbl_lengkapi_form_untuk_cek_jatuh_tempo)
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST.KODE_DEALER && resultCode == Constants.RESULT.KODE_DEALER) {
            kodeDealerSelected = data!!.extra(Constants.BUNDLE.JSON).getObject()
            etKodeDealer text kodeDealerSelected!!.name + " ~ " + kodeDealerSelected!!.code
        }
    }

    private fun initListener() {
        etKodeDealer.setOnClickListener(this)
        btnCheckCredit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            etKodeDealer -> {
            }
            btnCheckCredit -> {
                if (kodeDealerSelected!!.id != "") {
                    goTo<KreditJatuhTempoListDetailActivity> {
                        putExtra(Constants.BUNDLE.KODEDEALER,createJsonDealer())
                    }
                    //viewModel.hitCheckKreditJatuhTempo("${kodeDealerSelected!!.id}")

                } else {
                    snacked(rootView, activity!!.resources.getString(R.string.lbl_form_required))
                }
            }
        }
    }


    fun createJsonDealer(): String {
        val jsonArray = JSONArray()

        val jsonObject = JSONObject()

        jsonObject.put("id", kodeDealerSelected!!.id.toInt())

        jsonArray.put(jsonObject)

        val jsonSend = jsonArray.toString()
        Log.e("jsonSend", "" + jsonSend)
        return jsonSend
        //viewModel.hitSendMySocialActivity(jsonSend)
    }


    companion object {
        @JvmStatic
        fun newInstance() = CheckKreditJatuhTempoSingleChoiceFragment()
    }


}
