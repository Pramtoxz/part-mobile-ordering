package ahm.parts.ordering.ui.home.dealer.collection

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.collection.Collection1
import ahm.parts.ordering.helper.extra
import ahm.parts.ordering.ui.base.BaseActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_collection_notification.*

class CollectionNotification : BaseActivity<CollectionViewModel>(), View.OnClickListener {
    lateinit var collection : Collection1
    var notrans = ""
    var totb =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_notification)
        setToolbar(getString(R.string.lbl_title_collection_notification), true)
        notrans = extra(Constants.BUNDLE.NOTRANSAKSI)
        totb = extra(Constants.BUNDLE.TOTALBAYAR)
        initUI()
//        initListener()
//        observeData()
    }

    private fun initUI(){
        tvKodeDealerCollectNotif text notrans
            tvTotalBayarNotif text totb
    }

    override fun onClick(v: View?) {

    }
}